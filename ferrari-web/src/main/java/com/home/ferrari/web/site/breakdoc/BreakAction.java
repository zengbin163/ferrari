package com.home.ferrari.web.site.breakdoc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.breakdoc.BreakDocumentService;
import com.home.ferrari.status.BreakDocStatus;
import com.home.ferrari.vo.breakdoc.BreakDocument;
import com.home.ferrari.vo.breakdoc.BreakTrace;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/break")
public class BreakAction extends BaseAction{

	private static final long serialVersionUID = 2276446980106038998L;
	
	@Autowired
	private BreakDocumentService breakDocumentService;
	
	@RequestMapping("publish")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> publish() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String title = this.getFilteredParameter(request, "title", 0, null);
		String breakTypeList = this.getFilteredParameter(request, "breakTypeList", 0, null);
		String carModel = this.getFilteredParameter(request, "carModel", 0, null);
		String carPart = this.getFilteredParameter(request, "carPart", 0, null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		String text = this.getFilteredParameter(request, "text", 0, null);
		BreakDocument breakDocument = new BreakDocument(null, shopId, title,
				breakTypeList, BreakDocStatus.AUDIT_WAITING, carModel, carPart,
				remark, text);
		return this.breakDocumentService.publishBreakDocument(breakDocument);
	}

	@RequestMapping("edit")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> edit() {
		Integer breakId = this.getIntParameter(request, "breakId", null);
		String breakTypeList = this.getFilteredParameter(request, "breakTypeList", 0, null);
		String title = this.getFilteredParameter(request, "title", 0, null);
		String carModel = this.getFilteredParameter(request, "carModel", 0, null);
		String carPart = this.getFilteredParameter(request, "carPart", 0, null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		String text = this.getFilteredParameter(request, "text", 0, null);
		BreakDocument breakDocument = new BreakDocument(breakId, null, title,
				breakTypeList, null, carModel, carPart, remark, text);
		return this.breakDocumentService.updateBreakDocument(breakDocument);
	}

	@RequestMapping("breakList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> breakList() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String title = this.getFilteredParameter(request, "title", 0, null);
		String carModel = this.getFilteredParameter(request, "carModel", 0, null);
		String carPart = this.getFilteredParameter(request, "carPart", 0, null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		String breakTypeName = this.getFilteredParameter(request, "breakTypeName", 0, null);
		Integer docStatus = this.getIntParameter(request, "docStatus", null);
		return this.breakDocumentService.getBreakDocumentList(pageOffset, pageSize, shopId, title, carModel, carPart, remark, breakTypeName, docStatus);
	}

	@RequestMapping("breakDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> breakDetail() {
		Integer breakId = this.getIntParameter(request, "breakId", null);
		return this.breakDocumentService.getBreakDocumentById(breakId);
	}
	
	@RequestMapping("audit")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> audit() {
		Integer breakId = this.getIntParameter(request, "breakId", null);
		Integer docStatus = this.getIntParameter(request, "docStatus", null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		return this.breakDocumentService.auditBreakDocument(breakId, docStatus, remark);
	}
	
	@RequestMapping("remark")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> remark() {
		Integer breakId = this.getIntParameter(request, "breakId", null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		BreakTrace breakTrace = new BreakTrace();
		breakTrace.setBreakId(breakId);
		breakTrace.setRemark(remark);
		return this.breakDocumentService.saveBreakTrace(breakTrace);
	}

	@RequestMapping("remarkList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> remarkList() {
		Integer breakId = this.getIntParameter(request, "breakId", null);
		return this.breakDocumentService.getBreakTraceList(breakId);
	}
	
	@RequestMapping("breakTypeList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> breakTypeList() {
		Integer parentId = this.getIntParameter(request, "parentId", null);
		return this.breakDocumentService.getBreakTypeByParentId(parentId);
	}
}
