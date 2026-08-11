package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import dto.VideoCountDTO;
import logic.VideoCountLogic;
import logic.VideoRecordLogic;

/**
 * Servlet implementation class VideoRecordServlet
 */
@WebServlet("/VideoRecordServlet")
@MultipartConfig
public class VideoRecordServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // セッションからprofileIdを取得
    Integer profileId = (Integer) request.getSession().getAttribute("profileId");
    request.setAttribute("profileId", profileId);
    System.out.println(profileId);

    // 残り撮影回数を確認するLogicを生成
    VideoCountLogic videoCountLogic = new VideoCountLogic();

    // 残り撮影回数情報を取得
    VideoCountDTO videoCountDTO = videoCountLogic.checkRemainingCount(profileId);

    // 残り撮影回数を取得
    int remainingCount = videoCountDTO.getRemainingCount();

    // 残り撮影回数がある場合
    if (remainingCount > 0) {

      // 動画撮影画面へ遷移
      request.getRequestDispatcher("/videoRecord.jsp")
          .forward(request, response);

    } else {

      // 残り撮影回数がない場合、エラー画面へ遷移
      request.getRequestDispatcher("/error.jsp")
          .forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    // 文字コード設定
    request.setCharacterEncoding("UTF-8");

    // セッションからprofileIdを取得
    Integer profileId = (Integer) request.getSession().getAttribute("profileId");
    System.out.println("現在のprofileId = " + profileId);

    // 撮影した動画データを取得
    // JS側で FormData に "video" という名前で入れる
    Part videoPart = request.getPart("video");

    // 動画登録処理Logicを生成
    VideoRecordLogic videoRecordLogic = new VideoRecordLogic();

    // 撮影した動画データをLogicへ渡す
    // webapp/video の実パス取得
    String uploadPath = getServletContext().getRealPath("/video");
    //		String uploadPath = request.getContextPath() + "/video";
    //		String uploadPath = "/video";

    boolean result = videoRecordLogic.recordVideo(
        videoPart,
        profileId,
        uploadPath);

    // 確認用
    System.out.println("動画登録結果 = " + result);

    // 完了画面でもprofileIdを使うため保存
    request.setAttribute("profileId", profileId);

    // 動画撮影完了画面へ遷移
    request.getRequestDispatcher("/videoRecordComplete.jsp")
        .forward(request, response);
  }
}