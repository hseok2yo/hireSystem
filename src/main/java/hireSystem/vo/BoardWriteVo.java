package hireSystem.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardWriteVo {
	private int boardNum;

    private String category;

    private String title;

    private String content;

    private String writer;

    private Date regDt;

    private Date updateDt;

    private int viewCnt;
    
    private int userNum;
    
    //페이징 조건들
    private int page;
    private int pageSize;
    private int offset;
    
    private String searchType;
    private String searchKeyword;
    
    
    
}
