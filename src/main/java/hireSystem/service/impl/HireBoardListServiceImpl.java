package hireSystem.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hireSystem.common.CommonFileService;
import hireSystem.common.PagingUtil;
import hireSystem.service.HireBoardListService;
import hireSystem.service.mapper.BoardImageMapper;
import hireSystem.service.mapper.HireBoardListMapper;
import hireSystem.vo.BoardWriteVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("hireBoardListService")
public class HireBoardListServiceImpl extends EgovAbstractServiceImpl implements HireBoardListService {

	@Resource(name = "hireBoardListMapper")
	private HireBoardListMapper boardListMapper;

	@Resource(name = "boardImageMapper")
	private BoardImageMapper boardImageMapper;

	@Resource(name = "propertiesService")
	private EgovPropertyService propertiesService;

	@Resource(name = "commonFileService")
	private CommonFileService commonFileService;


	@Override
	public Map<String, Object> selectList(BoardWriteVo getVo) {

		Map<String , Object> result = new HashMap<>();

		if(getVo.getPage() == 0) getVo.setPage(1);
		int page = getVo.getPage();

		int pageSize = 5; //가져올 게시글 수
		int offset = (page - 1) * pageSize;
		int blockSize = 5; //페이징 수

		getVo.setPageSize(pageSize);
		getVo.setOffset(offset);

		List<BoardWriteVo> selectList = boardListMapper.selectList(getVo); //게시글 조회
		int totalCount = boardListMapper.selectTotalCount(getVo); //총 게시글 수

		result = PagingUtil.getPaging(getVo.getPage(), totalCount, pageSize, blockSize);
	    result.put("list", selectList);

		return result;
	}

	@Override
	public BoardWriteVo selectDetail(int boardNum) {
		boardListMapper.updateDetailCnt(boardNum); //조회수 +1

		return boardListMapper.selectDetail(boardNum);
	}

	@Override
	public BoardWriteVo selectDetailForEdit(int boardNum) {
		return boardListMapper.selectDetail(boardNum);
	}


	@Override
	public int boardInsert(BoardWriteVo writeVo, HttpSession session) {

	    log.info("=== boardInsert 서비스 시작 ===");

	    // 1. 로그인 체크
	    Integer loginUserNum = (Integer) session.getAttribute("loginUserNum");
	    log.info("로그인 유저 번호: {}", loginUserNum);
	    if (loginUserNum == null) {
	        log.warn("로그인 정보 없음 → 0 반환");
	        return 0;
	    }

	    writeVo.setWriter((String) session.getAttribute("loginNm"));
	    writeVo.setUserNum(loginUserNum);
	    log.info("작성자: {}, 유저번호: {}", writeVo.getWriter(), writeVo.getUserNum());
	    log.info("제목: {}", writeVo.getTitle());
	    log.info("카테고리: {}", writeVo.getCategory());
	    log.info("content: {}", writeVo.getContent());

	    // 2. board INSERT
	    int result = boardListMapper.boardInsert(writeVo);
	    int boardNum = writeVo.getBoardNum(); // useGeneratedKeys로 생성된 PK
	    log.info("생성된 boardNum: {}", boardNum);

	    // 3. 프론트에서 받은 파일명 목록 확인
	    List<String> filenames = writeVo.getFilenames();
	    log.info("전달받은 filenames: {}", filenames);
	    // 출력 예시: [uuid_photo1.jpg, uuid_photo2.jpg]

	    // 4. board_image 업데이트
	    if (filenames != null && !filenames.isEmpty()) {
	        log.info("board_image 업데이트 시작 - boardNum: {}, 파일 수: {}", boardNum, filenames.size());
	        boardImageMapper.updateBoardNumAndStatus(filenames, boardNum);
	        log.info("board_image 업데이트 완료 → BOARD_NUM={}, STATUS=STORE", boardNum);
	    } else {
	        log.info("등록된 이미지 없음 → board_image 업데이트 스킵");
	    }

	    log.info("=== boardInsert 서비스 종료 ===");
	    return result;
	}

	@Override
	public int insertTempImgInfo(Map<String, Object> insertMap) {

		return boardImageMapper.insertTempImgInfo(insertMap);
	}

	@Transactional("hireSystemTxManager")
	@Override
	public int boardUpdate(BoardWriteVo writeVo) {

		log.info("=== boardUpdate 서비스 시작 ===");
	    log.info("boardNum: {}", writeVo.getBoardNum());

	    List<String> currentFilenames = writeVo.getFilenames(); // 에디터에 현재 남아있는 파일명
	    log.info("에디터에 남아있는 파일명: {}", currentFilenames);

    	// 1. DB에서 이 게시글의 기존 STORE 이미지 목록 조회
	    List<String> storedFilenames = boardImageMapper.selectFilenamesByBoardNum(writeVo.getBoardNum());
    	log.info("DB 기존 파일명: {}", storedFilenames);


    	// 2. diff 계산 (공통 서비스)
        Map<String, List<String>> diff = commonFileService.diffImages(currentFilenames, storedFilenames);
        List<String> toDelete = diff.get("toDelete");
        List<String> toAdd    = diff.get("toAdd");
        log.info("삭제 대상: {}, 추가 대상: {}", toDelete, toAdd);


        // 3. DB 처리 (board_image 전용)
	    if (currentFilenames == null || currentFilenames.isEmpty()) {
	        boardImageMapper.deleteByBoardNum(writeVo.getBoardNum()); // 전체삭제
	    } else {
	        for (String f : toDelete) {
	            boardImageMapper.deleteByFilename(f);
	        }
	    }

	    if (!toAdd.isEmpty()) {
	        boardImageMapper.updateBoardNumAndStatus(toAdd, writeVo.getBoardNum());
	    }

	    // 4. board UPDATE
	    int result = boardListMapper.boardUpdate(writeVo);
	    log.info("boardUpdate 결과: {}", result);

	    // 5. 파일 삭제 (공통 서비스)
	    String storePath = propertiesService.getString("community.store.path");
	    commonFileService.deleteFiles(storePath, toDelete);

	    log.info("=== boardUpdate 서비스 종료 ===");


		return 1;
	}

	@Override
	public int boardDelete(int boardNum) {
		List<String> storedFilenames = boardImageMapper.selectFilenamesByBoardNum(boardNum);

		// 이미지 DB 삭제
	    boardImageMapper.deleteByBoardNum(boardNum);

	    // 게시글 DB 삭제
		int result = boardListMapper.boardDelete(boardNum);

		// 파일 삭제 (공통 서비스)
	    String storePath = propertiesService.getString("community.store.path");
	    commonFileService.deleteFiles(storePath, storedFilenames);

		return result;
	}




}
