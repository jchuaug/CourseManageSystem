package xmu.crms.mapper;

import org.apache.ibatis.annotations.Param;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.SeminarGroupTopic;

import java.math.BigInteger;
import java.util.List;
/**
 * 
* <p>Title: GradeMapper.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2018<／p>
 * @author Jackey
 * @date 2018年1月3日
 */
public interface GradeMapper {
/**
 * 
 * @param seminarGroupTopicId
 * @return
 */
    int deleteStudentScoreGroupByTopicId(BigInteger seminarGroupTopicId);
/**
 * 
 * @param seminarGroupId
 * @return
 */
    SeminarGroup getSeminarGroupBySeminarGroupId(BigInteger seminarGroupId);
/**
 * 
 * @param userId
 * @return
 */
    List<SeminarGroup> listSeminarGradeByUserId(BigInteger userId);
/**
 * 
 * @param topicId
 * @param userId
 * @param groupId
 * @param grade
 * @return
 */
    int insertGroupGradeByUserId(@Param("topicId") BigInteger topicId, @Param("userId") BigInteger userId, @Param("groupId") BigInteger groupId, @Param("grade") Integer grade);
/**
 * 
 * @param seminarGroupId
 * @param grade
 * @return
 */
    int updateGroupByGroupId(@Param("seminarGroupId") BigInteger seminarGroupId, @Param("grade") BigInteger grade);
/**
 * 
 * @param id
 * @return
 */
    List<SeminarGroupTopic> listSeminarGroupTopic(BigInteger id);
/**
 * 
 * @param id
 * @return
 */
    List<Integer> listGradeBySeminarGroupTopicId(BigInteger id);

/**
 * 
 * @param id
 * @param grade
 * @return
 */
    int insertGroupTopicGradeByUserId(@Param("id") BigInteger id, @Param("grade") Integer grade);
/**
 * 
 * @param studentId
 * @param id
 * @param score
 */
    void insertGroupPresentationGradeByUserId(@Param("studentId") BigInteger studentId, @Param("groupId") BigInteger id, @Param("score") Integer score);
/**
 * 
 * @param id
 * @param pTopicGrade
 */
    void updateSeminarGroupPresentationScore(@Param("groupId") BigInteger id, @Param("score") Integer pTopicGrade);
}
