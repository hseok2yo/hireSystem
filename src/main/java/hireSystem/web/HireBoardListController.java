package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hireSystem.service.HireBoareListService;
import hireSystem.vo.BoardWriteVo;

@Controller
@RequestMapping("/hireSystem/board")
public class HireBoardListController {
	private final String path = "hireSystem/board/";
	
	@Resource(name = "hireBoareListService")
	private HireBoareListService hireBoareListService;
	
	
	@RequestMapping("/boardList.do")
	public String boardList() {
		return path + "boardList";
	}
	
	/**
	 * 인터셉터에서 로그인됐는지 확인되면 json으로 확인데이터 쏴줌(rest api연습용)
	 * @param session 인터셉터
	 * @return
	 */
	@RequestMapping("/boardWriteCheck.do")
	@ResponseBody
	public Map<String, String> boardWrite(HttpSession session) {
		
		Map<String, String> result = new HashMap<>();
	    result.put("result", "yesUser"); // 여기 오면 무조건 로그인된것(인터셉터에서 체크중)
	    return result;
	}
	
	/**
	 * @return 글쓰기 페이지 이동
	 */
	@RequestMapping("/boardWrite.do")
	public String boardWrite() {
		return path + "boardWrite";
	}
	
	@PostMapping("/boardInsert.do")
	@ResponseBody
	public EgovMap boardInsert(@ModelAttribute BoardWriteVo writeVo) {
		EgovMap result = new EgovMap();
		int insertCnt = hireBoareListService.boardInsert(writeVo);
		if(insertCnt > 0 ) {
			result.put("result", "success");
		}else {
			result.put("result", "fail");
		}
		
		return result;
	}
}
