package hireSystem.service.impl;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import hireSystem.service.HireLoginService;
import hireSystem.service.dao.HireLoginDao;
import hireSystem.vo.HireUserVo;

@Service("hireLoginService")
public class HireLoginServiceImpl extends EgovAbstractServiceImpl implements HireLoginService{

	@Resource(name = "hireLoginDao")
	public HireLoginDao hireLoginDao;

	@Override
	public HireUserVo checkLogin(HireUserVo hireUserVo) {
		return hireLoginDao.checkLogin(hireUserVo);
	}

}
