package music.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

	@RequestMapping("/file.do")
	public String file() {
		return "/musicpractice/file";
	}

	@RequestMapping("/fileDivideDown.do")
	public String fileDivideDown() {
		return "/musicpractice/fileDivideDown";
	}

	/**
	 * 알집다운
	 * @param files
	 * @param removeText
	 * @param response
	 */
	 @PostMapping("/fileZip.do")
	    public void fileZip(@RequestParam("files") List<MultipartFile> files,
	                             @RequestParam("removeText") String removeText,
	                             HttpServletResponse response) {
	        try {
	            response.setContentType("application/zip");
	            String encodedFilename = URLEncoder.encode("파일.zip", "UTF-8").replaceAll("\\+", "%20");
	            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''"+encodedFilename);

	            try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
	                for (MultipartFile file : files) {
	                    String fileName = file.getOriginalFilename();
	                    if (fileName != null && (fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".flac"))) {
	                        String newFileName = fileName.replace(removeText, "");
	                        ZipEntry zipEntry = new ZipEntry(newFileName);
	                        zos.putNextEntry(zipEntry);

	                        byte[] buffer = new byte[1024];
	                        try (InputStream fis = file.getInputStream()) {
	                            int length;
	                            while ((length = fis.read(buffer)) > 0) {
	                                zos.write(buffer, 0, length);
	                            }
	                        }
	                        zos.closeEntry();
	                    }
	                }
	            }
	            response.flushBuffer();
	        } catch (IOException e) {
	            e.printStackTrace();
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            try {
	                response.getWriter().write("파일 처리 중 오류가 발생했습니다: " + e.getMessage());
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

	 /**
	  * 하나일 땐 하나/여러개 일 경우 여러개 다운
	  * @param files
	  * @param removeText
	  * @param response
	  */
	 @PostMapping("/fileSingleMultidown.do")
	 public void fileSingleMultidown(@RequestParam("files") List<MultipartFile> files,
	                          @RequestParam("removeText") String removeText,
	                          HttpServletResponse response) {
	     try {
	         // 파일이 하나일 경우
	         if (files.size() == 1) {
	             MultipartFile file = files.get(0);
	             String fileName = file.getOriginalFilename();
	             if (fileName != null && (fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".flac"))) {
	            	 String newFileName = fileName.replace(removeText, "").trim();
	                 String encodedFilename = URLEncoder.encode(newFileName, "UTF-8").replaceAll("\\+", "%20");

	                 response.setContentType(file.getContentType());
	                 response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename);

	                 try (InputStream fis = file.getInputStream(); OutputStream os = response.getOutputStream()) {
	                     byte[] buffer = new byte[1024];
	                     int length;
	                     while ((length = fis.read(buffer)) > 0) {
	                         os.write(buffer, 0, length);
	                     }
	                     os.flush();
	                 }
	             }
	         } else {
	             // 파일이 여러 개일 경우 ZIP 파일로 압축
	             response.setContentType("application/zip");
	             String encodedFilename = URLEncoder.encode("파일.zip", "UTF-8").replaceAll("\\+", "%20");
	             response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename);

	             try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
	                 for (MultipartFile file : files) {
	                     String fileName = file.getOriginalFilename();
	                     if (fileName != null && (fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".flac"))) {
	                    	 String newFileName = fileName.replace(removeText, "").trim();
	                         ZipEntry zipEntry = new ZipEntry(newFileName);
	                         zos.putNextEntry(zipEntry);

	                         byte[] buffer = new byte[1024];
	                         try (InputStream fis = file.getInputStream()) {
	                             int length;
	                             while ((length = fis.read(buffer)) > 0) {
	                                 zos.write(buffer, 0, length);
	                             }
	                         }
	                         zos.closeEntry();
	                     }
	                 }
	             }
	             response.flushBuffer();
	         }
	     } catch (IOException e) {
	         e.printStackTrace();
	         response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	         try {
	             response.getWriter().write("파일 처리 중 오류가 발생했습니다: " + e.getMessage());
	         } catch (IOException ex) {
	             ex.printStackTrace();
	         }
	     }
	 }


	 /**
	  * 파일이 여러개여도 하나씩 다운되도록
	  * @param files
	  * @param removeText
	  * @param response
	  */
	 @PostMapping("/fileSingledown.do")
	 public void fileSingledown(@RequestParam("files") List<MultipartFile> files,
	                            @RequestParam("removeText") String removeText,
	                            HttpServletResponse response) {
	     try {
	         // 파일 리스트를 순회하며 각 파일에 대해 처리
	         for (MultipartFile file : files) {
	             String fileName = file.getOriginalFilename();

	             // 파일명이 null이 아니고, 지정한 확장자를 포함한 경우 처리
	             if (fileName != null && (fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".flac"))) {

	                 // removeText를 파일명에서 제거하고 공백을 제거
	                 String newFileName = fileName.replace(removeText, "").trim();

	                 // 파일명 인코딩
	                 String encodedFilename = URLEncoder.encode(newFileName, "UTF-8").replaceAll("\\+", "%20");

	                 // 응답 헤더 설정 (파일 다운로드를 위한 설정)
	                 response.setContentType(file.getContentType());
	                 response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename);

	                 // 파일 내용을 HTTP 응답으로 전송
	                 try (InputStream fis = file.getInputStream(); OutputStream os = response.getOutputStream()) {
	                     byte[] buffer = new byte[1024];
	                     int length;
	                     while ((length = fis.read(buffer)) > 0) {
	                         os.write(buffer, 0, length);
	                     }
	                     os.flush();
	                 }

	             }
	         }
	     } catch (IOException e) {
	         e.printStackTrace();
	         response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	         try {
	             response.getWriter().write("파일 처리 중 오류가 발생했습니다: " + e.getMessage());
	         } catch (IOException ex) {
	             ex.printStackTrace();
	         }
	     }
	 }



	// 파일의 MIME 타입 결정
    private String determineMimeType(String fileName) {
        if (fileName.endsWith(".mp3")) {
            return "audio/mpeg";
        } else if (fileName.endsWith(".wav")) {
            return "audio/wav";
        } else if (fileName.endsWith(".flac")) {
            return "audio/flac";
        }
        return "application/octet-stream"; // 기본 MIME 타입
    }
}
