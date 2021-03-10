package db.DTO;

import java.io.Serializable;

public class ImageDetailsDTO implements Serializable{

	/**
	 * 
	 */
	
	private String UniqueCode;
	private Integer fileId;
	private Integer observationId;
	private String fileName;
	private String description;
	private String latitude;
	private String longitude;
	private String captureDateTime;
	private Integer isUploaded; //  (0 – false/1 – True)
	private Integer IsSelected; //  (0 – false/1 – True)
	
	private Integer isNotFound;
	
	public String getUniqueCode() {
		return UniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		UniqueCode = uniqueCode;
	}
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public Integer getObservationId() {
		return observationId;
	}
	public void setObservationId(Integer observationId) {
		this.observationId = observationId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getCaptureDateTime() {
		return captureDateTime;
	}
	public void setCaptureDateTime(String captureDateTime) {
		this.captureDateTime = captureDateTime;
	}
	public Integer getIsUploaded() {
		return isUploaded;
	}
	public void setIsUploaded(Integer isUploaded) {
		this.isUploaded = isUploaded;
	}
	public Integer getIsSelected() {
		return IsSelected;
	}
	public void setIsSelected(Integer isSelected) {
		IsSelected = isSelected;
	}
	
	public Integer getNotFound() {
		return isNotFound;
	}
	public void setNotFound(Integer isNotFound) {
		this.isNotFound = isNotFound;
	}
	
	
}
