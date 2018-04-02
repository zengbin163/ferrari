package com.home.ferrari.plugin.upload;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.enums.UploadPictureEnum;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.exception.FerrariSysException;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.util.image.ImageCompressUtil;

@Service
public class UploadService {

	private String fileSavePath;
	private String fileDomain;
	private static final Integer NEED_COMPRESS_YES = 1;
	
	private static final Logger logger = LoggerFactory.getLogger(UploadService.class);
	
	/**
	 * 图片压缩上传
	 * @param needCompress 是否需要压缩   1:需要  
	 * @param fileType 1 服务商  2 门店  3 问卷
	 * @param file 文件内容  
	 * @return
	 */
	public Map<String, Object> upload(Integer needCompress, Integer fileType, MultipartFile file) {
		logger.warn(String.format("=================Upload request paramter,FileName=%s,needCompress=%s,fileType=%s", file.getOriginalFilename(), needCompress, fileType));
		UploadPictureEnum uploadPictureEnum = UploadPictureEnum.getEnum(fileType);
        // 上传的图片类别不在图片分类列表中
		if (null == uploadPictureEnum) {
			throw new FerrariBizException(ResultCode.ILLEGAL_ARGUMENT, String.format("图片类型错误，类型值为%s", fileType));
		}
        String path = fileSavePath  + uploadPictureEnum.getPath();
        String imgFileName = file.getOriginalFilename();
        String[] array = imgFileName.split("\\.");
        String imgType = array[array.length - 1];
        imgFileName = getImageName();
        imgFileName = imgFileName + "_R";// 图片后缀加上_R表示是原图
        try{
        	File newFile = new File(path + imgFileName + "." + imgType);
        	file.transferTo(newFile);
        	if(NEED_COMPRESS_YES.equals(needCompress)){
	            // 图片压缩 用户上传的是图片，需要保存三种格式的数据，后缀名分别是：原图(_R)、缩略图(_M)、大图(_L)
        		String temoFileName = imgFileName.substring(0, imgFileName.length()-2);
	            ImageCompressUtil.compressAndCutPic(newFile, path + "/", temoFileName + "_M." + imgType, 200, 200);
        	}
        }catch(Exception e){
        	throw new FerrariSysException(ResultCode.SYSTEM_ERROR, String.format("图片上传压缩出现异常"));
        }
        imgFileName = imgFileName.replace("_R", "_X");
        String imgUrl = "share" + uploadPictureEnum.getPath() + imgFileName + "." + imgType;
        return ResultUtil.successMap(imgUrl);

	}
	
	/**
	 * 上传文件
	 * @param file
	 * @param fileType
	 * @return
	 */
	public Map<String, Object> uploadFile(MultipartFile file, Integer fileType) {
		logger.warn(String.format("=================Upload request paramter,FileName=%s,fileType=%s", file.getOriginalFilename(), fileType));
		UploadPictureEnum uploadPictureEnum = UploadPictureEnum.getEnum(fileType);
        // 上传的图片类别不在图片分类列表中
		if (null == uploadPictureEnum) {
			throw new FerrariBizException(ResultCode.ILLEGAL_ARGUMENT, String.format("文件类型错误，类型值为%s", fileType));
		}
        String path = fileSavePath  + uploadPictureEnum.getPath();
        String fileName = file.getOriginalFilename();
        String[] array = fileName.split("\\.");
        String imgType = array[array.length - 1];
        fileName = array[0] + "_" + getImageName();
    	File newFile = new File(path + fileName + "." + imgType);
    	try {
			file.transferTo(newFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
        String imgUrl = "share" + uploadPictureEnum.getPath() + fileName + "." + imgType;
        return ResultUtil.successMap(imgUrl);
	}
	
	private static String getImageName() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public String getFileSavePath() {
		return fileSavePath;
	}

	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}

	public String getFileDomain() {
		return fileDomain;
	}

	public void setFileDomain(String fileDomain) {
		this.fileDomain = fileDomain;
	}
}
