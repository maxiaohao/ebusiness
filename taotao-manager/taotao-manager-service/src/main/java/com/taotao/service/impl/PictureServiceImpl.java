package com.taotao.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClient;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {
	
	Logger logger = Logger.getLogger(PictureServiceImpl.class);
	

	@Value("${IMAGE_SERVER_BASE_URL}")
	private String IMAGE_SERVER_BASE_URL;
	@Override
	public PictureResult uploadPic(MultipartFile picFile) {
		PictureResult result = new PictureResult();
		//判断图片是否为空
		if (picFile.isEmpty()) {
			result.setError(1);
			result.setMessage("图片为空");
			return result;
		}
		//上传到图片服务器
		try {
			//取图片扩展名
			String originalFilename = picFile.getOriginalFilename();
			//取扩展名不要“.”
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			
			ClassPathResource resource= new ClassPathResource("properties/client.conf");
			String path=resource.getFile().getAbsolutePath();
			logger.debug("配置文件:"+path);
			FastDFSClient client = new FastDFSClient(path);
			//this.getClass().getR
			String url = client.uploadFile(picFile.getBytes(), extName);
			//String url = client.uploadFile(originalFilename, extName);
			url = IMAGE_SERVER_BASE_URL+url;
			logger.debug("图片路径:"+url);
			//把url响应给客户端
			result.setError(0);
			result.setUrl(url);
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(1);
			result.setMessage("图片上传失败");
		}
		return result;
	}

}
