package com.home.ferrari.dao.file;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.file.TeslaFiles;

public interface TeslaFilesDao {
	
	public Integer insertTeslaFiles(TeslaFiles teslaFiles);
	
	public List<TeslaFiles> getTeslaFilesByShopId(
			@Param(value = "page") Page<?> page,
			@Param(value = "shopId") Integer shopId,
			@Param(value = "fileType") Integer fileType);

	public Integer countTeslaFilesByShopId(
			@Param(value = "shopId") Integer shopId,
			@Param(value = "fileType") Integer fileType);
}
