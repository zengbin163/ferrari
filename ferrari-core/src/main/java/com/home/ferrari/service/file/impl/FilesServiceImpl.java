package com.home.ferrari.service.file.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.file.FerrariFilesDao;
import com.home.ferrari.dao.file.TeslaFilesDao;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.file.FilesService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.file.FerrariFiles;
import com.home.ferrari.vo.file.TeslaFiles;

@Service
public class FilesServiceImpl implements FilesService {

	@Autowired
	private FerrariFilesDao ferrariFilesDao;
	@Autowired
	private TeslaFilesDao teslaFilesDao;

	@Override
	public Map<String, Object> createFerrariFiles(Integer parentFiletypeId,
			Integer isFile, Integer fileType, String fileUrl) {
		Assert.notNull(parentFiletypeId, "父级文件id不能为空");
		Assert.notNull(isFile, "是否文件不能为空");
		Assert.notNull(fileType, "文件类型不能为空");
		Assert.notNull(fileUrl, "文件链接不能为空");
		String fileSuffix = this.getFileSuffix(fileUrl);
		FerrariFiles ferrariFiles = new FerrariFiles(parentFiletypeId, isFile,
				fileType, fileSuffix, fileUrl);
		Integer flag = this.ferrariFilesDao.insertFerrariFiles(ferrariFiles);
		if(flag<1) {
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "新增星奥车码头资料失败");
		}
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}

	@Override
	public Map<String, Object> getFerrariFiles(Integer pageOffset, Integer pageSize, Integer fileType) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_MODIFY_TIME);
		List<FerrariFiles> ferrariFileList = this.ferrariFilesDao.getFerrariFiles(page, fileType);
		if(CollectionUtils.isEmpty(ferrariFileList)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", ferrariFileList);
		map.put("sum", this.ferrariFilesDao.countFerrariFiles(fileType));
		return ResultUtil.successMap(map);
	}

	@Override
	public Map<String, Object> createTeslaFiles(Integer shopId, Integer fileType, String fileUrl) {
		Assert.notNull(shopId, "门店id不能为空");
		Assert.notNull(fileUrl, "文件链接不能为空");
		String fileSuffix = this.getFileSuffix(fileUrl);
		TeslaFiles teslaFile = new TeslaFiles(shopId,fileType,fileSuffix, fileUrl);
		Integer flag = this.teslaFilesDao.insertTeslaFiles(teslaFile);
		if(flag<1) {
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "新增星奥门店资料失败");
		}
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}

	@Override
	public Map<String, Object> getTeslaFilesByShopId(Integer shopId,
			Integer pageOffset, Integer pageSize, Integer fileType) {
		Assert.notNull(shopId, "门店id不能为空");
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_MODIFY_TIME);
		List<TeslaFiles> list = this.teslaFilesDao.getTeslaFilesByShopId(page, shopId, fileType);
		if(CollectionUtils.isEmpty(list)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("sum", this.teslaFilesDao.countTeslaFilesByShopId(shopId, fileType));
		return ResultUtil.successMap(map);
		
	}
	
	public Map<String, Object> deleteFerrariFile(Integer fileTypeId) {
		Assert.notNull(fileTypeId, "fileTypeId不能为空");
		return ResultUtil.successMap(this.ferrariFilesDao.deleteFerrariFileByFileTypeId(fileTypeId));
	}

	private String getFileSuffix(String fileUrls) {
		if(StringUtils.isEmpty(fileUrls)) {
			return null;
		}
		Integer lastIndex = fileUrls.lastIndexOf(".");
		return fileUrls.substring(lastIndex + 1, fileUrls.length());
	}
}
