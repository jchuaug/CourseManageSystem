package xmu.crms.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.User;

public interface FixedGroupMapper {

	int insertFixGroup(FixGroup fixGroup);

	int deleteFixGroupMemberByFixGroupId(BigInteger fixGroupId);

	int deleteFixGroupUserById(@Param("fixGroupId") BigInteger fixGroupId,@Param("userId") BigInteger userId);

	List<User> listFixGroupMemberByGroupId(BigInteger groupId);

	FixGroup getFixGroupByFixGroupId(BigInteger groupId);

	List<FixGroup> listFixGroupByClassId(BigInteger classId);

	int deleteFixGroupByClassId(BigInteger classId);

	int deleteFixGroupByGroupId(BigInteger groupId);

	int updateFixGroupByGroupId(FixGroup fixGroupBO);

	List<FixGroupMember> listFixGroupByGroupId(BigInteger groupId);

	int insertFixGroupMember(FixGroupMember fixGroup);

	FixGroupMember getFixGroupMemberById(@Param("userId")BigInteger userId, @Param("groupId")BigInteger groupId);

	FixGroup getFixGroupById(@Param("userId")BigInteger userId,@Param("classId") BigInteger classId);

}
