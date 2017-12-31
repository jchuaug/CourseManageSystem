package xmu.crms.shiro;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import xmu.crms.entity.User;
import xmu.crms.service.UserService;
import xmu.crms.utils.JWTUtil;

import java.math.BigInteger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTFilter extends BasicHttpAuthenticationFilter {
	private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

	@Autowired
	UserService userService;

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// 获取Authorization字段
		String authorization = httpServletRequest.getHeader("Authorization");

		if (authorization != null) {
			try {
				JWTToken token = new JWTToken(authorization);
				// 提交给realm进行登入，如果错误他会抛出异常并被捕获
				System.out.println("提交给realm进行登入，如果错误他会抛出异常并被捕获");
				getSubject(request, response).login(token);
				return true;
			} catch (Exception e) {
				System.out.println("提交给realm产生异常");
				response401(request, response);
				return false;
			}
		} else {
			response401(request, response);
			return false;
		}
	}

	/**
	 * 将请求返回到 /401
	 */

	private static boolean isAjax(ServletRequest request) {
		String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
		if ("XMLHttpRequest".equalsIgnoreCase(header)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private void response401(ServletRequest req, ServletResponse resp) throws Exception {
		HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
		httpServletResponse.sendRedirect("/401");
	}
}
