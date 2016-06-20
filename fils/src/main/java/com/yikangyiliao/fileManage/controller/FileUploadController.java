package com.yikangyiliao.fileManage.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.yikangyiliao.base.awss3.conect.S3ConectFactory;
import com.yikangyiliao.base.utils.FileUtil;
import com.yikangyiliao.base.utils.ImgCompressUtil;
import com.yikangyiliao.base.utils.SystemProperties;
import com.yikangyiliao.fileManage.common.error.ExceptionConstants;

/**
 * @author liushuaic
 * @date 2015/09/10 17:55
 * @desc 文件上传
 * 
 */
@Controller
public class FileUploadController {

	private Logger logger =Logger.getLogger(FileUploadController.class);
	private final String headImageBucketName = "biophoto";

	@RequestMapping
	@ResponseBody
	public Map<String, Object> imageFileUpload(@RequestParam(value = "fileGroup") String fileGroup,
			@RequestParam(value = "files") MultipartFile[] files, HttpServletRequest request) {
		
		Map<String, Object> rtnData = new HashMap<String, Object>();

		logger.info("FileUploadController 上传文件 ，文件数量为:"+files.length);
		if (null != fileGroup && null != files) {

			if (fileGroup.equals("headImage")) {
				String fileName = "";

				String fileUrl = "";

				String oldFileName = "";

				String newFileName = "";
				
				String NewfileUrl="";

				if (files.length > 0) {
					for (MultipartFile f : files) {
						AmazonS3 con = S3ConectFactory.ConnectFactory();
						oldFileName = f.getOriginalFilename();
						if (oldFileName.indexOf(".") > 0) {
							fileName = FileUtil.getUniqueFileName(oldFileName);
							newFileName = FileUtil.getUniqueNewFileName(fileName);
						} else if (null != f.getContentType() && f.getContentType().equals("image/jpeg")) {
							fileName = FileUtil.getUniqueFileName(fileName + ".jpg");
						} else {
							rtnData.put("status", ExceptionConstants.fileException.imageFileTypeException.errorCode);
							rtnData.put("message",
									ExceptionConstants.fileException.imageFileTypeException.errorMessage);
							return rtnData;
						}
						try {
							S3ConectFactory.putImgeFile(con, headImageBucketName, fileName, f.getInputStream(),f.getContentType());
							ImgCompressUtil imgCompressUtil = new ImgCompressUtil(f.getInputStream());
							Image tempImage = imgCompressUtil.changeNumber(f.getInputStream(),SystemProperties.multiple);
							try {
								String path = SystemProperties.FilePath;
							    ImageIO.write((BufferedImage)tempImage,"jpg", new File(path));
								S3ConectFactory.putImgeFile(con, headImageBucketName, newFileName,new FileInputStream(path), f.getContentType());
							} catch (IOException e) {
								e.printStackTrace();
								//logger.debug(e.getMessage());
							}
						} catch (IOException e) {
							e.printStackTrace();
							//logger.debug(e.getMessage());
						}
						fileUrl = S3ConectFactory.getResourceURL(con, headImageBucketName, fileName).toString();
						NewfileUrl = S3ConectFactory.getResourceURL(con, headImageBucketName, newFileName).toString();
					}

					Map<String, String> fileMap = new HashMap<String, String>();
					fileMap.put("fileName", fileName);
					fileMap.put("fileUrl", fileUrl);
					fileMap.put("oldFileName", oldFileName);
                    fileMap.put("newFileName", newFileName);
                    fileMap.put("NewfileUrl", NewfileUrl);
                    
					rtnData.put("data", fileMap);
					rtnData.put("status", ExceptionConstants.responseSuccess.responseSuccess.code);
					rtnData.put("message", ExceptionConstants.responseSuccess.responseSuccess.message);

				} else {
					rtnData.put("status", ExceptionConstants.fileException.fileNumsException.errorCode);
					rtnData.put("message", ExceptionConstants.fileException.fileNumsException.errorMessage);
				}
			}
		}
		return rtnData;

	}

	@RequestMapping
	public String upload() {

		return "fileUpload";
	}

	@RequestMapping
	public void getFile(String fileKey, HttpServletResponse httpServletResponse) {

		AmazonS3 con = S3ConectFactory.ConnectFactory();
		try {
			S3ConectFactory.getFile(con, "biophoto", fileKey, httpServletResponse.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
	}
}