package xmu.crms.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import xmu.crms.dao.AuthDao;
import xmu.crms.security.JwtUserDetails;
import xmu.crms.service.security.AuthService;
import xmu.crms.utils.MD5Utils;
import xmu.crms.web.VO.JwtAuthenticationResponse;

@Controller
public class AuthController {
/*	@Value("${jwt_token_head}")
	private String tokenHeader;

	@Autowired(required = false)
	private AuthDao authDao;

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
		if (wholeStr == null) {
			return ResponseEntity.status(500).build();
		}
		Map<String, Object> o = new ObjectMapper().readValue(wholeStr, Map.class);
		System.out.println("post登陆信息" + o.toString());
		final String password = (String) o.get("password");

//		final String token = authService.login((String) o.get("phone"), MD5Utils.MD5encode(password));
		final String token = authService.login((String) o.get("phone"),password);
		JwtUserDetails user = authDao.getUserByPhone((String) o.get("phone"));
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
	}*/

}
