package com.taotao.sso.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.taotao.sso.pojo.User;

/**
 * 用户数据访问接口
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月7日 上午10:20:46
 * @version 1.0
 */
@Mapper
public interface UserMapper {
	
	/** 根据条件统计查询 */
	int count(User user);
	
	/** 添加用户 */
	@Insert("INSERT INTO `tb_user`(`username`,`password`,phone,`email`,`created`,`updated`) "
			+ "	VALUES (#{username},#{password},#{phone},#{email}, #{created},#{updated})")
	void save(User user);

	/** 根据用户名与密码查询用户  */
	@Select("select * from tb_user where username=#{username} and password=#{password}")
	User getUser(User user);

}
