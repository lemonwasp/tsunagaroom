package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.VideoDTO;

public class VideoDAO {

  // countTodayReactionVideoメソッド
  public int countTodayReactionVideo(int profileId) {
    int count = 0;

    String sql = "SELECT COUNT(*) "
        + "FROM video "
        + "WHERE profile_id = ? "
        + "AND video_type = 2 "
        + "AND DATE(created_at) = CURRENT_DATE";

    try (
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {

      pstmt.setInt(1, profileId);

      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        count = rs.getInt(1);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return count;
  }

  // 本日撮影した通常動画の件数を取得
  public int countTodayVideos(int profileId) {
    int count = 0;

    String sql = "SELECT COUNT(*) "
        + "FROM video "
        + "WHERE profile_id = ? "
        + "AND video_type = 1 "
        + "AND DATE(created_at) = CURRENT_DATE";

    try (
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {

      pstmt.setInt(1, profileId);

      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        count = rs.getInt(1);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return count;
  }

  // 未読動画の件数を取得
  public int countUnreadVideos() {
    int count = 0;

    String sql = "SELECT COUNT(*) "
        + "FROM video "
        + "WHERE is_read = 0";

    try (
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();) {

      if (rs.next()) {
        count = rs.getInt(1);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return count;
  }
//家族が送った通常動画の未読件数を取得 追加
public int countUnreadFamilyVideos() {
 int count = 0;

 String sql = "SELECT COUNT(*) "
     + "FROM video "
     + "WHERE video_type = 1 "
     + "AND is_read = 0";

 try (
     Connection conn = DBManager.getConnection();
     PreparedStatement pstmt = conn.prepareStatement(sql);
     ResultSet rs = pstmt.executeQuery();) {

   if (rs.next()) {
     count = rs.getInt(1);
   }

 } catch (SQLException e) {
   e.printStackTrace();
 }

 return count;
}

//高齢者が送った反応動画の未読件数を取得
public int countUnreadReactionVideos() {
 int count = 0;

 String sql = "SELECT COUNT(*) "
     + "FROM video "
     + "WHERE video_type = 2 "
     + "AND is_read = 0";

 try (
     Connection conn = DBManager.getConnection();
     PreparedStatement pstmt = conn.prepareStatement(sql);
     ResultSet rs = pstmt.executeQuery();) {

   if (rs.next()) {
     count = rs.getInt(1);
   }

 } catch (SQLException e) {
   e.printStackTrace();
 }

 return count;
}

  public int countUnreadVideos(int receiverProfileId) {
    int count = 0;

    String sql = "SELECT COUNT(*) "
        + "FROM video "
        + "WHERE receiver_profile_id = ? "
        + "AND is_read = 0";

    try (
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {

      pstmt.setInt(1, receiverProfileId);

      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        count = rs.getInt(1);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return count;
  }

  // insertReactionVideoメソッド
  public boolean insertReactionVideo(VideoDTO videoDTO) {
    String sql = "INSERT INTO video "
        + "(profile_id, video_type, created_at, is_read, file_path) "
        + "VALUES "
        + "(?, ?, CURRENT_TIMESTAMP, 0, ?)";

    try (
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {

      pstmt.setInt(1, videoDTO.getProfileId());
      pstmt.setInt(2, videoDTO.getVideoType());
      pstmt.setString(3, videoDTO.getFilePath());

      int result = pstmt.executeUpdate();

      return result > 0;

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  // updateReadStatusメソッド
  public boolean updateReadStatus(int videoId) {
    String sql = "UPDATE video "
        + "SET is_read = 1 "
        + "WHERE id = ?";

    try (
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {

      pstmt.setInt(1, videoId);

      int result = pstmt.executeUpdate();

      return result > 0;

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return false;
  }

  // selectOldestUnreadVideoメソッド
  public VideoDTO selectOldestUnreadVideo(int profileId) {
    VideoDTO videoDTO = null;

    String sql = """
        SELECT
        	id,
        	profile_id,
        	video_type,
        	created_at,
        	is_read,
        	file_path
        FROM
        	video
        WHERE
        	profile_id = ?
        AND video_type = 1
        AND is_read = 0
        ORDER BY created_at ASC
        LIMIT 1
        """;

    try (
        Connection con = DBManager.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);) {

      pstmt.setInt(1, profileId);

      try (ResultSet rs = pstmt.executeQuery()) {

        if (rs.next()) {
          videoDTO = new VideoDTO();

          videoDTO.setId(rs.getInt("id"));
          videoDTO.setProfileId(rs.getInt("profile_id"));
          videoDTO.setVideoType(rs.getInt("video_type"));
          videoDTO.setCreatedAt(
              rs.getTimestamp("created_at").toLocalDateTime());
          videoDTO.setIsRead(rs.getInt("is_read"));
          videoDTO.setFilePath(rs.getString("file_path"));
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return videoDTO;
  }

  // selectVideoByIdメソッド
  public VideoDTO selectVideoById(int videoId) {
    VideoDTO videoDTO = null;

    String sql = """
        SELECT
        	id,
        	profile_id,
        	video_type,
        	created_at,
        	is_read,
        	file_path
        FROM
        	video
        WHERE
        	id = ?
        """;

    try (
        Connection con = DBManager.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);) {

      pstmt.setInt(1, videoId);

      try (ResultSet rs = pstmt.executeQuery()) {

        if (rs.next()) {
          videoDTO = new VideoDTO();

          videoDTO.setId(rs.getInt("id"));
          videoDTO.setProfileId(rs.getInt("profile_id"));
          videoDTO.setVideoType(rs.getInt("video_type"));
          videoDTO.setCreatedAt(
              rs.getTimestamp("created_at").toLocalDateTime());
          videoDTO.setIsRead(rs.getInt("is_read"));
          videoDTO.setFilePath(rs.getString("file_path"));
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return videoDTO;
  }

  //動画件数取得メソッド
  public int countVideos(int profileId) {

    // 動画件数
    int count = 0;

    String sql = """
        SELECT
        	COUNT(*)
        FROM
        	video
        WHERE
        	profile_id = ?
        """;

    try (Connection con = DBManager.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {

      // プレースホルダにプロフィールIDを設定
      pstmt.setInt(1, profileId);

      try (ResultSet rs = pstmt.executeQuery()) {

        // 件数取得
        if (rs.next()) {
          count = rs.getInt(1);
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    // 件数を返却
    return count;
  }

  public List<VideoDTO> selectVideoList(int profileId) {

    // 動画一覧格納用リスト
    List<VideoDTO> videoList = new ArrayList<>();

    // 指定プロフィールIDの動画一覧取得SQL
    String sql = """
        SELECT
        	id,
        	profile_id,
        	video_type,
        	created_at,
        	is_read,
        	file_path
        FROM
        	video
        WHERE
        	profile_id = ?
        ORDER BY
        	created_at DESC
        """;

    try (Connection con = DBManager.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)) {

      // プレースホルダにプロフィールIDを設定
      pstmt.setInt(1, profileId);

      try (ResultSet rs = pstmt.executeQuery()) {

        // 検索結果がある間繰り返す
        while (rs.next()) {

          // DTO生成
          VideoDTO videoDTO = new VideoDTO();

          // 検索結果をDTOへ格納
          videoDTO.setId(rs.getInt("id"));
          videoDTO.setProfileId(rs.getInt("profile_id"));
          videoDTO.setVideoType(rs.getInt("video_type"));
          videoDTO.setCreatedAt(
              rs.getTimestamp("created_at").toLocalDateTime());
          videoDTO.setIsRead(rs.getInt("is_read"));
          videoDTO.setFilePath(rs.getString("file_path"));

          // DTOをリストへ追加
          videoList.add(videoDTO);
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    // 動画一覧を返却
    return videoList;
  }

  // insertVideoメソッド
  // 通常動画を登録
  public boolean insertVideo(VideoDTO videoDTO) {

    // 動画テーブルへ登録
    String sql = "INSERT INTO video "
        + "(profile_id, video_type, created_at, is_read, file_path) "
        + "VALUES "
        + "(?, ?, CURRENT_TIMESTAMP, 0, ?)";

    try (
        Connection conn = DBManager.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);) {

      // プレースホルダへ値を設定
      pstmt.setInt(1, videoDTO.getProfileId());
      pstmt.setInt(2, videoDTO.getVideoType());
      pstmt.setString(3, videoDTO.getFilePath());

      // SQL実行
      int result = pstmt.executeUpdate();

      // 登録成功時 true
      return result > 0;

    } catch (SQLException e) {
      e.printStackTrace();
    }

    // 登録失敗時 false
    return false;
  }
}
