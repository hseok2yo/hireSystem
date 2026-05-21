package practice.service;

import java.util.List;

import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.ui.ModelMap;

import practice.vo.PostPracticeVo;

public interface PostPracticeService {

	public List<EgovMap> selectList(PostPracticeVo vo, ModelMap model);

}
