package com.oasis.tmsv5.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.oasis.tmsv5.common.enums.type.FileType;
import com.oasis.tmsv5.model.BaseModel;
@Table(name = "WL_REPORT_DOWNLOAD")
@SequenceGenerator(name = "WL_REPORT_DOWNLOAD_SEQ")
public class DownloadInfo extends BaseModel {
   
    private static final long serialVersionUID = 5642263563521074295L;

    @Id
    private Long id;
    
    /**
     * 文件实际存放路径（包含文件名称）
     */
    private String path;
    
    /**
     * 文件生成时间
     */
    private Date time;
    
    
    /**
     * 操作人
     */
    private String operator;
    
    /**
     * 文件所属类别：费用、运营时效 等
     */
    @Column(name = "file_type")
    private FileType fileType;
    
    /**
     * 报表数据开始时间
     */
    @Column(name = "start_time")
    private Date startTime;
    
    /**
     * 报表数据结束时间
     */
    @Column(name = "end_time")
    private Date endTime;
    
    @Column(name = "opened")
    private Boolean opened = Boolean.FALSE;
    

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public Date getTime() {
		return time;
	}


	public void setTime(Date time) {
		this.time = time;
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


	public Date getStartTime() {
		return startTime;
	}


	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public Date getEndTime() {
		return endTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}


	public Boolean getOpened() {
		return opened;
	}


	public void setOpened(Boolean opened) {
		this.opened = opened;
	}



    	
    

}
