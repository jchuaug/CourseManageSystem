package xmu.crms.web.controller;


/**
 *
 * @author caistrong
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xmu.crms.entity.School;
import xmu.crms.service.SchoolService;
import xmu.crms.web.VO.SchoolRequestVO;


import javax.websocket.server.PathParam;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/school")
public class SchoolController {
     @Autowired
    SchoolService schoolService;


    @RequestMapping(method = GET)
    @ResponseBody
    public ResponseEntity getSchoolList(@PathParam("city") String city) {
        List<School> schools = schoolService.listSchoolByCity(city);
//        String school = "[\n" +
//                "  {\n" +
//                "    \"id\": 32,\n" +
//                "    \"name\": \"厦门大学\",\n" +
//                "    \"province\": \"福建\",\n" +
//                "    \"city\": \"厦门\"\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 37,\n" +
//                "    \"name\": \"厦门软件学院\",\n" +
//                "    \"province\": \"福建\",\n" +
//                "    \"city\": \"厦门\"\n" +
//                "  }\n" +
//                "]";
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON_UTF8).body(schools);
    }


    @RequestMapping(method = POST)
    @ResponseBody
    public ResponseEntity addSchool(@RequestBody SchoolRequestVO addSchoolVO) {

        School school = new School(null, addSchoolVO.getName(), addSchoolVO.getProvince(), addSchoolVO.getCity());
        BigInteger id = schoolService.insertSchool(school);
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON_UTF8).body(id);
    }


    @RequestMapping(value = "/province", method = GET)
    @ResponseBody
    public ResponseEntity getProvince() {
        List<String> provinces = new ArrayList<String>();
        provinces = schoolService.listProvince();
//        String province = "[\n" +
//                "  \"北京\",\n" +
//                "  \"天津\",\n" +
//                "  \"河北省\",\n" +
//                "  \"……\",\n" +
//                "  \"澳门特别行政区\"\n" +
//                "]";
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(provinces);
    }


    @RequestMapping(value = "/city", method = GET)
    @ResponseBody
    public  ResponseEntity getCity(@PathParam("province") String province) {
        List<String> citys = new ArrayList<String>();
        citys = schoolService.listCity(province);
//        String city = "[\n" +
//                "  \"北京\",\n" +
//                "  \"天津\",\n" +
//                "  \"河北省\",\n" +
//                "  \"……\",\n" +
//                "  \"澳门特别行政区\"\n" +
//                "]";
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(citys);
    }
}
