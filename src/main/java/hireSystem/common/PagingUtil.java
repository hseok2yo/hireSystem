package hireSystem.common;

import java.util.HashMap;
import java.util.Map;

public class PagingUtil {

	//페이징 공통함수
	public static Map<String, Object> getPaging(int page, int totalCount, int pageSize, int blockSize) {

	    // 페이지가 0이면 1페이지로 초기화
	    if (page == 0) page = 1;

	    // 몇 번째 행부터 가져올지 (ex. 2페이지, pageSize=5 → 5번째 행부터)
	    int offset = (page - 1) * pageSize;

	    // 총 페이지 수 (ex. 총 13개, pageSize=5 → 3페이지)
	    int totalPage = (totalCount + pageSize - 1) / pageSize;

	    // 현재 페이지 첫번째 글 번호 (ex. 총 13개, 2페이지 → 8번)
	    int displayNo = totalCount - offset;

	    // 현재 블록의 시작 페이지 (ex. page=6, blockSize=5 → 6페이지)
	    int startPage = ((page - 1) / blockSize) * blockSize + 1;

	    // 현재 블록의 마지막 페이지 (ex. startPage=6, blockSize=5 → 10페이지)
	    int endPage = startPage + blockSize - 1;

	    // 마지막 블록은 totalPage를 초과하지 않도록 보정
	    if (endPage > totalPage) endPage = totalPage;

	    Map<String, Object> result = new HashMap<>();
	    result.put("displayNo", displayNo);   // 현재 페이지 첫번째 글 번호
	    result.put("startPage", startPage);   // 페이징 시작 번호
	    result.put("endPage", endPage);       // 페이징 마지막 번호
	    result.put("totalPage", totalPage);   // 총 페이지 수
	    result.put("currentPage", page);      // 현재 페이지

	    return result;
	}
}
