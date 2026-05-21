package practice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import practice.service.PostPracticeService;
import practice.service.dao.PostPracticeDao;
import practice.vo.PostPracticeVo;

@Service("PostPracticeService")
public class PostPracticeServiceImpl extends EgovAbstractServiceImpl implements PostPracticeService{
	
	@Resource(name = "PostPracticeDao")
	private PostPracticeDao dao;
	
	@Override
	public List<EgovMap> selectList(PostPracticeVo vo, ModelMap model) {
		//초기 페이지 접속 시
		if(vo.getPageNum() < 1) {
			vo.setPageNum(1);
		}
		
		vo.setPagePostSize(10); //한 페이지당 게시글 수
		vo.setPagingSize(5); //페이지 사이징 크기
		
		int pageNum = vo.getPageNum();
		int pagePostSize = vo.getPagePostSize();
		int pagingSize = vo.getPagingSize();
		
		//페이징 limit 시작숫자 설정
		int pagingLimitStartNum = (pageNum - 1) * pagePostSize;
		vo.setPagingLimitStartNum(pagingLimitStartNum);
		
		List<EgovMap> returnList = dao.selectList(vo);
		
		int maxPostCnt = dao.selectPostListCnt();
		
		// 페이징 그룹 계산 (5페이지씩 나누기)
	    int startPageInGroup = ((pageNum - 1) / pagingSize) * pagingSize + 1; 
	    int endPageInGroup = startPageInGroup + pagingSize - 1;
	    int nextPage = endPageInGroup +1;
	    
	    // 전체 페이지 수 계산 (총 게시글 수 / 한 페이지에 표시할 게시글 수)
	    int totalPages = (int) Math.ceil((double) maxPostCnt / pagePostSize);
	    
	    
	    // 마지막 페이지 번호 조정
	    if (endPageInGroup > totalPages) {
	        endPageInGroup = totalPages;
	        nextPage = endPageInGroup;
	    }
	    
	    // 그룹의 첫 페이지와 마지막 페이지 정보를 model에 담기
	    model.addAttribute("startPageInGroup", startPageInGroup); //첫 시작 페이지
	    model.addAttribute("endPageInGroup", endPageInGroup);	  //마지막 페이지
	    model.addAttribute("nextPage", nextPage);				  //다음페이지			  	
	    model.addAttribute("totalPages", totalPages);			  //총 페이지 수 
	    model.addAttribute("currentPage", pageNum);				  //현재페이지
	    model.addAttribute("maxPostCnt", maxPostCnt);			  //총 게시글 수
	    
	    
		
		return returnList;
	}


}
