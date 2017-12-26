package xmu.crms.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import xmu.crms.entity.Course;
import xmu.crms.entity.Seminar;

public interface SeminarDao {
    int deleteByPrimaryKey(BigInteger id);

    int insertSelective(Seminar record);

    Seminar selectByPrimaryKey(BigInteger id);

    int updateByPrimaryKeySelective(Seminar record);

	List<Seminar> listSeminarByCourseId(BigInteger courseId);

	int deleteSeminarByCourseId(BigInteger courseId);

	Course getCourseById(BigInteger courseId);


}