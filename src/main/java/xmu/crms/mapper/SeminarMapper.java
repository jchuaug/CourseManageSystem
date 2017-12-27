package xmu.crms.mapper;

import xmu.crms.entity.Course;
import xmu.crms.entity.Seminar;

import java.math.BigInteger;
import java.util.List;

/**
 * @author: drafting_dreams
 */
public interface SeminarMapper {
    /**
     * 根据课程ID查找该课程下的所有讨论课，返回讨论课列表
     *
     * @param courseId courseId
     * @return List of Seminar
     */
    List<Seminar> listSeminarByCourseId(BigInteger courseId);


    /**
     * 插入讨论课信息，该讨论课属于的课程为 courseId 所指定的课程
     *
     * @param seminar seminar 信息
     * @return 新建讨论课的 id， 失败返回 -1
     */
    Long insertSeminarByCourseId(Seminar seminar);

    /**
     * 删除 courseId 对应的课程下的所有讨论课信息，先删除 semianr 下的 topic ，再把相应的 SeminarGroup 删除
     *
     * @param courseId courseId
     * @return 是否删除成功
     */
    Boolean deleteSeminarByCourseId(BigInteger courseId);

    /**
     * delete seminar by seminarId
     *
     * @param seminarId seminarId
     * @return 如果成功删除返回 true，如果没有找到 seminarId 导致删除失败 返回 false
     */
    Boolean deleteSeminarBySeminarId(BigInteger seminarId);

    /**
     * update seminar by seminar ID
     *
     * @return 如果成功更新返回 true，如果没有找到 seminarId 导致更新失败 返回 false
     */
    Boolean updateSeminarBySeminarId(Seminar seminar);

    /**
     * get seminar by seminar ID
     *
     * @param seminarId seminarId
     * @return Seminar
     */
    Seminar getSeminarBySeminarId(BigInteger seminarId);

    /**
     * delete topic by seminar id
     *
     * @param id seminar id
     */
    void deleteTopicBySeminarId(BigInteger id);

    /**
     * delete seminar group by seminar id
     *
     * @param id seminar
     */
    void deleteSeminarGroupBySeminarId(BigInteger id);

    /**
     * get course by course id
     *
     * @param courseId course id
     * @return the course
     */
    Course getCourseById(BigInteger courseId);
}
