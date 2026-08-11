package logic;

import dao.VideoDAO;
import dto.VideoDTO;

public class VideoPlayLogic {

  private VideoDAO videoDAO;

  public VideoPlayLogic() {
    this.videoDAO = new VideoDAO();
  }

  /**
   * 最新の未読通常動画を取得する
   */
  public VideoDTO getOldestUnreadVideo() {

    int familyProfileId = 2;

    VideoDTO videoDTO = videoDAO.selectOldestUnreadVideo(familyProfileId);

    return videoDTO;
  }

  /**
   * 指定した動画を取得する
   */
  public VideoDTO getVideo(int videoId) {

    return videoDAO.selectVideoById(videoId);
  }

  /**
   * 動画を既読に更新する
   */
  public void updateReadStatus(int videoId) {

    videoDAO.updateReadStatus(videoId);
  }
}