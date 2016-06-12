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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.AmazonS3;
import com.yikangyiliao.base.awss3.conect.S3ConectFactory;
import com.yikangyiliao.base.utils.FileUtil;
import com.yikangyiliao.base.utils.ImgCompressUtil;
import com.yikangyiliao.fileManage.common.error.ExceptionConstants;

/**
 * @author liushuaic
 * @date 2015/09/10 17:55
 * @desc 文件上传
 * 
 */
@Controller
public class FileUploadController {

	private final String headImageBucketName = "biophoto";

	@RequestMapping
	@ResponseBody
	public Map<String, Object> imageFileUpload(@RequestParam(value = "fileGroup") String fileGroup,
			@RequestParam(value = "files") MultipartFile[] files, HttpServletRequest request) {
		Map<String, Object> rtnData = new HashMap<String, Object>();

		if (null != fileGroup && null != files) {

			if (fileGroup.equals("headImage")) {
				String fileName = "";

				String fileUrl = "";

				String newFileUrl = "";

				String oldFileName = "";

				String newFileName = "";

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
							S3ConectFactory.putImgeFile(con, headImageBucketName, fileName, f.getInputStream(),
									f.getContentType());
							ImgCompressUtil imgCompressUtil = new ImgCompressUtil(f.getInputStream());
							Image tempImage = imgCompressUtil.resizeFix(220, 220);
							File tempFile = new File("F:/ii/0.jpg");
							if(!tempFile.exists()){
								tempFile.createNewFile();
							}else{
								ImageIO.write((BufferedImage) tempImage, newFileName, tempFile);
								S3ConectFactory.putImgeFile(con, headImageBucketName, newFileName,
										new FileInputStream(tempFile.toString()), f.getContentType());
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
						fileUrl = S3ConectFactory.getResourceURL(con, headImageBucketName, fileName).toString();
						newFileUrl = S3ConectFactory.getResourceURL(con, headImageBucketName, newFileName).toString();
					}

					Map<String, String> fileMap = new HashMap<String, String>();
					fileMap.put("fileName", fileName);
					fileMap.put("fileUrl", fileUrl);
					fileMap.put("oldFileName", oldFileName);
					fileMap.put("newFileUrl", newFileUrl);
					fileMap.put("newFileName", newFileName);

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
		// String newFileName ="";
		/*
		 * ImageIcon ii = new ImageIcon("E:\\123.jpg"); ImgCompressUtil imgCom =
		 * new ImgCompressUtil(ii.getImage()); Image tempImage =
		 * imgCom.resizeFix(600, 399); File tempFile = new
		 * File("E:\\biophoto\\.jpg");
		 * ImageIO.write((BufferedImage)tempImage,"jpg",tempFile);
		 */
	}

}