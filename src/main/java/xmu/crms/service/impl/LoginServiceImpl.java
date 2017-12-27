package xmu.crms.service.impl;

import java.math.BigInteger;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xmu.crms.entity.User;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.mapper.LoginMapper;
import xmu.crms.mapper.UserMapper;
import xmu.crms.service.ClassService;
import xmu.crms.service.CourseService;
import xmu.crms.service.LoginService;

/**
 * LoginServiceImpl实现
 *
 * @author JackeyHuang
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    public LoginMapper loginMapper;
    @Autowired
    public UserMapper userMapper;

    /**
     * 微信登录.
     * <p>
     * 微信登录<br>
     *
     * @param userId     用户Id
     * @param code       微信小程序/OAuth2授权的Code
     * @param state      微信OAuth2授权的state。对于小程序，值恒为 MiniProgram
     * @param successUrl 微信OAuth2授权后跳转到的网址
     * @return user 该用户信息
     * @throws UserNotFoundException 登录失败时抛出
     * @author qinlingyun
     */
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
     * @param user 用户信息(手机号Phone和密码Password)
     * @return user 该用户信息
     * @throws UserNotFoundException 登录失败时抛出
     * @author qinlingyun
     */
    @Override
    public User signInPhone(User user) throws UserNotFoundException {
        String phone = user.getPhone();
        User userResult = null;
        try {
            userResult = userMapper.getUserByPhone(phone);
            System.out.println(userResult);
            if (userResult.getPassword().equals(user.getPassword())) {
            } else {
                String errMsg = userResult != null ? "密码错误" : "账户不存在";
                userResult = null;
                throw new UserNotFoundException();
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
     * @param user 用户信息(手机号Phone和密码Password)
     * @return user 该用户信息
     * @author qinlingyun
     */
    @Override
    public User signUpPhone(User user) {

        User userResult = null;
        try {
            if (userMapper.getUserByPhone(user.getPhone()) != null) {
                throw new Exception("LoginService:手机号已被注册");
            }
            userResult = userMapper
                    .getUserByUserId(BigInteger.valueOf(loginMapper.signUpPhone(user.getPhone(), user.getPassword())));
            if (userResult == null) {
                throw new Exception("LoginService:注册失败");
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
     * @param userId 用户id
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @throws UserNotFoundException    未找到对应用户
     * @author qinlingyun
     * @see CourseService#listCourseByUserId(BigInteger userId)
     * @see CourseService#deleteCourseByCourseId(BigInteger courseId)
     */
    @Override
    public void deleteTeacherAccount(BigInteger userId) throws IllegalArgumentException, UserNotFoundException {
        User user = null;
        try {
            if (!(pattern.matcher(userId.toString()).matches())) {
                throw new IllegalArgumentException("LoginService:信息不合法，id格式错误");
            }
            user = userMapper.getUserByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }
            Integer unbindReturnVal = loginMapper.unbindTeacherAccount(userId);
            System.out.println("LoginService:unbindReturnVal is :" + unbindReturnVal);
            Boolean returnState = unbindReturnVal == 1 ? true : false;
            if (!returnState) {
                throw new Exception("LoginService:解绑失败");
            }
            System.out.println("LoginService:解绑用户成功");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 用户解绑.
     * <p>
     * 学生解绑账号(j2ee使用)<br>
     *
     * @author qinlingyun
     * @param userId
     * 用户id
     * @exception IllegalArgumentException
     * 信息不合法，id格式错误
     * @exception UserNotFoundException
     * 未找到对应用户
     * @see ClassService#deleteCourseSelectionById(BigInteger userId, BigInteger
     * classId)
     */
    Pattern pattern = Pattern.compile("[0-9]*");

    @Override
    public void deleteStudentAccount(BigInteger userId) throws IllegalArgumentException, UserNotFoundException {
        User user = null;
        try {
            if (!(pattern.matcher(userId.toString()).matches())) {
                throw new IllegalArgumentException("LoginService:信息不合法，id格式错误");
            }
            user = userMapper.getUserByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException();
            }
            Integer unbindReturnVal = loginMapper.unbindStudentAccount(userId);
            System.out.println("LoginService:unbindReturnVal is :" + unbindReturnVal);
            Boolean returnState = unbindReturnVal == 1 ? true : false;
            if (!returnState) {
                throw new Exception("LoginService:解绑失败");
            }
            System.out.println("LoginService:解绑用户成功");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 微信登录后用户绑定.
     * <p>
     * User中只有phone和password，userId是注册后才有并且在数据库自增<br>
     *
     * @param user 用户信息
     * @throws IllegalArgumentException user中信息有误
     */
    @Override
    public void signUpWeChat(User user) throws IllegalArgumentException {
        BigInteger returnId = null;
        try {
            returnId = BigInteger.valueOf(loginMapper.signUpWeChat(user.getPhone(), user.getPassword()));
            if (returnId == null) {
                throw new InvalidOperationException();
            }

        } catch (InvalidOperationException e) {
            e.printStackTrace();
        }

    }

}
