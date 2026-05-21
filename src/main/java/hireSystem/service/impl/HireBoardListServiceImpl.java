package hireSystem.service.impl;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.stereotype.Service;

import hireSystem.service.HireBoareListService;
import hireSystem.service.mapper.HireBoareListMapper;
import hireSystem.vo.BoardWriteVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hireBoareListService")
public class HireBoardListServiceImpl extends EgovAbstractServiceImpl implements HireBoareListService {
	
	@Resource(name = "hireBoareListMapper")
	private HireBoareListMapper boardListMapper;
	
	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;

	@Override
	public int boardInsert(BoardWriteVo writeVo) {
	    log.info("=== boardInsert 서비스 시작 ===");
	    log.info("저장 전 content: {}", writeVo.getContent());
	    
	    // ① content에서 temp 이미지 URL 추출 & 파일 이동 & URL 교체
	    String content = writeVo.getContent();
	    content = moveTempImagesToStore(content);
	    writeVo.setContent(content);
	    
	    log.info("URL 교체 후 content: {}", writeVo.getContent());
	    
	    int result = boardListMapper.boardInsert(writeVo);
	    log.info("DB insert 결과: {}", result);
	    log.info("=== boardInsert 서비스 종료 ===");
	    
	    return result;
	}
	
	private String moveTempImagesToStore(String content) {
		String tempPath  = propertiesService.getString("image.temp.path");
	    String storePath = propertiesService.getString("image.store.path");
	    String storeUrl  = propertiesService.getString("image.store.url");

	    log.info("=== moveTempImagesToStore 시작 ===");

	    // store 폴더 없으면 생성
	    File storeDir = new File(storePath);
	    if (!storeDir.exists()) {
	        storeDir.mkdirs();
	        log.info("store 폴더 새로 생성: {}", storePath);
	    } else {
	        log.info("store 폴더 이미 존재: {}", storePath);
	    }
	    
	    //Pattern은 텍스트에서 특정 모양의 문자열을 찾는 도구
	    Pattern pattern = Pattern.compile("/image/temp\\.do\\?filename=([^\"'\\s]+)");
	    //Pattern(찾을 규칙)을 가지고 content(찾을 대상 텍스트)에 실제로 적용할 준비를
	    Matcher matcher = pattern.matcher(content);

	    StringBuffer sb = new StringBuffer();
	    int count = 0;
	    
	    //matcher.find();      // 패턴 찾기
	    //matcher.group(0);    // 찾은 전체
	    //matcher.group(1);    // 첫번째 () 안
	    //matcher.group(2);    // 두번째 () 안
	    while (matcher.find()) {
	        count++;
	        String filename = matcher.group(1);
	        log.info("[{}번째 이미지] filename: {}", count, filename);
	        
	        // → 이 경로를 가리키는 객체 생성
	        File tempFile  = new File(tempPath + filename);
	        File storeFile = new File(storePath + filename);
	        
	        
	        if (tempFile.exists()) { //파일이 있으면
	            boolean moved = tempFile.renameTo(storeFile); //경로가 같으면 이름 바꾸기, 경로가 다르면 이동
	            if (moved) {
	                log.info("[{}번째 이미지] 이동 성공 → {}", count, storeFile.getAbsolutePath());
	            } else {
	                log.warn("[{}번째 이미지] 이동 실패!", count);
	            }
	        } else {
	            log.warn("[{}번째 이미지] temp에 파일 없음: {}", count, tempFile.getAbsolutePath());
	        }

	        String newUrl = storeUrl + filename;
	        //group(0)은 이번에 찾은 URL 전체예요.
	        log.info("[{}번째 이미지] URL 교체: {} → {}", count, matcher.group(0), newUrl);
	        matcher.appendReplacement(sb, newUrl); //찾은 부분(group(0)) 대신 newUrl을 sb에 넣기
	    }
	    matcher.appendTail(sb); //마지막으로 찾은 URL 이후의 나머지 텍스트를 sb에 붙임

	    log.info("총 {}개 이미지 처리 완료", count);
	    log.info("=== moveTempImagesToStore 종료 ===");

	    return sb.toString();
	}
	
	
	
	
	
}
