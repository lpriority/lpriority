package com.lp.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "column_headers")
public class ColumnHeaders implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private long columnHeaderId;
	private String columnName;
	private String dataType;
	
	public ColumnHeaders() {
		super();
	}

	public ColumnHeaders(long columnHeaderId, String columnName, String dataType) {
		super();
		this.columnHeaderId = columnHeaderId;
		this.columnName = columnName;
		this.dataType = dataType;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "column_headers_id", unique = true, nullable = false)
	public long getColumnHeaderId() {
		return columnHeaderId;
	}

	public void setColumnHeaderId(long columnHeaderId) {
		this.columnHeaderId = columnHeaderId;
	}
	@Column(name = "column_name", nullable = false)
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	@Column(name = "data_type", nullable = false)
	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
