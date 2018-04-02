package com.home.ferrari.dao.file;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.file.FerrariFiles;

public interface FerrariFilesDao {
	
	public Integer insertFerrariFiles(FerrariFiles ferrariFiles);
	
	public List<FerrariFiles> getFerrariFiles(@Param(value = "page") Page<?> page, @Param(value = "fileType") Integer fileType);
	public Integer countFerrariFiles(@Param(value = "fileType") Integer fileType);
	
	public Integer deleteFerrariFileByFileTypeId(@Param(value = "fileTypeId") Integer fileTypeId);
}
