package xmu.crms.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import xmu.crms.service.UserService;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
* <p>Title: JWTFilter.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2018<／p>
 * @author Jackey
 * @date 2018年1月3日
 */
public class JWTFilter extends AuthenticationFilter {
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

	 @Override    
	    protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) {    
	        Subject subject = getSubject(req, resp);    
	        String[] rolesArray = (String[]) mappedValue;    
	    
	        if (rolesArray == null || rolesArray.length == 0) { //没有角色限制，有权限访问    
	            return true;    
	        }    
	        for (int i = 0; i < rolesArray.length; i++) {    
	            if (subject.hasRole(rolesArray[i])) { //若当前用户是rolesArray中的任何一个，则有权限访问    
	                return true;    
	            }    
	        }    
	    
	        return false;    
	    }    
	/**
	 * 对跨域提供支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
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
