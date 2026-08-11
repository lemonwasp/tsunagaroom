package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dto.VideoCountDTO;
import logic.HomeLogic;

/**
 * Servlet implementation class SeniorHomeServlet
 */
@WebServlet("/SeniorHomeServlet")
public class SeniorHomeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    String sendKind = request.getParameter("sendKind");
    String from = request.getParameter("from");

    if ("home".equals(sendKind)) {

      // 高齢者側プロフィールID「1」を設定
      int profileId = 1;

      // セッション取得
      HttpSession session = request.getSession();

      // profileIdをセッションに保存
      session.setAttribute("profileId", profileId);

      // HomeLogicのgetHomeInfoメソッドを呼び出す
      HomeLogic logic = new HomeLogic();
      VideoCountDTO videoCountDTO = logic.getHomeInfo(profileId);

      // 確認用ログ 追加
      System.out.println("SeniorHomeServletに来た");
      System.out.println("remainingCount = " + videoCountDTO.getRemainingCount());

      // VideoCountDTOをリクエストスコープに登録
      request.setAttribute("videoCountDTO", videoCountDTO);

      // 未読動画がある場合は動画再生画面へ遷移
      if (!videoCountDTO.isViewed() && !"list".equals(from)) {

        response.sendRedirect(
            request.getContextPath() + "/VideoPlayServlet");
        return;
      }

      // 高齢者側ホーム画面へ遷移
      request.getRequestDispatcher("/seniorHome.jsp")
          .forward(request, response);

      return;
    }

  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    doGet(request, response);
  }
}