package xmu.crms.dao;

import java.math.BigInteger;

import xmu.crms.entity.User;

public interface LoginDao {
	/**
	 * 通过手机号获取用户
	 * @param phone
	 * @return
	 */

	User getUserByPhoneAndId(String username, Long id);
	
	
/**
 * 用户注册
 * @param user
 * @return 用户id
 */
	BigInteger signUpPhone(User user);
	

}
