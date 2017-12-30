package xmu.crms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.Topic;
import xmu.crms.exception.TopicNotFoundException;
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.TopicService;
import xmu.crms.web.VO.TopicGroupVO;
import xmu.crms.web.VO.TopicResponseVO;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
/**
 * TopicController class
 *
 * @author drafting_dreams
 * @date 2017/12/29
 */

@Controller
@RequestMapping("/topic/{topicID}")
public class TopicController {

    @Autowired
    TopicService topicService;

    @Autowired
    SeminarGroupService seminarGroupService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getTopicByID (@PathVariable BigInteger topicID) {
        TopicResponseVO topicResponse = new TopicResponseVO();
        // if topicId bad format response with 400

        try {
            Topic topic = topicService.getTopicByTopicId(topicID);
            if (topic == null) {
                return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON_UTF8).body(null);
            }
            topicResponse.setId(topic.getId());
            topicResponse.setSerial(topic.getSerial());
            topicResponse.setName(topic.getName());
            topicResponse.setDescription(topic.getDescription());
            topicResponse.setGroupLimit(topic.getGroupNumberLimit());
            topicResponse.setGroupMemberLimit(topic.getGroupStudentLimit());
           // topicResponse.setGroupLeft(topic.get); Service doesn't have groupLeft property
        } catch (TopicNotFoundException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(topicResponse);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateTopicByID (@RequestBody TopicResponseVO requestBody, @PathVariable BigInteger topicID) {
        // if insufficient permissions return 403
        Topic topic = new Topic();
        topic.setSerial(requestBody.getSerial());
        topic.setName(requestBody.getName());
        topic.setDescription(requestBody.getDescription());
        topic.setGroupNumberLimit(requestBody.getGroupLimit());
        topic.setGroupStudentLimit(requestBody.getGroupMemberLimit());
        try {
            topicService.updateTopicByTopicId(topicID, topic);
            return ResponseEntity.status(204).contentType(MediaType.APPLICATION_JSON_UTF8).body(null);
        } catch (TopicNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON_UTF8).body(null);
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON_UTF8).body(null);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity deleteTopicByID (@PathVariable BigInteger topicID) {
        // if insufficient permissions return 403

        try {
            topicService.deleteTopicByTopicId(topicID);
            return ResponseEntity.status(204).contentType(MediaType.APPLICATION_JSON_UTF8).body(null);
        } catch (TopicNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON_UTF8).body(null);
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON_UTF8).body(null);
        }
    }

    @RequestMapping(value = "/group")
    @ResponseBody
    public ResponseEntity selectGroupsByTopicID (@PathVariable BigInteger topicID) {
        try {
            List<SeminarGroup> groups = seminarGroupService.listGroupByTopicId(topicID);
            List<TopicGroupVO> groupVOS = new ArrayList<>();
            for(SeminarGroup group:groups) {
                TopicGroupVO temp = new TopicGroupVO();
                temp.setId(group.getId());
                temp.setName(group.getId().toString());
                groupVOS.add(temp);
            }
            if(groupVOS.size() == 0 || groupVOS == null) {
                return ResponseEntity.status(404).build();
            }
            return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(groupVOS);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(400).build();
        }
        // why not throw a notFound exception so I can use that to return 404
    }

}
