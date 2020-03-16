package com.taotao.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 图片上传的处理器
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月26日 下午5:52:17
 * @version 1.0
 */
@RestController
public class PictrueController {
	
	/** 注入fastdfs服务器的URL */
	@Value("${fastdfsUrl}")
	private String fastdfsUrl;
	/** 定义ObjectMapper操作JSON */
	private ObjectMapper objectMapper = new ObjectMapper();
	
	/** 文件上传 */
	@PostMapping(path="/pic/upload", produces=MediaType.TEXT_HTML_VALUE)
	public String upload(@RequestParam("pic")MultipartFile multipartFile) throws Exception{
		/** 定义Map集合封装最后的响应数据 */
		Map<String, Object> data = new HashMap<>();
		data.put("error", 500);
		try{
			/** 获取fastdfs-client.conf配置文件 */
			String conf_filename = this.getClass()
					.getResource("/fastdfs-client.conf").getPath();
			/** 初始化客户端全局信息对象 */
			ClientGlobal.init(conf_filename);
			/** 创建存储客户端对象 */
			StorageClient storageClient = new StorageClient(); 
			/** 上传文件到FastDFS服务器 */
			String[] arr = storageClient.upload_file(multipartFile.getBytes(), 
					FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
			// http://192.168.12.135/arr[0]/arr[1]
			/** 定义StringBuilder拼接图片的URL */
			StringBuilder sb = new StringBuilder(fastdfsUrl);
			for (String str : arr){
				sb.append("/" + str);
			}
			data.put("error", 0);
			data.put("url", sb.toString());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		// {"error" : 0 , "url" : ""}
		return objectMapper.writeValueAsString(data);
	}
}