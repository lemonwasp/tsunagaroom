package logic;

import java.util.List;

import dao.VideoDAO;
import dto.VideoDTO;

public class VideoListLogic {
  private VideoDAO videoDAO = new VideoDAO();

  /**
   * 指定プロフィールIDの動画一覧を取得する
   *
   * @param profileId プロフィールID
   * @return 動画DTOリスト
   */
  public List<VideoDTO> getVideoList(int profileId) {

    // (1) VideoDAOに動画一覧取得を依頼
    List<VideoDTO> videoList = videoDAO.selectVideoList(profileId);

    // (2)(3) 取得結果をServletへ返却
    return videoList;
  }

  // 動画件数取得メソッド
  public int getVideoCount(int profileId) {

    // VideoDAOに動画件数取得を依頼
    int videoCount = videoDAO.countVideos(profileId);

    // 件数をServletへ返却
    return videoCount;
  }
}
