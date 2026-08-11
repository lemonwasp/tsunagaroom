package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dto.VideoCountDTO;
import logic.HomeLogic;

/**
 * Servlet implementation class FamilyHomeServlet
 */
@WebServlet("/FamilyHomeServlet")
public class FamilyHomeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    String sendKind = request.getParameter("sendKind");

    if ("home".equals(sendKind) || "FootPrintBack".equals(sendKind)) {

      // 家族側プロフィールID「2」を設定
      int profileId = 2;

      // セッションに保存
      HttpSession session = request.getSession();
      session.setAttribute("profileId", profileId);

      // HomeLogicを呼び出す
      HomeLogic logic = new HomeLogic();
      VideoCountDTO videoCountDTO = logic.getHomeInfo(profileId);

      // DTOをリクエストスコープへ登録
      request.setAttribute("videoCountDTO", videoCountDTO);

      // 家族側ホーム画面へ遷移
      request.getRequestDispatcher("familyHome.jsp")
          .forward(request, response);

      return;

    } else if ("title".equals(sendKind)) {
      // フォワード
      RequestDispatcher dispatcher = request.getRequestDispatcher("/familyTitle.jsp");
      dispatcher.forward(request, response);
      return;
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

  }
}