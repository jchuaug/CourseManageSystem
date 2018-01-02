package xmu.crms.mapper;

import java.math.BigInteger;
import java.util.List;

import xmu.crms.entity.School;

/**
 * 
 * <p>
 * Title: SchoolMapper.java<／p>
 * <p>
 * Description: <／p>
 * <p>
 * Copyright: Copyright (c) 2018<／p>
 * 
 * @author Jackey
 * @date 2018年1月3日
 */
public interface SchoolMapper {
    /**
     * 
     * @param city
     * @return
     */
    List<School> listSchoolByCity(String city);

    /**
     * '
     * 
     * @param school
     * @return
     */
    Integer insertSchool(School school);

    /**
     * 
     * @return
     */
    List<String> listProvince();

    /**
     * 
     * @param province
     * @return
     */
    List<String> listCity(String province);

    /**
     * 
     * @param SchoolId
     * @return
     */
    School getSchoolBySchoolId(BigInteger SchoolId);
}
