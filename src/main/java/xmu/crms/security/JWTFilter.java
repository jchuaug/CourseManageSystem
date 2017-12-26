package xmu.crms.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

public class JWTFilter extends BasicHttpAuthenticationFilter {

	
	JWTToken jwt;
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
    	System.out.println("开始鉴权");
    	System.out.println(jwt);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 获取Authorization字段
        String authorization = httpServletRequest.getHeader("Authorization");
        System.out.println(authorization);
        if (authorization!=null) {
            try {
                JWTToken token = new JWTToken(authorization);
                System.out.println("token="+token);
                // 提交给realm进行登入，如果错误他会抛出异常并被捕获
                getSubject(request, response).login(token);
                return true;
            } catch (Exception e) {
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
    private void response401(ServletRequest req, ServletResponse resp) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.sendRedirect("/401");
    }
    
    
    public static boolean isAjax(ServletRequest request) {
    	String header=((HttpServletRequest) request).getHeader("X-Requested-With");  
    	if("XMLHttpRequest".equalsIgnoreCase(header)){  
            System.out.println( "当前请求为Ajax请求");  
            return Boolean.TRUE;  
        }  
    	System.out.println( "当前请求非Ajax请求"); 
        return Boolean.FALSE;  
    }
}
