package xmu.crms.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import xmu.crms.entity.*;
import xmu.crms.exception.*;
import xmu.crms.mapper.SeminarMapper;
import xmu.crms.service.SeminarService;

/**
 *
 * @author zhouzhongjun CaoXingmei YeHongjie
 * @version 2.00
 *
 */
@Service
public class SeminarServiceImpl implements SeminarService {

	@Autowired
	private SeminarMapper seminarMapper;
	
/*	@Autowired
	private TopicService topicService;*/
	
	/*	@Autowired
	private SeminarGroupService seminarGroupService;*/

	@Override
	public List<Seminar> listSeminarByCourseId(BigInteger courseId)
			throws IllegalArgumentException, CourseNotFoundException {
		if (courseId==null) {
			throw new IllegalArgumentException();
		}
		Course course=seminarMapper.getCourseById(courseId);
		if (course==null) {
			throw new CourseNotFoundException();
		}
		List<Seminar> list= seminarMapper.listSeminarByCourseId(courseId);
		return list;
	}

	@Override
	public void deleteSeminarByCourseId(BigInteger courseId)
			throws IllegalArgumentException, CourseNotFoundException {
		if (courseId==null) {
			throw new IllegalArgumentException();
		}
		Course course=seminarMapper.getCourseById(courseId);
		if (course==null) {
			throw new CourseNotFoundException();
		}
		List<Seminar> list=listSeminarByCourseId(courseId);
		for (Seminar seminar : list) {
			//topicService.deleteTopicBySeminarId( seminar.getId());
			//seminarGroupService.deleteSeminarGroupBySeminarId( seminar.getId());
			
		}
		int flag= seminarMapper.deleteSeminarByCourseId(courseId);

	}

	@Override
	public Seminar getSeminarBySeminarId(BigInteger seminarId)
			throws IllegalArgumentException, SeminarNotFoundException {
		if (seminarId==null) {
			throw new IllegalArgumentException();
		}
		Seminar seminar=seminarMapper.selectByPrimaryKey(seminarId);
		if (seminar==null) {
			throw new SeminarNotFoundException();
		}
		return seminar;
	}

	@Override
	public void updateSeminarBySeminarId(BigInteger seminarId, Seminar seminar)
			throws IllegalArgumentException, SeminarNotFoundException {
		if (seminarId==null||seminar==null) {
			throw new IllegalArgumentException();
		}
		if (getSeminarBySeminarId(seminarId)==null) {
			throw new SeminarNotFoundException();
		}
		
		seminar.setId(seminarId);
		int flag=seminarMapper.updateByPrimaryKeySelective(seminar);
	}

	@Override
	public void deleteSeminarBySeminarId(BigInteger seminarId)
			throws IllegalArgumentException, SeminarNotFoundException {
		if (seminarId==null) {
			throw new IllegalArgumentException();
		}
		if (getSeminarBySeminarId(seminarId)==null) {
			throw new SeminarNotFoundException();
		}
		//seminarGroupService.deleteSeminarGroupBySeminarId( seminarId);
		//topicService.deleteTopicBySeminarId( seminarId);
		int flag=seminarMapper.deleteByPrimaryKey(seminarId);
	}

	@Override
	public BigInteger insertSeminarByCourseId(BigInteger courseId, Seminar seminar)
			throws IllegalArgumentException, CourseNotFoundException {
		if (courseId==null||seminar==null) {
			throw new IllegalArgumentException();
		}
		if (seminarMapper.getCourseById(courseId)==null) {
			throw new CourseNotFoundException();
		}
		if (seminar.getCourse()==null) {
			Course course =new Course();
			course.setId(courseId);
			seminar.setCourse(course);
		}else {
			seminar.getCourse().setId(courseId);
		}
		int flag=seminarMapper.insertSelective(seminar);
		return seminar.getId();
	}

	

}
