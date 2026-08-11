package logic;

import dao.VideoDAO;
import dto.VideoCountDTO;

public class VideoCountLogic {
  public VideoCountDTO checkRemainingCount(int profileId) {

    // VideoDAO生成
    VideoDAO videoDAO = new VideoDAO();

    // 本日撮影した動画数を取得
    int todayVideoCount = videoDAO.countTodayVideos(profileId);
    System.out.println("todayVideoCount = " + todayVideoCount);

    // 残り撮影可能回数を計算
    int remainingCount = 3 - todayVideoCount;

    // 0未満にならないよう補正
    if (remainingCount < 0) {
      remainingCount = 0;
    }

    // DTO生成
    VideoCountDTO videoCountDTO = new VideoCountDTO();

    // DTOへ格納
    videoCountDTO.setTodayVideoCount(todayVideoCount);
    videoCountDTO.setRemainingCount(remainingCount);

    // DTOを返却
    return videoCountDTO;
  }

  public int checkReactionCount(int profileId) {
    //videoDAOをインスタンス化
    VideoDAO videoDAO = new VideoDAO();

    // (1) VideoDAOに本日の反応動画数取得を依頼
    int count = videoDAO.countTodayReactionVideo(profileId);

    // (2)(3) 取得した件数をServletへ返す
    return count;
  }
}
