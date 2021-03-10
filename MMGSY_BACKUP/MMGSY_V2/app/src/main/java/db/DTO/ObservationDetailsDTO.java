package db.DTO;

public class ObservationDetailsDTO {

	private String uniqueCode;
	private String inspectionDate;
	private Float fromChainage;
	private Float toChainage;
	private String gradingParams;
	private String commentParams;
	private String startLatitude;
	private String startLongitude;
	private String endLatitude;
	private String endLongitude;
	private Integer observationId;
	private Integer isUploaded;
	
	
	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	public String getInspectionDate() {
		return inspectionDate;
	}
	public void setInspectionDate(String inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	public Float getFromChainage() {
		return fromChainage;
	}
	public void setFromChainage(Float fromChainage) {
		this.fromChainage = fromChainage;
	}
	public Float getToChainage() {
		return toChainage;
	}
	public void setToChainage(Float toChainage) {
		this.toChainage = toChainage;
	}
	public String getGradingParams() {
		return gradingParams;
	}
	public void setGradingParams(String gradingParams) {
		this.gradingParams = gradingParams;
	}
	public String getStartLatitude() {
		return startLatitude;
	}
	public void setStartLatitude(String startLatitude) {
		this.startLatitude = startLatitude;
	}
	public String getStartLongitude() {
		return startLongitude;
	}
	public void setStartLongitude(String startLongitude) {
		this.startLongitude = startLongitude;
	}
	public String getEndLatitude() {
		return endLatitude;
	}
	public void setEndLatitude(String endLatitude) {
		this.endLatitude = endLatitude;
	}
	public String getEndLongitude() {
		return endLongitude;
	}
	public void setEndLongitude(String endLongitude) {
		this.endLongitude = endLongitude;
	}
	public Integer getObservationId() {
		return observationId;
	}
	public void setObservationId(Integer observationId) {
		this.observationId = observationId;
	}
	public Integer getIsUploaded() {
		return isUploaded;
	}
	public void setIsUploaded(Integer isUploaded) {
		this.isUploaded = isUploaded;
	}
	public String getCommentParams() {
		return commentParams;
	}
	public void setCommentParams(String commentParams) {
		this.commentParams = commentParams;
	}
	
	
}
