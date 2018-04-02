package com.home.ferrari.web.site.file;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.file.FilesService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/file")
public class FileAction extends BaseAction {

	private static final long serialVersionUID = -736692819688850647L;
	@Autowired
	private FilesService filesService;

	/**
	 * 新增星奥车码头资料
	 * @return
	 */
	@RequestMapping("create")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> create() {
		Integer parentFiletypeId = this.getIntParameter(request, "parentFiletypeId", null);
		Integer isFile = this.getIntParameter(request, "isFile", null);
		Integer fileType = this.getIntParameter(request, "fileType", null);
		String fileUrl =this.getFilteredParameter(request, "fileUrl", 0, null);
		return this.filesService.createFerrariFiles(parentFiletypeId, isFile,
				fileType, fileUrl);
	}
	
	/**
	 * 查询星奥车码头资料列表
	 * @return
	 */
	@RequestMapping("query")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> query() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer fileType = this.getIntParameter(request, "fileType", null);
		return this.filesService.getFerrariFiles(pageOffset, pageSize, fileType);
	}

	/**
	 * 删除星奥车码头资料
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> delete() {
		Integer fileTypeId = this.getIntParameter(request, "fileTypeId", null);
		return this.filesService.deleteFerrariFile(fileTypeId);
	}

	/**
	 * 新增门店资料
	 * @return
	 */
	@RequestMapping("shopCreate")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopCreate() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer fileType = this.getIntParameter(request, "fileType", null);
		String fileUrl =this.getFilteredParameter(request, "fileUrl", 0, null);
		return this.filesService.createTeslaFiles(shopId, fileType, fileUrl);
	}
	
	/**
	 * 根据门店id查询门店资料列表
	 * @return
	 */
	@RequestMapping("shopQuery")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopQuery() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer fileType = this.getIntParameter(request, "fileType", null);
		return this.filesService.getTeslaFilesByShopId(shopId, pageOffset, pageSize, fileType);
	}
}
