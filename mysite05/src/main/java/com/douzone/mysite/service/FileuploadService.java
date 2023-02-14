package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.exception.FileuploadServiceException;

@Service
@PropertySource("classpath:com/douzone/mysite/web/fileupload.properties")
public class FileuploadService {
	@Autowired
	private Environment env;
	
	public String restore(MultipartFile file) {
		String url = null;
		try {
			File uploadDirectory = new File(env.getProperty("fileupload.uploadLocation"));
			if(!uploadDirectory.exists()) {
				//없으면 
				uploadDirectory.mkdir();
			}
			if(file.isEmpty()) {
				//사용자가 파일 업로드 안한 경우
				return url;
			}
			//사용자가 업로드했던 파일 이름
			String originFilename = file.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf(".")+1);
			String saveFilename = generateSaveFilename(extName);
			Long fileSize = file.getSize();
			System.out.println("######"+originFilename+"######");
			System.out.println("######"+saveFilename+"######");
			System.out.println("######"+fileSize+"######");
			byte[] data = file.getBytes();
			OutputStream os = new FileOutputStream(env.getProperty("fileupload.uploadLocation")+"/"+saveFilename);
			os.write(data);
			os.close();
			url = env.getProperty("fileupload.resourceUrl") + "/" + saveFilename; 
		} catch (IOException ex) {
			throw new FileuploadServiceException(ex.toString());
		}
		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
			
		return filename;
	}

}
