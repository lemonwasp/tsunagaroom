package logic;

import java.util.ArrayList;

import dao.LocationDAO;
import dto.LocationDTO;

public class LocationLogic {

  public ArrayList<LocationDTO> getLocation(int userId) {

    LocationDAO dao = new LocationDAO();

    // (1) 位置情報の件数を取得
    int count = dao.countLocation(userId);

    // (2) 位置情報が11件以上の場合、一番古い位置情報を削除
    if (count >= 11) {
      dao.deleteLocation(userId);
    }

    // (3)(4) 新しい順に10件取得し、LocationDTOリストに格納
    ArrayList<LocationDTO> locationList = dao.arrayLocation(userId);

    // (5) LocationServletに返却
    return locationList;
  }
}