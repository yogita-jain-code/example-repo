package db.DTO;

public class LoginDetailsDTO {

	private Integer userId;
	private String userName;
	private String password;
	private Integer qmCode;
	private String qmType;
	private String passwordWithoutHash;
	private Integer isRemember;	//  (0 – false/1 – True)
	private String monitorName; 
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getQmCode() {
		return qmCode;
	}
	public void setQmCode(Integer qmCode) {
		this.qmCode = qmCode;
	}
	public String getQmType() {
		return qmType;
	}
	public void setQmType(String qmType) {
		this.qmType = qmType;
	}
	public String getPasswordWithoutHash() {
		return passwordWithoutHash;
	}
	public void setPasswordWithoutHash(String passwordWithoutHash) {
		this.passwordWithoutHash = passwordWithoutHash;
	}
	public Integer getIsRemember() {
		return isRemember;
	}
	public void setIsRemember(Integer isRemember) {
		this.isRemember = isRemember;
	}
	public String getMonitorName() {
		return monitorName;
	}
	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}
	
	
	
	
	
	
	
}
