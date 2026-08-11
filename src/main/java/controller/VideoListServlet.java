package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dto.VideoDTO;
import logic.VideoListLogic;

@WebServlet("/VideoListServlet")
public class VideoListServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    // ログイン中のプロフィールIDを取得
    HttpSession session = request.getSession();
    int profileId = (int) session.getAttribute("profileId");

    //家族でログイン中とする
    //int profileId = 2;

    //動画一覧に表示する動画ターゲット
    int targetProfileId = 0;

    // 高齢者側の場合
    if (profileId == 1) {

      // 家族が投稿した動画を表示する
      targetProfileId = 2;

    } // 家族側の場合
    else if (profileId == 2) {

      // 高齢者が投稿した動画を表示する
      targetProfileId = 1;

    } else {

      request.setAttribute("message", "プロフィール情報が正しくありません。");
      request.getRequestDispatcher("/WEB-INF/jsp/error.jsp")
          .forward(request, response);
      return;
    }

    //familyHome.jspかseniorHome.jspへそれぞれ遷移する
    // 戻るボタンが押された場合
    String action = request.getParameter("action");
    if ("back".equals(action)) {

      //プロフィールID1(高齢者)の場合
      if (profileId == 1) {
        request.getRequestDispatcher("/SeniorHomeServlet")
            .forward(request, response);
        return;

      }
      //プロフィールID2(家族)の場合
      else if (profileId == 2) {
        request.getRequestDispatcher("/FamilyHomeServlet")
            .forward(request, response);
        return;
      }
    }

    // VideoListLogicのgetVideoListメソッドを呼び出す
    VideoListLogic logic = new VideoListLogic();
    List<VideoDTO> videoList = logic.getVideoList(targetProfileId);

    // 動画件数取得
    int videoCount = logic.getVideoCount(targetProfileId);
    request.setAttribute("videoCount", videoCount);

    // VideoDTOリストをリクエストスコープに登録
    request.setAttribute("videoList", videoList);

    // videoList.jspへ画面遷移
    request.getRequestDispatcher("/videoList.jsp")
        .forward(request, response);

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}