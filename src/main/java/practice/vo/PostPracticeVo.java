package practice.vo;

import lombok.Data;

@Data
public class PostPracticeVo {
	
	/** 현재 페이지 수*/
	private int pageNum;
	
	/** 한 페이지당 게시글 수*/
	private int pagePostSize;
	
	/** 페이지 사이징 크기*/
	private int pagingSize;
	
	/** 쿼리 limit 페이징 시작 수*/
	private int pagingLimitStartNum;
	
	
	
	
	
	
}
