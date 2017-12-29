package xmu.crms.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import xmu.crms.dao.AuthDao;
import xmu.crms.entity.User;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.security.JwtUserDetails;
import xmu.crms.service.LoginService;
import xmu.crms.service.security.AuthService;
import xmu.crms.utils.JWTUtil;
import xmu.crms.web.VO.JwtAuthenticationResponse;
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
    
    @Value("${jwt_token_head}")
	private String tokenHeader;

	@Autowired(required = false)
	private AuthDao authDao;

	@Autowired
	private AuthService authService;

	/**
     * 。手机号登陆
     *
     * @param phone
     * @param password
     * @return
     * @throws UserNotFoundException
     */
	@RequestMapping(value = "/signin")
	public ResponseEntity createAuthenticationToken(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {

		BufferedReader br = httpServletRequest.getReader();
		String str, entireStr = "";
		while ((str = br.readLine()) != null) {
			entireStr += str;
		}
		if (entireStr == null) {
			return ResponseEntity.status(500).build();
		}
		Map<String, Object> postInfo = new ObjectMapper().readValue(entireStr, Map.class);
		System.out.println("post登陆信息" + postInfo.toString());
		final String password = (String) postInfo.get("password");

//		final String token = authService.login((String) postInfo.get("phone"), MD5Utils.MD5encode(password));
		final String token = authService.login((String) postInfo.get("phone"),password);
		JwtUserDetails user = authDao.getUserByPhone((String) postInfo.get("phone"));
		// Return the token
		String type;
		if (user.getType() == 0) {
			type = "student";
		} else if (user.getType() == 1) {
			type = "teacher";
		} else {
			type = "unbind";
		}
		return ResponseEntity.ok(new JwtAuthenticationResponse(user.getId(), type, user.getName(), token));
	}
    /**
     * 。手机号登陆
     *
     * @param phone
     * @param password
     * @return
     * @throws UserNotFoundException
     */
    @PostMapping("/signin2")

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
