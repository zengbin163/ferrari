package com.home.ferrari.dao.breakdoc;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.breakdoc.BreakDocument;
import com.home.ferrari.vo.breakdoc.BreakTrace;
import com.home.ferrari.vo.breakdoc.BreakType;
import com.home.ferrari.vo.breakdoc.BreakTypelist;

public interface BreakDocumentDao {
	
	public Integer insertBreakDocument(BreakDocument breakDocument);
	public Integer insertBreakTypelist(BreakTypelist breakTypeList);
	public Integer updateBreakDocument(BreakDocument breakDocument);
	public Integer deleteBreakTypeListByBreakId(@Param(value = "breakId") Integer breakId);
	public Integer insertBreakTrace(BreakTrace breakTrace);
	public List<BreakDocument> getBreakDocumentList(
			@Param(value = "page") Page<?> page,
			@Param(value = "shopId") Integer shopId,
			@Param(value = "title") String title,
			@Param(value = "carModel") String carModel,
			@Param(value = "carPart") String carPart,
			@Param(value = "remark") String remark,
			@Param(value = "breakTypeName") String breakTypeName,
			@Param(value = "docStatus") Integer docStatus);
	public Integer getBreakDocumentCount(
			@Param(value = "shopId") Integer shopId,
			@Param(value = "title") String title,
			@Param(value = "carModel") String carModel,
			@Param(value = "carPart") String carPart,
			@Param(value = "remark") String remark,
			@Param(value = "breakTypeName") String breakTypeName,
			@Param(value = "docStatus") Integer docStatus);
	public BreakDocument getBreakDocumentById(@Param(value = "breakId") Integer breakId);
	public List<BreakTrace> getBreakTraceList(@Param(value = "breakId") Integer breakId);
	public List<Integer> getBreakTypeListByBreakId(@Param(value = "breakId") Integer breakId);
	public List<BreakType> getBreakTypeByParentId(@Param(value = "parentId") Integer parentId);
	public Map<String,Object> getBreakTypeLink(@Param(value = "breakTypeId") Integer breakTypeId);
}
