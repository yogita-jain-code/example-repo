package db.DTO;

public class QmsNotificationDTO {

	/**
	 * @param args
	 */
	
	private Integer userId;
	private Integer messageId;
	private String messageText;
	private String messageType;
	private Integer isDownload; 
	private String timeStamp;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Integer getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(Integer isDownload) {
		this.isDownload = isDownload;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
