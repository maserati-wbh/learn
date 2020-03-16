package com.taotao.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

/**
 * 商品
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年4月14日 下午10:52:14
 * @version 1.0
 */
@Table(name = "tb_item")
public class Item implements Serializable {
	
	private static final long serialVersionUID = 7884348431927995286L;
	/** 商品id，同时也是商品编号 */
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	/** 商品标题 */
    @Column
    private String title;
    /** 商品卖点 */
    @Column(name = "sell_point")
    private String sellPoint;
    /** 商品价格，单位为：分 */
    @Column
    private Long price;
    /** 库存数量 */
    @Column
    private Integer num;
    /** 商品条形码 */
    @Column
    private String barcode;
    /** 商品图片 */
    @Column
    private String image;
    /** 所属类目，叶子类目 */
    @Column
    private Long cid;
    /** 商品状态，1-正常，2-下架，3-删除 */
    @Column
    private Integer status;
    /** 创建时间 */
    @Column
    private Date created;
    /** 更新时间 */
    @Column
    private Date updated;
    
    /** setter and getter method */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSellPoint() {
        return sellPoint;
    }
    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }
    public Long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public Integer getNum() {
        return num;
    }
    public void setNum(Integer num) {
        this.num = num;
    }
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String[] getImages(){
    	if (StringUtils.isNoneBlank(image)){
    		return image.split(",");
    	}
    	return null;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Long getCid() {
        return cid;
    }
    public void setCid(Long cid) {
        this.cid = cid;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}