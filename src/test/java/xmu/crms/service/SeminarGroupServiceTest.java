package xmu.crms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import xmu.crms.CourseManageApplication;
import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.SeminarGroupMember;
import xmu.crms.entity.User;
import xmu.crms.exception.GroupNotFoundException;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.impl.SeminarGroupServiceImpl;

import java.math.BigInteger;

/**
 * @author zw
 * @date 2017/12/25 0025
 * @time 18:03
 * @email 493703217@qq.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@Transactional
@SpringBootTest(classes = CourseManageApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SeminarGroupServiceTest {
    @Autowired
    SeminarGroupServiceImpl seminarGroupService;

    /**
     * 按seminarGroupId删除SeminarGroupMember信息.
     * <p>按seminarGroupId删除SeminarGroupMember信息<br>
     *
     * @author zhouzhongjun
     */
    @Test
    // TODO: 2017/12/25
    public void testDeleteSeminarGroupMemberBySeminarGroupId() {
        seminarGroupService.deleteSeminarGroupMemberBySeminarGroupId(new BigInteger("1"));
    }


    @Test
    public void testdeleteSeminarGroupMemberByuId() {
        seminarGroupService.deleteSeminarGroupMemberById(new BigInteger("5"), new BigInteger("23"));
    }


    /**
     * 将学生加入讨论课小组.
     * <p>将用户加入指定的讨论课小组<br>
     *
     * @return BigInteger 若创建成功返回该条记录的id，失败则返回-1
     * @throws IllegalArgumentException  (信息不合法，id格式错误)
     * @throws GroupNotFoundException    (未找到小组)
     * @throws UserNotFoundException     (不存在该学生)
     * @throws InvalidOperationException （待添加学生已经在小组里了）
     * @author YeHongjie
     */
    @Test
    public void testInsertSeminarGroupMemberById() {
        try {
            seminarGroupService.insertSeminarGroupMemberById(new BigInteger("59"), new BigInteger("1"));
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidOperationException e) {
            e.printStackTrace();
        }
    }

    ;

    /**
     * 查询讨论课小组成员.
     * <p>按照讨论课小组id查询该小组的成员<br>
     *
     * @return List 讨论课小组成员信息
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @throws GroupNotFoundException   未找到小组
     * @author YeHongjie
     */
    @Test
    public void testListSeminarGroupMemberByGroupId() {
        try {
            System.out.println(seminarGroupService.listSeminarGroupMemberByGroupId(new BigInteger("1")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;

    /**
     * 获取某学生所有的讨论课小组.
     * <p>根据学生id获取学生所在的所有讨论课小组的信息<br>
     *
     * @return list 讨论课小组列表
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @author qinlingyun
     */
    @Test
    public void testListSeminarGroupIdByStudentId() {
        System.out.println(seminarGroupService.listSeminarGroupIdByStudentId(new BigInteger("31")));
    }

    ;


    /**
     * 查询讨论课小组队长id.
     * <p>按照讨论课小组id查询该小组的队长id<br>
     *
     * @return leaderId 讨论课小组队长id
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @throws GroupNotFoundException   未找到小组
     * @author YeHongjie
     */
    @Test
    public void testGetSeminarGroupLeaderByGroupId() {
        try {
            System.out.println(seminarGroupService.getSeminarGroupLeaderByGroupId(new BigInteger("1")));
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        }
    }

    ;


    /**
     * 按seminarId获取SeminarGroup.
     * <p>按seminarId获取SeminarGroup<br>
     *
     * @param seminarId 课程Id
     * @return 讨论课小组列表
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @throws SeminarNotFoundException 未找到讨论课
     * @author zhouzhongjun
     */
    @Test
    public void testListSeminarGroupBySeminarId() {
        try {
            System.out.println(seminarGroupService.listSeminarGroupBySeminarId(new BigInteger("2")));
        } catch (SeminarNotFoundException e) {
            e.printStackTrace();
        }
    }

    ;

    /**
     * 按seminarId删除讨论课小组信息.
     * <p>根据seminarId获得SeminarGroup，然后根据SeminarGroupId删除SeminarGroupMember信息，最后再删除SeminarGroup信息<br>
     *
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @author zhouzhongjun
     * @see SeminarGroupService #listSeminarGroupBySeminarId(BigInteger seminarId)
     * @see SeminarGroupService #deleteSeminarGroupMemberBySeminarGroupId(BigInteger seminarGroupId)
     */
    @Test
    public void testDeleteSeminarGroupBySeminarId() {
        seminarGroupService.deleteSeminarGroupBySeminarId(new BigInteger("1"));
    }

    ;

    /**
     * 创建讨论课小组.
     * <p>在指定讨论课下创建讨论课小组<br>
     *
     * @param
     * @param
     * @return BigInteger 若创建成功返回该小组的id，失败则返回-1
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @author YeHongjie
     * @see SeminarGroupService #insertSeminarGroupMemberByGroupId(BigInteger groupId,SeminarGroupMember SeminarGroupMember)
     */
    @Test
    public void testInsertSeminarGroupBySeminarId() {
        SeminarGroup seminarGroup = new SeminarGroup();
        ClassInfo classInfo = new ClassInfo();
        classInfo.setId(new BigInteger("1"));
        User user = new User();
        user.setId(new BigInteger("3"));
        seminarGroup.setLeader(user);
        seminarGroup.setClassInfo(classInfo);
        seminarGroupService.insertSeminarGroupBySeminarId(new BigInteger("2"), seminarGroup);
    }

    ;

    /**
     * 创建小组成员信息.
     * <p>在指定小组成员表下创建一个新的小组信息<br>
     *
     * @param
     * @param
     * @return BigInteger 若创建成功返回该小组成员表的id，失败则返回-1
     */
    @Test
    public void testInsertSeminarGroupMemberByGroupId() {
        SeminarGroupMember seminarGroupMember = new SeminarGroupMember();
        seminarGroupMember.setId(new BigInteger("58"));
        seminarGroupService.insertSeminarGroupMemberByGroupId(new BigInteger("1"), seminarGroupMember);
    }

    ;


    /**
     * 删除讨论课小组.
     * <p>按照id删除讨论课小组<br>
     *
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @author YeHongjie
     * @see SeminarGroupService #deleteSeminarGroupMemberBySeminarGroupId(BigInteger seminarGroupId)
     */
    @Test
    public void testDeleteSeminarGroupByGroupId() {
        seminarGroupService.deleteSeminarGroupByGroupId(new BigInteger("5"));
    }

    ;

    /**
     * 查询讨论课小组.
     * <p>按照id查询某一讨论课小组的信息（包括成员）<br>
     *
     * @return seminarGroup 讨论课小组对象，若未找到相关小组返回空(null)
     * @throws IllegalArgumentException (信息不合法，id格式错误)
     * @throws GroupNotFoundException   (未找到小组)
     * @author YeHongjie
     * @see SeminarGroupService #listSeminarGroupMemberByGroupId(BigInteger groupId)
     */
    @Test
    public void testGetSeminarGroupByGroupId() {
        try {
            System.out.println(seminarGroupService.getSeminarGroupByGroupId(new BigInteger("19")));
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        }
    }

    ;

    /**
     * 获取学生所在讨论课队长.
     * <p>按照用户id和讨论课id获取学生所在讨论课小组队长<br>
     *
     * @return BigInteger 讨论课小组的队长id，若未找到相关小组队长返回空(null)
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @author YeHongjie
     * @see SeminarGroupService #getSeminarGroupById(BigInteger userId, BigInteger seminarId)
     * @see SeminarGroupService #getSeminarGroupLeaderByGroupId(BigInteger groupId)
     */
    @Test
    public void testGetSeminarGroupLeaderById() {
        System.out.println(seminarGroupService.getSeminarGroupLeaderById(new BigInteger("31"), new BigInteger("2")));
    }

    ;


    /**
     * 自动分组 定时器方法.
     * <p>根据讨论课id和班级id，结束签到后 对签到的学生进行自动分组<br>
     *
     * @throws IllegalArgumentException  信息不合法，id格式错误
     * @throws SeminarNotFoundException  未找到讨论课
     * @throws GroupNotFoundException    未找到小组
     * @throws UserNotFoundException     学生不存在
     * @throws InvalidOperationException 无效操作
     * @author YeHongjie
     * @see UserService #listAttendanceById(BigInteger classId, BigInteger seminarId);
     */
    @Test
    public void testAutomaticallyGrouping() {
        try {
            seminarGroupService.automaticallyGrouping(new BigInteger("123"), new BigInteger("123"));
        } catch (SeminarNotFoundException e) {
            e.printStackTrace();
        }
    }

    ;

    //    @Test
    //todo inspect this method
    public void automaticallyAllotTopic() {
        try {
            seminarGroupService.automaticallyAllotTopic(new BigInteger("1"));
        } catch (SeminarNotFoundException e) {
            e.printStackTrace();
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增定时器方法.
     * <p>随机分组情况下，签到结束后十分钟给没有选择话题的小组分配话题<br>
     * @author qinlingyun
     * @param seminarId 讨论课的id
     * @param seminarGroupId 小组的id
     * @exception IllegalArgumentException 信息不合法，id格式错误
     * @exception SeminarNotFoundException 未找到讨论课
     * @exception GroupNotFoundException 未找到小组
     * 尚未实现
     */
//    void automaticallyAllotTopic(BigInteger seminarId) throws
//            IllegalArgumentException, SeminarNotFoundException, GroupNotFoundException;


    /**
     * 根据讨论课Id及用户id，获得该用户所在的讨论课的小组的信息.
     * <p>根据讨论课Id及用户id，获得该用户所在的讨论课的小组的信息<br>
     *
     * @return SeminarGroup Group的相关信息
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @throws GroupNotFoundException   未找到小组
     */
    @Test
    public void testGetSeminarGroupById() {
        try {
            System.out.println(seminarGroupService.getSeminarGroupById(new BigInteger("2"), new BigInteger("31")));
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * 根据话题Id获得选择该话题的所有小组的信息.
     * <p>根据话题Id获得选择该话题的所有小组的信息<br>
     *
     * @return List 所有选择该话题的所有group的信息
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @throws GroupNotFoundException   未找到小组
     */
    @Test
    public void testListGroupByTopicId() {
        try {
            System.out.println(seminarGroupService.listGroupByTopicId(new BigInteger("1")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ;

    /**
     * 小组按id选择话题.
     * <p>小组通过小组id和话题id选择讨论课的话题<br>
     *
     * @return BigInteger 返回seminarGroupTopicId
     * @throws IllegalArgumentException GroupId、TopicId格式错误时抛出
     * @throws GroupNotFoundException   该小组不存在时抛出
     * @author heqi
     */
    @Test
    public void testInsertTopicByGroupId() {
        try {
            seminarGroupService.insertTopicByGroupId(BigInteger.valueOf(1), BigInteger.valueOf(7));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ;


    /**
     * 成为组长.
     * <p>同学按小组id和自身id成为组长<br>
     *
     * @throws IllegalArgumentException  信息不合法，id格式错误
     * @throws GroupNotFoundException    未找到小组
     * @throws UserNotFoundException     不存在该学生
     * @throws InvalidOperationException 已经有组长了
     */
    @Test
    public void assignLeaderById() {
        try {
            seminarGroupService.assignLeaderById(new BigInteger("1"), new BigInteger("3"));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidOperationException e) {
            e.printStackTrace();
        }
    }

    ;

    /**
     * 组长辞职.
     * <p>同学按小组id和自身id,辞掉组长职位<br>
     *
     * @throws IllegalArgumentException (信息不合法，id格式错误)
     * @throws GroupNotFoundException   (未找到小组)
     */
    @Test
    public void resignLeaderById() {
        try {
            seminarGroupService.resignLeaderById(new BigInteger("1"), new BigInteger("3"));
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        }
    }
}
