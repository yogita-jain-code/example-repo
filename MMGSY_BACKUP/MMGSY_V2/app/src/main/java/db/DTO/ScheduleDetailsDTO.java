package db.DTO;

import java.io.Serializable;

public class ScheduleDetailsDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uniqueCode;
	private Integer scheduleMonth;
	private Integer scheduleYear;
	private String stateName;
	private String districtName;
	private String blockName;
	private String packageId;
	private String sanctionYear;
	private String roadName;
	private Float roadLength;
	private String roadStatus; //  (C/P/M/L)
	private String completionDate;
	private Integer isRecordUploaded; // (0 – false/1 – True)
	private Integer isGeneratedUniqueId; // (0 – false/1 – True)
	private Integer IsEnquiry;  // (0 – false/1 – True)
	private Integer IsUnplanFinalize;  // (0 – false/1 – True)
	private String status;
	private String scheduleDownloadDate;
	
	
	public String getScheduleDownloadDate() {
		return scheduleDownloadDate;
	}
	public void setScheduleDownloadDate(String scheduleDownloadDate) {
		this.scheduleDownloadDate = scheduleDownloadDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	public Integer getScheduleMonth() {
		return scheduleMonth;
	}
	public void setScheduleMonth(Integer scheduleMonth) {
		this.scheduleMonth = scheduleMonth;
	}
	public Integer getScheduleYear() {
		return scheduleYear;
	}
	public void setScheduleYear(Integer scheduleYear) {
		this.scheduleYear = scheduleYear;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getSanctionYear() {
		return sanctionYear;
	}
	public void setSanctionYear(String sanctionYear) {
		this.sanctionYear = sanctionYear;
	}
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	public Float getRoadLength() {
		return roadLength;
	}
	public void setRoadLength(Float roadLength) {
		this.roadLength = roadLength;
	}
	public String getRoadStatus() {
		return roadStatus;
	}
	public void setRoadStatus(String roadStatus) {
		this.roadStatus = roadStatus;
	}
	public String getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}
	public Integer getIsRecordUploaded() {
		return isRecordUploaded;
	}
	public void setIsRecordUploaded(Integer isRecordUploaded) {
		this.isRecordUploaded = isRecordUploaded;
	}
	public Integer getIsGeneratedUniqueId() {
		return isGeneratedUniqueId;
	}
	public void setIsGeneratedUniqueId(Integer isGeneratedUniqueId) {
		this.isGeneratedUniqueId = isGeneratedUniqueId;
	}
	public Integer getIsEnquiry() {
		return IsEnquiry;
	}
	public void setIsEnquiry(Integer isEnquiry) {
		IsEnquiry = isEnquiry;
	}
	
	public Integer getIsUnplanFinalize() {
		return IsUnplanFinalize;
	}
	public void setIsUnplanFinalize(Integer isUnplanFinalize) {
		IsUnplanFinalize = isUnplanFinalize;
	}
	
	
	
}
