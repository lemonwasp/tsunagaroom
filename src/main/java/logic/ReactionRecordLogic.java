package logic;

import dao.VideoDAO;
import dto.VideoDTO;

public class ReactionRecordLogic {
  /**
  * リアクション動画を登録し、視聴済み動画を既読に更新する
  *
  * @param profileId リアクション動画を投稿するプロフィールID
  * @param videoId 視聴済みにする動画ID
  * @param filePath リアクション動画のファイルパス
  * @return 処理成功:true / 処理失敗:false
  */

  //saveReactionVideoメソッド
  public boolean saveReactionVideo(int profileId, int videoId, String filePath) {

    //videoDAOをインスタンス化
    VideoDAO videoDAO = new VideoDAO();
    // (1)(2) VideoDTOに動画情報を格納
    VideoDTO videoDTO = new VideoDTO();
    videoDTO.setProfileId(profileId);
    videoDTO.setVideoType(2);
    videoDTO.setFilePath(filePath);

    // (3) VideoDAOにDTO内の動画情報の登録を依頼
    boolean insertResult = videoDAO.insertReactionVideo(videoDTO);

    // 登録に失敗した場合はfalseを返す
    if (!insertResult) {
      return false;
    }

    // (4) 視聴済み動画を既読更新
    boolean updateResult = videoDAO.updateReadStatus(videoId);

    // 既読更新に失敗した場合はfalseを返す
    if (!updateResult) {
      return false;
    }

    // (6) 結果をReactionRecordServletへ返却
    return true;
  }
}
