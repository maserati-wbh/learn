package com.taotao.search;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Search服务层启动类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月7日 上午9:48:56
 * @version 1.0
 */
@ImportResource(locations={"classpath:applicationContext-dubbo.xml"}) // 加载Spring的配置文件
@SpringBootApplication
public class SearchServiceApplication {

	public static void main(String[] args) {
		/** 创建SpringApplication */
		SpringApplication springApplication = new SpringApplication(SearchServiceApplication.class);
		/** 设置横幅为关闭 */
		springApplication.setBannerMode(Mode.OFF);
		/** 运行 */
		springApplication.run(args);
	}

}
