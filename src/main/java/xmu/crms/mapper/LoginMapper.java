package xmu.crms.mapper;

import java.math.BigInteger;

import org.apache.ibatis.annotations.Mapper;

import xmu.crms.entity.User;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.ClassService;
import xmu.crms.service.CourseService;

@Mapper
public interface LoginMapper {

	/**
	 * 微信登录后用户绑定. 成功后返回用户Id
	 */
	Integer signUpWeChat(String phone, String password);

	/**
	 * 根据手机号获取密码
	 * 
	 */
	String getPasswordByPhone(String phone);

	/**
	 * 手机号注册.
	 * <p>
	 * 手机号注册 (.Net使用),User中只有phone和password，userId是注册后才有并且在数据库自增<br>
	 * 
	 * @return user 该用户Id
	 */
	Integer signUpPhone(String phone, String password);

	/**
	 * 用户解绑.
	 * <p>
	 * 教师解绑账号(j2ee使用)<br>
	 * 
	 * @author
	 * @param Id
	 * 
	 */
	Integer unbindTeacherAccount(BigInteger id);

	/**
	 * 用户解绑.
	 * <p>
	 * 学生解绑账号(j2ee使用)<br>
	 * 
	 * @author
	 * @param 用户id
	 */
	Integer unbindStudentAccount(BigInteger id);

}
