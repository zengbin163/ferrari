package com.home.ferrari.service.file;

import java.util.Map;

public interface FilesService {

	/**
	 * 创建车码头资料
	 * 
	 * @param fileType
	 * @param fileUrl
	 * @return
	 */
	public Map<String, Object> createFerrariFiles(Integer parentFiletypeId,
			Integer isFile, Integer fileType, String fileUrl);

	/**
	 * 查询车码头资料列表
	 */
	public Map<String, Object> getFerrariFiles(Integer pageOffset, Integer pageSize, Integer fileType);

	/**
	 * 创建门店资料
	 * 
	 * @param shopId
	 * @param fileUrl
	 * @return
	 */
	public Map<String, Object> createTeslaFiles(Integer shopId, Integer fileType, String fileUrl);

	/**
	 * 根据门店id查询门店资料列表
	 * 
	 * @param shopId
	 * @return
	 */
	public Map<String, Object> getTeslaFilesByShopId(Integer shopId,
			Integer pageOffset, Integer pageSize, Integer fileType);
	
	/**
	 * 删除星奥平台的文件
	 * @param fileTypeId
	 * @return
	 */
	public Map<String, Object> deleteFerrariFile(Integer fileTypeId);
}
