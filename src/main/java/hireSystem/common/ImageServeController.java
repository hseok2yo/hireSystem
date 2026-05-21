package hireSystem.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ImageServeController {
	
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;
		
	/**
	 * ckeditor 이미지업로드
	 * @param filename 파일명
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/image/temp.do")
	public void serveImage(@RequestParam String filename,
	                       HttpServletResponse response) throws Exception {
		
		String tempPath = propertiesService.getString("image.temp.path");
		
	    File file = new File(tempPath + filename);

	    String contentType = Files.probeContentType(file.toPath());

	    response.setContentType(contentType);

	    FileInputStream fis = new FileInputStream(file);
	    OutputStream os = response.getOutputStream();

	    byte[] buffer = new byte[1024];
	    int length;

	    while ((length = fis.read(buffer)) != -1) {
	        os.write(buffer, 0, length);
	    }

	    os.flush();

	    fis.close();
	    os.close();
	}
}