package xmu.crms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Demo UploadController
 *
 * @author drafting_dreams
 * @date 2017/12/29
 */
@Controller
public class UploadController {
	// @Autowired
	// UploadService uploadService;

	@RequestMapping(value = "/upload/avatar", method = RequestMethod.POST)
	@ResponseBody
	public String upload(/*@RequestParam("file") MultipartFile file,*/HttpServletRequest req,
		      MultipartHttpServletRequest multiReq) {
		// 获取上传文件的路径
	    String uploadFilePath = multiReq.getFile("file").getOriginalFilename();
	    System.out.println("uploadFlePath:" + uploadFilePath);
	    // 截取上传文件的文件名
	    String uploadFileName = uploadFilePath.substring(
	        uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));
	    System.out.println("multiReq.getFile()" + uploadFileName);
	    // 截取上传文件的后缀
	    String uploadFileSuffix = uploadFilePath.substring(
	        uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
	    System.out.println("uploadFileSuffix:" + uploadFileSuffix);
	    FileOutputStream fos = null;
	    FileInputStream fis = null;
	    try {
	      fis = (FileInputStream) multiReq.getFile("file").getInputStream();
	      fos = new FileOutputStream(new File("src/main/resources/static/file//" + uploadFileName
	          + ".")
	          + uploadFileSuffix);
	      byte[] temp = new byte[1024];
	      int i = fis.read(temp);
	      while (i != -1){
	        fos.write(temp,0,temp.length);
	        fos.flush();
	        i = fis.read(temp);
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      if (fis != null) {
	        try {
	          fis.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	      if (fos != null) {
	        try {
	          fos.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    }
		return uploadFileSuffix;
	  }
		
		
	
		/*if (!file.isEmpty()) {

			try { 
				// 这里只是简单例子，文件直接输出到项目路径下。 // 实际项目中，文件需要输出到指定位置，需要在增加代码处理。 //
					// 还有关于文件格式限制、文件大小限制，详见：中配置。
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(new File(file.getOriginalFilename())));
				out.write(file.getBytes());
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				return "上传失败," + e.getMessage();
			}
			return "上传成功";
		} else {
			return "上传失败，因为文件是空的.";
		}
	}*/

	@RequestMapping(value = "/download/{filename}", method = RequestMethod.GET)
	public void testDownload(@PathVariable("filename") String fileName, HttpServletResponse res) {
		// fileName =fileName+"/report.word";
		fileName = "report.doc";
		res.setHeader("content-type", "application/octet-stream");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		byte[] buff = new byte[1024];
		BufferedInputStream bis = null;
		OutputStream os = null;
		try {
			os = res.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("success");
	}
	
	
}
