package hireSystem.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import hireSystem.service.HireBoardListService;

@Controller
@RequestMapping("/ckEditor")
public class CkEditor5Controller {
	
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;
	
	@Resource(name = "hireBoardListService")
	private HireBoardListService hireBoardListService;
	
	@Resource(name = "commonFileService")
	private CommonFileService commonFileService;
	
	@PostMapping("/upload/image.do")
	@ResponseBody
	public Map<String, Object> uploadImage(
	        @RequestParam("upload") MultipartFile file) throws IOException {
		
		String uploadPath = propertiesService.getString("image.store.path"); // 저장 폴더
	    String storeUrl    = propertiesService.getString("image.store.url");
	    
	    Map<String, Object> result = new HashMap<>();
	    
	    String filename = commonFileService.saveFile(file, uploadPath); //파일저장
	   	    
	    Map<String, Object> insertMap = new HashMap<>();
	    insertMap.put("filePath", storeUrl);
	    insertMap.put("fileName", filename);
	    insertMap.put("status", "TEMP");
	    
	    hireBoardListService.insertTempImgInfo(insertMap); //이미지정보 임시저장
	   
	    // CKEditor 5는 이 형식으로 JSON 반환해야 함 (CK4랑 다름!)
	    result.put("url", storeUrl + filename);
	    
	    return result;  
	}
	
	
}
