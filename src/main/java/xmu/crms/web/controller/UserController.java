package xmu.crms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xmu.crms.entity.User;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.LoginService;
import xmu.crms.utils.JWTUtil;
import xmu.crms.web.VO.LoginResponseVO;

/**
 * UserController
 *
 * @author Jackey
 */
@Controller
@RequestMapping("/")
@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')") // 学生或老师都能访问
public class UserController {
    @Autowired
    private LoginService loginService;

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
        LoginResponseVO responseVO = null;
        try {
            if (user == null) {
                responseVO = new LoginResponseVO(401, "手机号密码错误");
                throw new UserNotFoundException();
            } else {
                responseVO = new LoginResponseVO(200, "login success", user.getId(),
                        (user.getType() == 1 ? "teacher" : "student"), user.getName(), JWTUtil.sign(user));
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(responseVO.toString());
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
                responseVO = new LoginResponseVO(200, "signup success", user.getId(), "unbinded", user.getName(),
                        JWTUtil.sign(user));
            }
        } catch (Exception e) {
        }
        // ResponseEntity<LoginResponseVO>responseEntity=new
        // ResponseEntity<LoginResponseVO>(body, , HttpStatus.OK);
        return responseVO;
    }

}
