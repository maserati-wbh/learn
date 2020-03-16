package com.taotao.admin.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_content")
public class Content implements Serializable {

	private static final long serialVersionUID = -6133776133496680916L;
	/** 编号 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	/** 内容分类ID */
    @Column(name = "category_id")
    private Long categoryId;
    /** 内容标题 */
    private String title;
    /** 子标题 */
    @Column(name = "sub_title")
    private String subTitle;
    /** 标题描述 */
    @Column(name = "title_desc")
    private String titleDesc;
    /** 链接 */
    private String url;
    /** 图片绝对路径 */
    private String pic;
    /** 图片2 */
    private String pic2;
    /** 内容 */
    private String content;
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
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSubTitle() {
        return subTitle;
    }
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
    public String getTitleDesc() {
        return titleDesc;
    }
    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPic() {
        return pic;
    }
    public void setPic(String pic) {
        this.pic = pic;
    }
    public String getPic2() {
        return pic2;
    }
    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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