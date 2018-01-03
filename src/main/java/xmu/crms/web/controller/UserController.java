package xmu.crms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import xmu.crms.entity.School;
import xmu.crms.entity.User;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.LoginService;
import xmu.crms.service.SchoolService;
import xmu.crms.service.UserService;
import xmu.crms.service.WeChatService;
import xmu.crms.utils.JWTUtil;
import xmu.crms.utils.MD5Utils;
import xmu.crms.utils.ModelUtils;
import xmu.crms.web.VO.LoginResponseVO;
import xmu.crms.web.VO.RegisterRequestVO;
import xmu.crms.web.VO.WeChatLoginRequestVO;

import java.math.BigInteger;

/**
 * UserController
 *
 * @author Jackey
 */

@RestController
public class UserController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private WeChatService weChatService;

    @Autowired
    private SchoolService schoolService;

    /**
     * 。手机号登陆
     *
     * @param phone
     * @param password
     * @return
     * @throws UserNotFoundException
     */
    @PostMapping("/signin")
    public ResponseEntity login(@RequestParam("phone") String phone, @RequestParam("password") String password)
            throws UserNotFoundException {
        User user = loginService.signInPhone(new User(phone, MD5Utils.getMD5(password)));
        System.out.println("User:" + user);
        LoginResponseVO responseVO = null;
        if (user == null) {
            responseVO = new LoginResponseVO(401, "手机号密码错误");
            return ResponseEntity.status(401).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseVO);
        } else {
            responseVO = new LoginResponseVO(200, "login success", user.getId(),
                    (user.getType() == 1 ? "teacher" : "student"), user.getName(), JWTUtil.sign(user));

        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseVO);
    }

    @PostMapping("/register")
    public LoginResponseVO register(@RequestBody RegisterRequestVO req) {
        System.out.println("req:" + req.toString());
        User user = loginService.signUpPhone(new User(req.getPhone(), MD5Utils.getMD5(req.getPassword()), req.getName(),
                req.getGender(), req.getType(), req.getNumber(), req.getEmail()));

        LoginResponseVO responseVO = null;
        try {
            if (user == null) {
                responseVO = new LoginResponseVO(401, "手机号已被注册");
                // throw new UserAlreadyExistException("手机号已被注册");
            } else {
                responseVO = new LoginResponseVO(200, "signup success", user.getId(),
                        user.getType() == 1 ? "teacher" : "student", user.getName(), JWTUtil.sign(user));
            }
        } catch (Exception e) {
        }
        // ResponseEntity<LoginResponseVO>responseEntity=new
        // ResponseEntity<LoginResponseVO>(body, , HttpStatus.OK);
        return responseVO;
    }

    @PostMapping("/wechat/login")
    public ResponseEntity weChatLogin(@RequestBody WeChatLoginRequestVO req) {
        String openId = "";

        User user = userService.getUserByWeChatCode(req.getCode());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        LoginResponseVO responseVO = new LoginResponseVO(200, "login success", user, JWTUtil.sign(user));
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseVO);
    }

    @PostMapping("/wechat/bind")
    public ResponseEntity weChatBind(@RequestBody WeChatLoginRequestVO req) {
        String openid = weChatService.getOpenId(req.getCode());
        User user = new User();
        user.setName(req.getName());
        user.setNumber(req.getNumber());
        user.setOpenid(openid);
        // todo add school
        // user.setSchool(req.getSchool());
        userService.bindWeChatUser(user);

        try {
            user = userService.getUserByUserNumber(req.getNumber());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }
        if (user == null) {
            return ResponseEntity.status(404).build();
        }
        LoginResponseVO responseVO = new LoginResponseVO(200, "login success", user, JWTUtil.sign(user));
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseVO);
    }

    @GetMapping(value = "/wechat/unbind")
    public ResponseEntity unbindUser(@RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());

        userService.unBindWeChatUser(userId);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/me")
    public ResponseEntity getMe(@RequestHeader HttpHeaders headers) {
        BigInteger userId = JWTUtil.getUserIdFromHeader(headers);
        User user = null;
        try {
            user = userService.getUserByUserId(userId);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        if(user==null) {
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(null);
            
        }
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(ModelUtils.UserToUserResponseVO(user));
    }
}
