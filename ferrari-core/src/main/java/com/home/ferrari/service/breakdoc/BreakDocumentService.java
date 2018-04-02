package com.home.ferrari.service.breakdoc;

import java.util.Map;

import com.home.ferrari.vo.breakdoc.BreakDocument;
import com.home.ferrari.vo.breakdoc.BreakTrace;

public interface BreakDocumentService {
	public Map<String, Object> publishBreakDocument(BreakDocument breakDocument);

	public Map<String, Object> auditBreakDocument(Integer breakId, Integer docStatus, String remark);

	public Map<String, Object> updateBreakDocument(BreakDocument breakDocument);

	public Map<String, Object> saveBreakTrace(BreakTrace breakTrace);

	public Map<String, Object> getBreakDocumentList(Integer pageOffset,
			Integer pageSize, Integer shopId, String title, String carModel,
			String carPart, String remark, String breakTypeName,
			Integer docStatus);

	public Map<String, Object> getBreakDocumentById(Integer breakId);

	public Map<String, Object> getBreakTraceList(Integer breakId);
	
	public Map<String, Object> getBreakTypeByParentId(Integer parentId);
}
