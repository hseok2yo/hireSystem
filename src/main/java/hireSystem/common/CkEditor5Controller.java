package hireSystem.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ckEditor")
public class CkEditor5Controller {
	
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;
	
	@PostMapping("/upload/image.do")
	@ResponseBody
	public Map<String, Object> uploadImage(
	        @RequestParam("upload") MultipartFile file) throws IOException {
		
		String uploadPath = propertiesService.getString("image.temp.path"); // 저장 폴더
	    String tempUrl    = propertiesService.getString("image.temp.url");
	    
	    Map<String, Object> result = new HashMap<>();

	    // 파일 저장
	    String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
	    
	    // 폴더 없으면 생성
	    File dir = new File(uploadPath);

	    if (!dir.exists()) {
	        dir.mkdirs();
	    }

	    // 저장
	    Path savePath = Paths.get(uploadPath + filename);
	    Files.write(savePath, file.getBytes());

	    // CKEditor 5는 이 형식으로 JSON 반환해야 함 (CK4랑 다름!)
	    result.put("url", tempUrl + filename);
	    
	    return result;  
	}
	
	
}
