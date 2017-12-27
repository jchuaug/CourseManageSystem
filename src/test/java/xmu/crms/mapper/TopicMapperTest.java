package xmu.crms.mapper;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xmu.crms.CourseManageApplication;
import xmu.crms.entity.SeminarGroupTopic;
import xmu.crms.entity.Topic;
import static xmu.crms.service.EntityTestMethods.*;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourseManageApplication.class)
@Sql(scripts = "classpath:schema.sql")
//todo run sql before test suite
public class TopicMapperTest {
    @Autowired
    TopicMapper topicMapper;

    @Test
    public void getTopicById() {
        Topic topic = topicMapper.getTopicById(BigInteger.valueOf(1));
        testTopic(topic);
    }

    @Test
    public void updateTopic() {
        String randomStr = new Date().toString();
        Topic topic = topicMapper.getTopicById(BigInteger.valueOf(1));
        topic.setDescription(randomStr);

        Boolean result = topicMapper.updateTopic(topic);
        Topic updatedTopic = topicMapper.getTopicById(BigInteger.valueOf(1));

        Assert.assertNotNull(result);
        Assert.assertEquals(updatedTopic.getDescription(), randomStr);
        testTopic(updatedTopic);
    }


    @Test
    public void insertTopicWithSeminarId() {
        Topic topic = topicMapper.getTopicById(BigInteger.valueOf(1));
        String testName = "test String";
        BigInteger seminarId = BigInteger.valueOf(2);

        topic.setName(testName);
        topic.setId(null);

        topicMapper.insertWithSeminarId(seminarId, topic);
        System.out.println(topic.getId());
        Topic insertedTopic = topicMapper.getTopicById(topic.getId());

        Assert.assertNotNull(insertedTopic);
        Assert.assertNotNull(topic.getId());
        Assert.assertEquals(testName, insertedTopic.getName());
        Assert.assertEquals(seminarId, insertedTopic.getSeminar().getId());
        testTopic(insertedTopic);
    }

    @Test
    public void deleteTopic() {
        Topic topic = topicMapper.getTopicById(BigInteger.valueOf(1));
        topic.setId(null);
        BigInteger seminarId = BigInteger.valueOf(2);
        topicMapper.insertWithSeminarId(seminarId, topic);

        // after insert
        BigInteger insertedId = topic.getId();
        topicMapper.deleteById(insertedId);

        Topic t = topicMapper.getTopicById(insertedId);
        Assert.assertEquals(null, t);

    }

    @Test
    public void getTopicsBySeminarId() {
        BigInteger seminarId = BigInteger.valueOf(2);
        List<Topic> topics = topicMapper.getTopicsBySeminarId(seminarId);

        Assert.assertNotNull(topics);
        Assert.assertTrue(topics.size() > 0);

        Topic oneTopic = topics.get(0);
        testTopic(oneTopic);
    }

    @Test
    public void getSeminarGroupTopicInfo() {
        SeminarGroupTopic info = topicMapper.getTopicInfoOfGroup(BigInteger.valueOf(1), BigInteger.valueOf(1));
        Assert.assertEquals(4, info.getPresentationGrade().intValue());
        Assert.assertEquals(1, info.getTopic().getId().intValue());
        Assert.assertEquals(1, info.getSeminarGroup().getId().intValue());
    }

    @Test
    public void deleteSeminarGroupTopicById() {
        BigInteger topicId = BigInteger.valueOf(1);
        BigInteger groupId = BigInteger.valueOf(1);


        SeminarGroupTopic info = topicMapper.getTopicInfoOfGroup(topicId, groupId);
        Assert.assertNotNull(info);

        boolean result = topicMapper.deleteSeminarGroupTopic(topicId, groupId);
        info = topicMapper.getTopicInfoOfGroup(topicId, groupId);

        Assert.assertEquals(true, result);
        Assert.assertEquals(null, info);
        //todo test seminar group topic
    }

    @Test
    public void deleteAllSeminarGroupTopicsByTopicId() {
        BigInteger topicId = BigInteger.valueOf(1);

        // this number is correspond to schema.sql
        int topicNum = 12;

        int deletedNum = topicMapper.deleteAllSeminarGroupTopicsByTopicId(topicId);

        Assert.assertEquals(topicNum, deletedNum);
    }


    @Test
    public void getSeminarGroupTopicsByGroupId() {
        BigInteger groupId = BigInteger.valueOf(1);

        List<SeminarGroupTopic> topics = topicMapper.getChosenTopicByGroupId(groupId);

        Assert.assertNotNull(topics);
        Assert.assertTrue(topics.size() > 0);
    }

    @Test
    public void deleteAllChosenTopics() {
        BigInteger groupId = BigInteger.valueOf(1);
        topicMapper.deleteChosenTopicByGroupId(groupId);

        List<SeminarGroupTopic> topics = topicMapper.getChosenTopicByGroupId(groupId);
        Assert.assertEquals(0, topics.size());
    }


//    @Test
//    @DirtiesContext
//    public void deleteTopicBySeminarId() {
//        BigInteger seminarId = BigInteger.valueOf(1);
//
//        List<Topic> topics = topicMapper.getTopicsBySeminarId(seminarId);
//        int oriSize = topics.size();
//
//        topicMapper.deleteTopicEverywhere(seminarId);
//
//        List<Topic> afterDeleteTopics = topicMapper.getTopicsBySeminarId(seminarId);
//        assert (oriSize > 0);
//        Assert.assertEquals(0, afterDeleteTopics.size());
//
//    }


}
