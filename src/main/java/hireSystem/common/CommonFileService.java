package hireSystem.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("commonFileService")
public class CommonFileService {
	
	/**
	 * @param file 저장할 파일
	 * @param savePath 저장경로
	 * @return 파일명
	 * @throws IOException
	 */
	public String saveFile(MultipartFile file, String savePath) throws IOException {
        String originalName = file.getOriginalFilename()
                                  .replaceAll("[^a-zA-Z0-9가-힣._-]", "_");
        String filename = UUID.randomUUID() + "_" + originalName;
        
        File dir = new File(savePath);
        if (!dir.exists()) dir.mkdirs();
        
        Files.write(Paths.get(savePath + filename), file.getBytes());
        log.info("[CommonFileService] 파일 저장 완료: {}", filename);
        return filename;
    }
	
	// 파일 단건 삭제
    public boolean deleteFile(String savePath, String filename) {
        File file = new File(savePath + filename);
        if (file.exists()) {
            boolean deleted = file.delete();
            log.info("[CommonFileService] 파일 삭제: {} → {}", filename, deleted ? "성공" : "실패");
            return deleted;
        } else {
            log.warn("[CommonFileService] 파일 없음: {}", filename);
            return true;
        }
    }
    // 파일 여러개 삭제
    public void deleteFiles(String savePath, List<String> filenames) {
        if (filenames == null || filenames.isEmpty()) {
            log.info("[CommonFileService] 삭제할 파일 없음");
            return;
        }
        log.info("[CommonFileService] 파일 {}개 삭제 시작", filenames.size());
        for (String f : filenames) {
            deleteFile(savePath, f);
        }
        log.info("[CommonFileService] 파일 삭제 완료");
    }
    
    /**
     * 수정 시 삭제할 파일명, 새로 추가된 파일명 계산만 해줌
     * @param currentFilenames 에디터에 현재 남아있는 파일명
     * @param storedFilenames DB에서 조회한 기존 파일명
     * @return toDelete : 삭제할 파일명리스트/ toAdd: 추가할 파일명리스트
     */
    public Map<String, List<String>> diffImages(
            List<String> currentFilenames,
            List<String> storedFilenames) {
        
        log.info("[CommonFileService] diffImages 시작 - 에디터: {}, DB: {}", currentFilenames, storedFilenames);
        
        List<String> toDelete = new ArrayList<>();
        List<String> toAdd    = new ArrayList<>();
        
        if (currentFilenames == null || currentFilenames.isEmpty()) {
            log.info("[CommonFileService] 이미지 전부 삭제된 케이스");
            toDelete.addAll(storedFilenames);
        } else {
            for (String f : storedFilenames) {
                if (!currentFilenames.contains(f)) toDelete.add(f);
            }
            for (String f : currentFilenames) {
                if (!storedFilenames.contains(f)) toAdd.add(f);
            }
        }
        
        log.info("[CommonFileService] diffImages 결과 - 삭제: {}, 추가: {}", toDelete, toAdd);
        
        Map<String, List<String>> result = new HashMap<>();
        result.put("toDelete", toDelete);
        result.put("toAdd", toAdd);
        return result;
    }
	
}
