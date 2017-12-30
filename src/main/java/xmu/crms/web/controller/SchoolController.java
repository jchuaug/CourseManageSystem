package xmu.crms.web.controller;


/**
 * @author caistrong
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping(value = "/{city}")
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
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(schools);
    }


    @RequestMapping(method = POST)
    @ResponseBody
    public ResponseEntity addSchool(@RequestBody SchoolRequestVO schoolRequest) {

        School school = new School(null, schoolRequest.getName(), schoolRequest.getProvince(), schoolRequest.getCity());
        BigInteger id = schoolService.insertSchool(school);
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON_UTF8).body(id);
    }


    @RequestMapping(value = "/province", method = GET)
    @ResponseBody
    public ResponseEntity getProvince() {
        List<String> provinces = new ArrayList<String>();
        provinces = schoolService.listProvince();
        return ResponseEntity.status(200).contentType(MediaType.APPLICATION_JSON_UTF8).body(provinces);
    }


    @PostMapping(value = "/province/city")
    @ResponseBody
    public ResponseEntity getCity(@RequestBody SchoolRequestVO req) {
        List<String> citys;
        citys = schoolService.listCity(req.getProvince());
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
