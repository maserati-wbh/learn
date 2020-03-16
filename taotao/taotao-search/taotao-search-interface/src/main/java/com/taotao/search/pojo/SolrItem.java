package com.taotao.search.pojo;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

/**
 * 商品类
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年4月26日 上午12:46:52
 * @version 1.0
 */
public class SolrItem implements Serializable {

	private static final long serialVersionUID = 5933282713019435377L;
	@Field("id")
	private long id;
	@Field("title")
	private String title;
	@Field("price")
	private long price;
	@Field
	private String image;
	@Field
	private String sellPoint;
	@Field
	private int status;
	
	/** setter and getter method */
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String[] getImages() {
		if (StringUtils.isNotBlank(this.image)){
			return image.split(",");
		}
		return null;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public int isStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}