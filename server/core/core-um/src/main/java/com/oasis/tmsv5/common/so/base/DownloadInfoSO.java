package com.oasis.tmsv5.common.so.base;

import java.util.Date;

import com.oasis.tmsv5.common.enums.type.FileType;
import com.oasis.tmsv5.common.util.page.BasePageSO;

public class DownloadInfoSO extends BasePageSO {
	private static final long serialVersionUID = 1L;

	private Date beginTime;
	
	private Date endTime;
	
	private String operator;
	
	private FileType fileType;
	
	private String code;


	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public FileType getFileType() {
		return fileType;
	}

	public void setFileType(FileType fileType) {
		this.fileType = fileType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

  
}
