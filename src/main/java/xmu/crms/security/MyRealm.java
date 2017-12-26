package xmu.crms.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import xmu.crms.entity.User;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.LoginService;
import xmu.crms.service.impl.LoginServiceImpl;

public class MyRealm extends AuthorizingRealm {

	private static final Logger LOGGER = LogManager.getLogger(MyRealm.class);

	private LoginService loginService;

	public MyRealm() {
		loginService = new LoginServiceImpl();
	}

	/**
	 * 大坑！，必须重写此方法，不然Shiro会报错
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JWTToken;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
	 */
	/*@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = JWTUtil.getUsername(principals.toString());
		User user = null;
		SimpleAuthorizationInfo simpleAuthorizationInfo = null;
		try {
			user = loginService.signInPhone(user);
			if (user != null) {
			} else {
				throw new UserNotFoundException("用户不存在");
			}
			simpleAuthorizationInfo = new SimpleAuthorizationInfo();
			simpleAuthorizationInfo.addRole(user.getType() ? "teacher" : "student");
			System.out.println("simpleAuthorizationInfo"+simpleAuthorizationInfo);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		// Set<String> permission = new
		// HashSet<>(Arrays.asList(user.getPermission().split(",")));
		// simpleAuthorizationInfo.addStringPermissions(permission);
		return simpleAuthorizationInfo;
	}
*/
	/**
	 * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
	 */
	/*@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
		String token = (String) auth.getCredentials();
		// 解密获得username，用于和数据库进行对比
		String username;
		Long id;
		User user = null;
		try {
			username = JWTUtil.getUsername(token);
			id = JWTUtil.getUserId(token);
			if (username == null || id == null) {
				throw new AuthenticationException("token invalid");
			}
			user = loginService.getUserByPhoneAndId(username, id);
			if (user == null) {
				throw new AuthenticationException("User didn't existed!");
			}
			if (!JWTUtil.verify(token, username, user.getPassword())) {
				throw new AuthenticationException("Username or password error");
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		SimpleAuthenticationInfo simpleAuthenticationInfo=null;
		simpleAuthenticationInfo=new SimpleAuthenticationInfo(token, token, "my_realm");
		System.out.println("new simpleAuthentication"+simpleAuthenticationInfo);
		return simpleAuthenticationInfo;
	}*/

}