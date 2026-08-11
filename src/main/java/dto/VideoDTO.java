package dto;

import java.time.LocalDateTime;

public class VideoDTO {
	private int id;
	private int profileId;
	private int videoType;
	private LocalDateTime createdAt;
	private int isRead;
	private String filePath;

	//引数なしのコンストラクタ
	public VideoDTO() {
	}

	//引数ありのコンストラクタ
	public VideoDTO(int id, int profileId, int videoType, LocalDateTime createdAt, int isRead, String filePath) {
		this.id = id;
		this.profileId = profileId;
		this.videoType = videoType;
		this.createdAt = createdAt;
		this.isRead = isRead;
		this.filePath = filePath;
	}

	//getterおよびsetter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}

	public int getVideoType() {
		return videoType;
	}

	public void setVideoType(int videoType) {
		this.videoType = videoType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
