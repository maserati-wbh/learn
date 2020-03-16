package com.taotao.admin.controller;

import java.util.Date;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.admin.pojo.Item;
import com.taotao.admin.service.ItemService;

/**
 * 商品Restful风格接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月6日 上午9:04:38
 * @version 1.0
 */
@Controller
@RequestMapping("/item/restful")
public class ItemRestfulController {
	
	/** 注入服务层接口的代理对象 */
	@Autowired
	private ItemService itemService;
	/** signKey 签名密钥  MD5加密*/
	private static final String SIGN_KEY = "e1f1fa6431adbdae23060c7b3704667c";
	private ObjectMapper objectMapper = new ObjectMapper();
	
	/** 
	 * 根据id获取商品 
	 * http://admin.taotao.com/item/restful/1
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItem(@PathVariable("id") Long id, 
			@RequestParam("sign")String sign){
		try{
			if (id != null && id > 0){
				/** 先得到签名 */
				String mySign = DigestUtils.md5Hex(SIGN_KEY + id);
				/** 对比签名 */
				if (mySign.equals(sign)){
					Item item = itemService.findOne(id);
					if (item != null){
						/** 200(响应成功) */
						//return ResponseEntity.status(HttpStatus.OK).body(item);
						return ResponseEntity.ok(item);
					}else{
						/** 404: 没有查询有到商品的内容 */
						return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
					}
				}
			}
			/** 400(错误的请求，请求参数有问题) */
			return ResponseEntity.badRequest().body(null);
		}catch(Exception ex){
			/**  500(服务器内容错误) */
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
	/** 
	 * 添加商品 
	 * */
	@PostMapping
	public ResponseEntity<Void> saveItem(@RequestBody Map<String,String> itemMap){
		try{
			// {"title" : ""}
			/** 先取客户端的签名 */
			String sign = itemMap.remove("sign");
			/** 先得到签名 */
			String mySign = DigestUtils.md5Hex(SIGN_KEY + 
						objectMapper.writeValueAsString(itemMap));
			/** 对比签名 */
			if (mySign.equals(sign)){
				if (itemMap != null && itemMap.size() > 0){
					Item item = objectMapper.readValue(objectMapper.writeValueAsString(itemMap),
							Item.class);
					item.setCreated(new Date());
					item.setUpdated(item.getCreated());
					item.setStatus(1);
					itemService.insertSelective(item);
					/**  201(创建成功)*/
					return ResponseEntity.status(HttpStatus.CREATED).build();
				}
			}
			/** 400 请求参数有问题 */
			return ResponseEntity.badRequest().build();
		}catch(Exception ex){
			/**  500(服务器内容错误) */
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/** 
	 * 修改商品 
	 * */
	@PutMapping
	public ResponseEntity<Void> updateItem(@RequestBody Map<String,String> itemMap){
		try{
			/** 先取客户端的签名 */
			String sign = itemMap.remove("sign");
			/** 先得到签名 */
			String mySign = DigestUtils.md5Hex(SIGN_KEY + 
						objectMapper.writeValueAsString(itemMap));
			/** 对比签名 */
			if (mySign.equals(sign)){
				
				if (itemMap != null && itemMap.size() > 0){
					Item item = objectMapper.readValue(objectMapper.writeValueAsString(itemMap),
							Item.class);
					item.setUpdated(new Date());
					itemService.updateSelective(item);
					/** 204(修改成功，没有响应内容返回) */
					return ResponseEntity.noContent().build();
				}
			}
			/** 400 请求参数有问题 */
			return ResponseEntity.badRequest().build();
		}catch(Exception ex){
			/**  500(服务器内容错误) */
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/** 删除商品  */
	@DeleteMapping
	public ResponseEntity<Void> deleteItem(@RequestParam("id")Long id, 
			@RequestParam("sign")String sign){
		try{
			if (id != null && id > 0){
				/** 先得到签名 */
				String mySign = DigestUtils.md5Hex(SIGN_KEY + id);
				/** 对比签名 */
				if (mySign.equals(sign)){
					itemService.delete(id);
					/** 204(删除成功，没有响应内容返回) */
					return ResponseEntity.noContent().build();
				}
			}
			/** 400 请求参数有问题 */
			return ResponseEntity.badRequest().build();
		}catch(Exception ex){
			/**  500(服务器内容错误) */
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}