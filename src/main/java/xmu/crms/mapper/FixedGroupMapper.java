package xmu.crms.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.User;

/**
 * 
 * <p>
 * Title: FixedGroupMapper.java<／p>
 * <p>
 * Description: <／p>
 * <p>
 * Copyright: Copyright (c) 2018<／p>
 * 
 * @author Jackey
 * @date 2018年1月3日
 */
public interface FixedGroupMapper {
    /**
     * 
     * @param fixGroup
     * @return
     */
    int insertFixGroup(FixGroup fixGroup);

    /**
     * 
     * @param fixGroupId
     * @return
     */

    int deleteFixGroupMemberByFixGroupId(BigInteger fixGroupId);

    /**
     * 
     * @param fixGroupId
     * @param userId
     * @return
     */
    int deleteFixGroupUserById(@Param("fixGroupId") BigInteger fixGroupId, @Param("userId") BigInteger userId);

    /**
     * 
     * @param groupId
     * @return
     */
    List<User> listFixGroupMemberByGroupId(BigInteger groupId);

    /**
     * 
     * @param groupId
     * @return
     */
    FixGroup getFixGroupByFixGroupId(BigInteger groupId);

    /**
     * 
     * @param classId
     * @return
     */
    List<FixGroup> listFixGroupByClassId(BigInteger classId);

    /**
     * 
     * @param classId
     * @return
     */
    int deleteFixGroupByClassId(BigInteger classId);

    /**
     * 
     * @param groupId
     * @return
     */
    int deleteFixGroupByGroupId(BigInteger groupId);

    /**
     * 
     * @param fixGroupBO
     * @return
     */
    int updateFixGroupByGroupId(FixGroup fixGroupBO);

    /**
     * 
     * @param groupId
     * @return
     */
    List<FixGroupMember> listFixGroupByGroupId(BigInteger groupId);

    /**
     * 
     * @param fixGroup
     * @return
     */
    int insertFixGroupMember(FixGroupMember fixGroup);

    /**
     * 
     * @param userId
     * @param groupId
     * @return
     */
    FixGroupMember getFixGroupMemberById(@Param("userId") BigInteger userId, @Param("groupId") BigInteger groupId);

    /**
     * 
     * @param userId
     * @param classId
     * @return
     */
    FixGroup getFixGroupById(@Param("userId") BigInteger userId, @Param("classId") BigInteger classId);

}
