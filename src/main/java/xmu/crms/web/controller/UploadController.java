package xmu.crms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xmu.crms.service.UploadService;
import xmu.crms.web.VO.UploadAvatarVO;

import javax.servlet.http.HttpServletRequest;

/**
 * Demo UploadController
 *
 * @author drafting_dreams
 * @date 2017/12/29
 */
@Controller
@RequestMapping("/upload")
public class UploadController {
//    @Autowired
//    UploadService uploadService;

    @RequestMapping(value = "/avatar")
    public void uploadAvatar(@RequestBody UploadAvatarVO file, HttpServletRequest request) {

    }
}
