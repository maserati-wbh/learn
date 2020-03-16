package com.taotao.sso.service;

import com.taotao.sso.pojo.User;

/**
 * 用户服务接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月7日 上午10:22:55
 * @version 1.0
 */
public interface UserService {
	
	/**
	 * 验证用户名、手机号码、邮箱是否重复
	 * @param param 用户名｜手机号码｜邮箱
	 * @param type 1、2、3分别代表username、phone、email
	 * @return Boolean true : 数据可用  false: 不可用
	 */
	Boolean validate(String param, Integer type);
	/**
	 * 添加用户
	 * @param user 用户
	 */
	void saveUser(User user);
	/**
	 * 用户登录
	 * @param user 用户
	 * @return ticket票据
	 */
	String login(User user);
	/**
	 * 根据ticket票据查询登录用户信息
	 * @param ticket 票据
	 * @return 用户json格式的字符串
	 */
	String findUserByTicket(String ticket);
	/**
	 * 根据ticket票据删除Redis中用户数据
	 * @param ticket 票据
	 */
	void delTicket(String ticket);

}
