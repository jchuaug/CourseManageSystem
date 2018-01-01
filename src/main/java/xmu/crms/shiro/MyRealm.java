package xmu.crms.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import xmu.crms.entity.User;
import xmu.crms.service.UserService;
import xmu.crms.utils.JWTUtil;

public class MyRealm extends AuthorizingRealm {
    @Autowired
    UserService service;

    // AuthService authService;

	/*
     * MyRealm() { authService = new AuthService(); }
	 * 
	 */

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 权限认证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("doGetAuthorizationInfo调用进行权限认证");
        System.out.println(principals);
        User principalsUser=(User) principals.getPrimaryPrincipal();
//        String phone = JWTUtil.getUserPhone(principals.toString());
        User user = null;

        try {
            user = service.getUserByUserPhone(principalsUser.getPhone());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole((user.getType() == 1 ? "teacher" : "student"));
        // Set<String> permission = new
        // HashSet<>(Arrays.asList(user.getPermission().split(",")));
        // simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo进行登录认证");
        String token = (String) auth.getCredentials();
        // 解密获得phone，用于和数据库进行对比
        String phone = JWTUtil.getUserPhone(token);
        System.out.println("phone:" + phone);
        if (phone.isEmpty()) {
            throw new AuthenticationException("token invalid");
        }
        User user = null;
        user = service.getUserByUserPhone(phone);
        System.out.println("user:" + user);
        if (user == null) {
            throw new AuthenticationException("User didn't existed!");
        }

        if (!JWTUtil.verify(token, phone, user.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }
        SimpleAccount account=new SimpleAccount(user,token,getName());
//        return new SimpleAuthenticationInfo(token, token, "my_realm");
        return account;
    }
}
