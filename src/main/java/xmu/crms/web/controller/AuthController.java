package xmu.crms.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xmu.crms.entity.User;
import xmu.crms.mapper.UserMapper;
import xmu.crms.security.UserDetailsImpl;
import xmu.crms.service.security.AuthService;
import xmu.crms.util.MD5Utils;
import xmu.crms.web.VO.JwtAuthenticationResponse;
import xmu.crms.web.VO.WeChatRequestVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * @author mads
 * @date 2017/12/22 9:18
 */
@Controller
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/auth")
    public ResponseEntity createAuthenticationToken(HttpServletRequest httpServletRequest,
                                                    HttpServletResponse httpServletResponse) throws AuthenticationException, IOException {

        BufferedReader br = httpServletRequest.getReader();
        String str, wholeStr = "";
        while ((str = br.readLine()) != null) {
            wholeStr += str;
        }

        Map<String, Object> o = new ObjectMapper().readValue(wholeStr, Map.class);
        System.out.println("post登陆信息" + o.toString());
        final String password = (String) o.get("password");

        final String token = authService.login((String) o.get("phone"), MD5Utils.MD5encode(password));
        UserDetailsImpl user = userMapper.getUserByPhone((String) o.get("phone"));
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

    @RequestMapping(value = "/auth/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public ResponseEntity register(HttpServletRequest request) throws AuthenticationException, IOException {
        BufferedReader br = request.getReader();
        String str, wholeStr = "";
        while ((str = br.readLine()) != null) {
            wholeStr += str;
        }
        if (wholeStr == null) {
            return ResponseEntity.status(500).build();
        }
        Map<String, Object> o = new ObjectMapper().readValue(wholeStr, Map.class);

        User user = new User(o);
        System.out.println(user.toString());
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(authService.register(user));
    }

    @RequestMapping(value = "/auth/weChat", method = RequestMethod.POST)
    public ResponseEntity weChatLogin(HttpServletRequest request, @RequestBody WeChatRequestVO req) throws IOException {
        Map<String, Object> ne = authService.weChatLogin(req.getCode(), req.getType());
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(ne);
    }
}