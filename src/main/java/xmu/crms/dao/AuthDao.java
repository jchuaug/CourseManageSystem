package xmu.crms.dao;

import org.apache.ibatis.annotations.Mapper;

import xmu.crms.security.JwtUserDetails;

/**
 *
 * @author JackeyHuang
 *
 */
@Mapper
public interface AuthDao {
	/**
	 * 根据手机号登录
	 * 
	 * @param phone
	 * @return
	 */
	JwtUserDetails getUserByPhone(String phone);

	/**
	 * 根据openid登录
	 * 
	 * @param openid
	 * @return
	 */
	JwtUserDetails getUserByOpenId(String openid);
}
