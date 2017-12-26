package xmu.crms.service.impl;

import java.math.BigInteger;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xmu.crms.dao.LoginDao;
import xmu.crms.dao.UserDao;
import xmu.crms.entity.User;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.CourseService;
import xmu.crms.service.LoginService;

/**
 * LoginServiceImpl实现
 * 
 * @author JackeyHuang
 *
 */
@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	public LoginDao loginDao;
	@Autowired
	public UserDao userDao;

	@Override
	public User signInWeChat(BigInteger userId, String code, String state, String successUrl)
			throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 手机号登录.
	 * <p>
	 * 手机号登录 (.Net使用),User中只有phone和password，用于判断用户名密码是否正确<br>
	 * 
	 * @author qinlingyun
	 * @param user
	 *            用户信息(手机号Phone和密码Password)
	 * @return user 该用户信息
	 * @exception UserNotFoundException
	 *                登录失败时抛出
	 */
	@Override
	public User signInPhone(User user) throws UserNotFoundException {
		String phone = user.getPhone();
		User userResult = null;
		try {
			userResult = userDao.getUserByPhone(phone);
			System.out.println(userResult);
			if (userResult.getPassword().equals(user.getPassword())) {
			} else {
				String errMsg = userResult != null ? "密码错误" : "账户不存在";
				userResult = null;
				throw new UserNotFoundException(errMsg);
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return userResult;

	}

	/**
	 * 手机号注册.
	 * <p>
	 * 手机号注册 (.Net使用),User中只有phone和password，userId是注册后才有并且在数据库自增<br>
	 * 
	 * @author qinlingyun
	 * @param user
	 *            用户信息(手机号Phone和密码Password)
	 * @return user 该用户信息
	 */
	@Override
	public User signUpPhone(User user) {

		User userResult = null;
		try {
			if (userDao.getUserByPhone(user.getPhone()) != null) {
				throw new Exception("手机号已被注册");
			}
			userResult = userDao.getUserById(loginDao.signUpPhone(user));
			if (userResult == null) {
				throw new Exception("注册失败");
			}

			return userResult;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userResult;
	}

	/**
	 * 用户解绑.
	 * <p>
	 * 教师解绑账号(j2ee使用)<br>
	 * 
	 * @author qinlingyun
	 * @param userId
	 *            用户id
	 * @see CourseService#listCourseByUserId(BigInteger userId)
	 * @see CourseService#deleteCourseByCourseId(BigInteger courseId)
	 * @exception IllegalArgumentException
	 *                信息不合法，id格式错误
	 * @exception UserNotFoundException
	 *                未找到对应用户
	 */
	@Override
	public void deleteTeacherAccount(BigInteger userId) throws IllegalArgumentException, UserNotFoundException {
		User user = null;
		Pattern pattern = Pattern.compile("[0-9]*");
		try {
			if (!(pattern.matcher(userId.toString()).matches())) {
				throw new IllegalArgumentException("信息不合法，id格式错误");
			}
			user = userDao.getUserById(userId);
			if (user == null) {
				throw new UserNotFoundException("未找到对应用户");
			}
			User returnUser = userDao.deleteUserAccount(userId);
			if (returnUser == null) {
				throw new Exception("注册失败");
			}
			System.out.println("解绑用户" + returnUser.getName() + "成功");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteStudentAccount(BigInteger userId) throws IllegalArgumentException, UserNotFoundException {
		User user = null;
		Pattern pattern = Pattern.compile("[0-9]*");
		try {
			if (!(pattern.matcher(userId.toString()).matches())) {
				throw new IllegalArgumentException("信息不合法，id格式错误");
			}
			user = userDao.getUserById(userId);
			if (user == null) {
				throw new UserNotFoundException("未找到对应用户");
			}
			User returnUser = userDao.deleteUserAccount(userId);
			if (returnUser == null) {
				throw new Exception("删除失败");
			}
			System.out.println("解绑用户" + returnUser.getName() + "成功");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
