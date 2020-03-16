package com.taotao.item;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Item表现层启动类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年10月7日 上午9:48:56
 * @version 1.0
 */
@ImportResource(locations={"classpath:applicationContext-dubbo.xml"}) // 加载Spring的配置文件
@SpringBootApplication(scanBasePackages={"com.taotao.item"})
public class ItemWebApplication {

	public static void main(String[] args) {
		/** 创建SpringApplication */
		SpringApplication springApplication = new SpringApplication(ItemWebApplication.class);
		/** 设置横幅为关闭 */
		springApplication.setBannerMode(Mode.OFF);
		/** 运行 */
		springApplication.run(args);
	}

}
