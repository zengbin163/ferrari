package com.home.ferrari.service.breakdoc.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.breakdoc.BreakDocumentDao;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.breakdoc.BreakDocumentService;
import com.home.ferrari.status.BreakDocStatus;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.breakdoc.BreakDocument;
import com.home.ferrari.vo.breakdoc.BreakTrace;
import com.home.ferrari.vo.breakdoc.BreakTypelist;

@Service
public class BreakDocumentServiceImpl implements BreakDocumentService {
	
	@Autowired
	private BreakDocumentDao breakDocumentDao;

	@Override
	@Transactional
	public Map<String, Object> publishBreakDocument(BreakDocument breakDocument) {
		Integer flag = this.breakDocumentDao.insertBreakDocument(breakDocument);
		if(flag<1){
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "新增故障单失败");
		}
		String breakTypeList = breakDocument.getBreakTypeList();
		Assert.notNull(breakTypeList, "三级分类id不能为空，多个通过逗号分隔");
		String []typeIds = breakTypeList.split(",");
		for (int i = 0; i < typeIds.length; i++) {
			BreakTypelist bt = new BreakTypelist(breakDocument.getBreakId(), Integer.parseInt(typeIds[i]));
			this.breakDocumentDao.insertBreakTypelist(bt);
		}
		return ResultUtil.successMap(breakDocument.getBreakId());
	}
	
	@Transactional
	public Map<String, Object> auditBreakDocument(Integer breakId, Integer docStatus, String remark) {
		Assert.notNull(breakId, "故障单id不能为空");
		Assert.notNull(docStatus, "故障单审批状态不能为空");

		BreakDocument breakDocument  = new BreakDocument(breakId, docStatus);
		this.breakDocumentDao.updateBreakDocument(breakDocument);
		//新增备注
		BreakTrace breakTrace = new BreakTrace();
		breakTrace.setBreakId(breakId);
		breakTrace.setRemark(remark);
		this.saveBreakTrace(breakTrace);
		return ResultUtil.successMap(breakId);
	}

	@Override
	@Transactional
	public Map<String, Object> updateBreakDocument(BreakDocument breakDocument) {
		BreakDocument breakDocumentDb = this.breakDocumentDao.getBreakDocumentById(breakDocument.getBreakId());
		if(BreakDocStatus.AUDIT_RETURN.equals(breakDocumentDb.getDocStatus())) {
			breakDocument.setDocStatus(BreakDocStatus.AUDIT_WAITING);
		}
		//分类
		Integer breakId = breakDocument.getBreakId();
		this.breakDocumentDao.deleteBreakTypeListByBreakId(breakId);
		String breakTypeList = breakDocument.getBreakTypeList();
		Assert.notNull(breakTypeList, "三级分类id不能为空，多个通过逗号分隔");
		String []typeIds = breakTypeList.split(",");
		for (int i = 0; i < typeIds.length; i++) {
			BreakTypelist bt = new BreakTypelist(breakId, Integer.parseInt(typeIds[i]));
			this.breakDocumentDao.insertBreakTypelist(bt);
		}
		Integer flag = this.breakDocumentDao.updateBreakDocument(breakDocument);
		if(flag<1){
			throw new FerrariBizException(ResultCode.UPDATE_FAIL, "更新故障单失败");
		}
		return ResultUtil.successMap(breakDocument.getBreakId());
	}

	@Override
	public Map<String, Object> saveBreakTrace(BreakTrace breakTrace) {
		Assert.notNull(breakTrace, "breakTrace实体对象不能为空");
		BreakDocument breakDocument = this.breakDocumentDao.getBreakDocumentById(breakTrace.getBreakId());
		if(null == breakDocument) {
			throw new FerrariBizException(ResultCode.BREAK_NOT_EXISTS, "故障单不存在，breakId=" + breakTrace.getBreakId());
		}
		Integer flag = this.breakDocumentDao.insertBreakTrace(breakTrace);
		if(flag<1){
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "新增故障单备注失败");
		}
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}

	@Override
	public Map<String, Object> getBreakDocumentList(Integer pageOffset,
			Integer pageSize, Integer shopId, String title, String carModel,
			String carPart, String remark, String breakTypeName,
			Integer docStatus) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Map<String, Object> map = new HashMap<String, Object>();
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_MODIFY_TIME);
		map.put("list", this.breakDocumentDao.getBreakDocumentList(page, shopId, title, carModel, carPart, remark, breakTypeName, docStatus));
		map.put("sum", this.breakDocumentDao.getBreakDocumentCount(shopId, title, carModel, carPart, remark, breakTypeName, docStatus));
		return ResultUtil.successMap(map);
	}

	@Override
	public Map<String, Object> getBreakDocumentById(Integer breakId) {
		Assert.notNull(breakId, "breakId不能为空");
		BreakDocument breakDocument = this.breakDocumentDao.getBreakDocumentById(breakId);
		if(null==breakDocument) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}else{
			List<Integer> list = this.breakDocumentDao.getBreakTypeListByBreakId(breakId);
			if(!CollectionUtils.isEmpty(list)) {
				List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
				for(Integer breakTypeId : list) {
					listMap.add(this.breakDocumentDao.getBreakTypeLink(breakTypeId));
				}
				breakDocument.setListMap(listMap);
			}
		}
		return ResultUtil.successMap(breakDocument);
	}

	public Map<String, Object> getBreakTraceList(Integer breakId) {
		return ResultUtil.successMap(this.breakDocumentDao.getBreakTraceList(breakId));
	}
	
	public Map<String, Object> getBreakTypeByParentId(Integer parentId) {
		Assert.notNull(parentId, "parentId不能为空");
		return ResultUtil.successMap(this.breakDocumentDao.getBreakTypeByParentId(parentId));
	}
}
