package xmu.crms.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.SeminarGroupTopic;

public interface GradeMapper {

	int deleteStudentScoreGroupByTopicId(BigInteger seminarGroupTopicId);

	SeminarGroup getSeminarGroupBySeminarGroupId(BigInteger seminarGroupId);

	List<SeminarGroup> listSeminarGradeByUserId(BigInteger userId);

	int insertGroupGradeByUserId(@Param("topicId") BigInteger topicId,@Param("userId") BigInteger userId, @Param("groupId")BigInteger groupId, @Param("grade")BigInteger grade);

	int updateGroupByGroupId(@Param("seminarGroupId")BigInteger seminarGroupId,@Param("grade")BigInteger grade);

	List<SeminarGroupTopic> listSeminarGroupTopic(BigInteger id);

	List<Integer> listGradeBySeminarGroupTopicId(BigInteger id);

	int insertGroupPresentationGradeByUserId(@Param("id")BigInteger id,@Param("grade") Integer grade);

	int insertGroupTopicGradeByUserId(@Param("id")BigInteger id,@Param("grade") Integer grade);

}
