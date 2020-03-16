package com.taotao.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 定义封装DataGrid的实体
 * @author maserati-wbh
 * @email maseratigc@163.com
 * @date 2017年9月26日 下午6:30:36
 * @version 1.0
 */
public class DataGridResult implements Serializable {
	
	private static final long serialVersionUID = -533446393990769907L;
	/** 定义总记录数 */
	private long total;
	/** 定义rows需要List集合 */
	private List<?> rows;
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}