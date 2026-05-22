package hireSystem.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String boardList(@ModelAttribute BoardWriteVo getVo, Model model) {
		
		Map<String, Object> result = hireBoareListService.selectList(getVo);
		
		model.addAttribute("list", result.get("list"));
		model.addAttribute("displayNo", result.get("displayNo"));
		model.addAttribute("startPage", result.get("startPage"));
		model.addAttribute("endPage", result.get("endPage"));
		model.addAttribute("totalPage", result.get("totalPage"));
		model.addAttribute("currentPage", result.get("currentPage"));
		
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
	public EgovMap boardInsert(@ModelAttribute BoardWriteVo writeVo
			, HttpSession session) {
		EgovMap result = new EgovMap();
		int insertCnt = hireBoareListService.boardInsert(writeVo, session);
		if(insertCnt > 0 ) {
			result.put("result", "success");
		}else {
			result.put("result", "fail");
		}
		
		return result;
	}
	
	@RequestMapping("/boardDetail.do")
	public String boardDetail(@RequestParam int boardNum, Model model) {
		model.addAttribute("board", hireBoareListService.selectDetail(boardNum));
		return path + "boardDetail";
	}

	@RequestMapping("/boardEdit.do")
	public String boardEdit(@RequestParam int boardNum, Model model) {
		model.addAttribute("board", hireBoareListService.selectDetailForEdit(boardNum));
		return path + "boardEdit";
	}
	
}
