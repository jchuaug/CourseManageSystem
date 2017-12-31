package xmu.crms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xmu.crms.entity.User;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.LoginService;
import xmu.crms.service.SchoolService;
import xmu.crms.service.UserService;
import xmu.crms.service.WeChatService;
import xmu.crms.utils.JWTUtil;
import xmu.crms.web.VO.LoginResponseVO;
import xmu.crms.web.VO.WeChatLoginRequestVO;

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
    public LoginResponseVO login(@RequestParam("phone") String phone, @RequestParam("password") String password)
            throws UserNotFoundException {
        User user = loginService.signInPhone(new User(phone, password));
        System.out.println("User:" + user);
        LoginResponseVO responseVO = null;
        try {
            if (user == null) {
                responseVO = new LoginResponseVO(401, "手机号密码错误");
                throw new UserNotFoundException("用户不存在或者用户密码错误");
            } else {
                responseVO = new LoginResponseVO(200, "login success", user.getId(),
                        (user.getType() == 1 ? "teacher" : "student"), user.getName(), JWTUtil.sign(user));
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(responseVO.toString());

        String jwtString = (String) responseVO.getJwt();
        System.out.println("id=" + JWTUtil.getUserId(jwtString));
        System.out.println("name=" + JWTUtil.getUsername(jwtString));
        System.out.println("userType=" + JWTUtil.getUserType(jwtString));
        return responseVO;
    }


    @PostMapping("/register")
    public LoginResponseVO register(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        User user = loginService.signUpPhone(new User(phone, password));

        LoginResponseVO responseVO = null;
        try {
            if (user == null) {
                responseVO = new LoginResponseVO(401, "手机号已被注册");
                // throw new UserAlreadyExistException("手机号已被注册");
            } else {
                responseVO = new LoginResponseVO(200, "signup success", user.getId(), user.getType() == 1 ? "teacher" : "student", user.getName(),
                        JWTUtil.sign(user));
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

        LoginResponseVO responseVO = new LoginResponseVO(204, "login success", user, JWTUtil.sign(user));
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseVO);
    }

    @PostMapping("/wechat/bind")
    public ResponseEntity weChatBind(@RequestBody WeChatLoginRequestVO req) {
        String openid = weChatService.getOpenId(req.getCode());
        User user = new User();
        user.setName(req.getName());
        user.setNumber(req.getNumber());
        user.setOpenid(openid);
        //todo add school
//        user.setSchool(req.getSchool());
        userService.bindWeChatUser(user);

        try {
            user = userService.getUserByUserNumber(req.getNumber());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }
        LoginResponseVO responseVO = new LoginResponseVO(204, "login success", user, JWTUtil.sign(user));
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseVO);

    }


}
