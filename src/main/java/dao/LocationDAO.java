package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.LocationDTO;

public class LocationDAO {

  /**
   * 位置情報件数取得
   */
  public int countLocation(int userId) {

    int count = 0;

    String sql = "SELECT COUNT(*) FROM location WHERE user_id = ?";

    try (Connection conn = DBManager.getConnection();
        PreparedStatement pStmt = conn.prepareStatement(sql)) {

      pStmt.setInt(1, userId);

      try (ResultSet rs = pStmt.executeQuery()) {

        if (rs.next()) {
          count = rs.getInt(1);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return count;
  }

  /**
   * 一番古い位置情報削除
   */
  public boolean deleteLocation(int userId) {

    String sql = """
        DELETE FROM location
        WHERE user_id = ?
        ORDER BY created_at ASC
        LIMIT 1
        """;

    try (Connection conn = DBManager.getConnection();
        PreparedStatement pStmt = conn.prepareStatement(sql)) {

      pStmt.setInt(1, userId);

      int result = pStmt.executeUpdate();

      return result > 0;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return false;
  }

  /**
   * 最新10件の位置情報取得
   */
  public ArrayList<LocationDTO> arrayLocation(int userId) {

    ArrayList<LocationDTO> locationList = new ArrayList<>();

    String sql = """
        SELECT
            id,
            user_id,
            latitude,
            longitude,
            address,
            created_at
        FROM
            location
        WHERE
            user_id = ?
        ORDER BY
            created_at DESC
        LIMIT 10
        """;

    try (Connection conn = DBManager.getConnection();
        PreparedStatement pStmt = conn.prepareStatement(sql)) {

      pStmt.setInt(1, userId);

      try (ResultSet rs = pStmt.executeQuery()) {

        while (rs.next()) {

          LocationDTO location = new LocationDTO();

          location.setId(rs.getInt("id"));
          location.setUserId(rs.getInt("user_id"));
          location.setLatitude(rs.getDouble("latitude"));
          location.setLongitude(rs.getDouble("longitude"));
          location.setAddress(rs.getString("address"));

          if (rs.getTimestamp("created_at") != null) {
            location.setCreatedAt(
                rs.getTimestamp("created_at").toLocalDateTime());
          }

          locationList.add(location);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return locationList;
  }
}
