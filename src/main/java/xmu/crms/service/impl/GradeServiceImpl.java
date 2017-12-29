package xmu.crms.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xmu.crms.entity.Seminar;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.SeminarGroupTopic;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.GroupNotFoundException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.mapper.GradeMapper;
import xmu.crms.service.GradeService;
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.SeminarService;

/**
 * @author yjj
 * @date 12/27
 */
@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	private GradeMapper gradeMapper;

	@Autowired
	private SeminarService seminarService;

	@Autowired
	private SeminarGroupService seminarGroupService;

	@Override
	public void deleteStudentScoreGroupByTopicId(BigInteger seminarGroupTopicId) throws IllegalArgumentException {
		if (seminarGroupTopicId == null) {
			throw new IllegalArgumentException();
		}
		gradeMapper.deleteStudentScoreGroupByTopicId(seminarGroupTopicId);
	}

	@Override
	public SeminarGroup getSeminarGroupBySeminarGroupId(BigInteger seminarGroupId)
			throws GroupNotFoundException, IllegalArgumentException {
		SeminarGroup seminarGroup = gradeMapper.getSeminarGroupBySeminarGroupId(seminarGroupId);
		if (seminarGroup == null) {
			throw new GroupNotFoundException();
		}
		return seminarGroup;
	}

	@Override
	public List<SeminarGroup> listSeminarGradeByCourseId(BigInteger userId, BigInteger courseId)
			throws IllegalArgumentException {
		if (userId == null || courseId == null) {
			throw new IllegalArgumentException();
		}
		List<SeminarGroup> list = new ArrayList<>();
		List<Seminar> seminars = null;
		try {
			seminars = seminarService.listSeminarByCourseId(courseId);
		} catch (CourseNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		for (Seminar seminar : seminars) {
			try {
				List<SeminarGroup> seminarGroups = seminarGroupService.listSeminarGroupBySeminarId(seminar.getId());
				if (seminarGroups!=null) {
					list.addAll(seminarGroups);
				}
				
			} catch (SeminarNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		List<SeminarGroup> seminarGroupsByUserId = listSeminarGradeByUserId(userId);
		if (seminarGroupsByUserId == null) {
			return null;
		} else {
			list.retainAll(seminarGroupsByUserId);
		}
		return list;
	}

	private List<SeminarGroup> listSeminarGradeByUserId(BigInteger userId) {
		List<SeminarGroup> seminarGroups = gradeMapper.listSeminarGradeByUserId(userId);
		return seminarGroups;
	}

	@Override
	public void insertGroupGradeByUserId(BigInteger topicId, BigInteger userId, BigInteger groupId, BigInteger grade)
			throws IllegalArgumentException {
		if (topicId == null | userId == null | groupId == null | grade == null) {
			throw new IllegalArgumentException();
		}
		 gradeMapper.insertGroupGradeByUserId(topicId, userId, groupId, grade);

	}

	@Override
	public void updateGroupByGroupId(BigInteger seminarGroupId, BigInteger grade)
			throws GroupNotFoundException, IllegalArgumentException {
		if (seminarGroupId == null | grade == null) {
			throw new IllegalArgumentException();
		}
		int flag = gradeMapper.updateGroupByGroupId(seminarGroupId, grade);
		if (flag==0) {
			throw new GroupNotFoundException();
		}
	}

	@Override
	public void countPresentationGrade(BigInteger seminarId) throws IllegalArgumentException {
		if (seminarId == null) {
			throw new IllegalArgumentException();
		}
		List<SeminarGroup> seminarGroups;
		try {
			seminarGroups = seminarGroupService.listSeminarGroupBySeminarId(seminarId);
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return;
		}
		for (SeminarGroup seminarGroup : seminarGroups) {
			List<SeminarGroupTopic> topics=gradeMapper.listSeminarGroupTopic(seminarGroup.getId());
			List<Integer> topicGrade=new ArrayList<>();
			for (SeminarGroupTopic seminarGroupTopic : topics) {
				List<Integer> grades=gradeMapper.listGradeBySeminarGroupTopicId(seminarGroupTopic.getId());
				if (grades.isEmpty()) {
					break;
				}
				Integer sumGrade = 0;
				for (Integer integer : grades) {
					sumGrade+=integer;
				}
				Integer pGrade=sumGrade/grades.size();
				gradeMapper.insertGroupTopicGradeByUserId(seminarGroupTopic.getId(), pGrade);
				topicGrade.add(pGrade);
			}
			Integer sumTopicGrade = 0;
			for (Integer integer : topicGrade) {
				sumTopicGrade+=integer;
			}
			Integer pTopicGrade=  sumTopicGrade/topicGrade.size();
			gradeMapper.insertGroupPresentationGradeByUserId(seminarGroup.getId(), pTopicGrade);
		}
	}

	@Override
	public void countGroupGradeBySeminarId(BigInteger seminarId) throws IllegalArgumentException {
		if (seminarId == null) {
			throw new IllegalArgumentException();
		}
		countPresentationGrade(seminarId);
	}

}
