package logic;

import dao.VideoDAO;
import dto.VideoCountDTO;

public class HomeLogic {

  // 1日の撮影上限
  private static final int MAX_VIDEO_COUNT = 3;

  public VideoCountDTO getHomeInfo(int profileId) {

    VideoDAO videoDAO = new VideoDAO();

    // (1) 本日の動画数取得を依頼
    int todayVideoCount = videoDAO.countTodayVideos(profileId);

    // (2)(3) 残り撮影可能回数を計算
    int remainingCount = MAX_VIDEO_COUNT - todayVideoCount;

    if (remainingCount < 0) {
      remainingCount = 0;
    }

    // 未読動画件数を取得 追加
    //int unreadVideoCount = videoDAO.countUnreadVideos();

    //追加
    int unreadVideoCount = 0;

    if (profileId == 1) {
        // 高齢者側：家族が送った通常動画を見る
        unreadVideoCount = videoDAO.countUnreadFamilyVideos();
    } else if (profileId == 2) {
        // 家族側：高齢者が送った反応動画を見る
        unreadVideoCount = videoDAO.countUnreadReactionVideos();
    }
    
    // 未読動画がある場合 false、ない場合 true
    boolean isViewed = true;

    if (unreadVideoCount > 0) {
      isViewed = false;
    }

    // (4) DTOに格納
    VideoCountDTO videoCountDTO = new VideoCountDTO();

    videoCountDTO.setTodayVideoCount(todayVideoCount);
    videoCountDTO.setRemainingCount(remainingCount);

    // 未読動画有無を格納 追加
    videoCountDTO.setViewed(isViewed);

    return videoCountDTO;
  }
}