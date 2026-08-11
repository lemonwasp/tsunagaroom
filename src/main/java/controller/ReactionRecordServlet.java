package controller;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import dto.VideoDTO;
import logic.ReactionRecordLogic;

/**
 * リアクション動画登録用Servlet
 */
@WebServlet("/ReactionRecordServlet")
@MultipartConfig
public class ReactionRecordServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    response.sendRedirect(request.getContextPath() + "/VideoPlayServlet");
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setCharacterEncoding("UTF-8");

    // セッション取得
    HttpSession session = request.getSession();

    // (1) 視聴動画の動画IDを動画情報セッションスコープから取得
    VideoDTO videoDTO = (VideoDTO) session.getAttribute("videoDTO");

    if (videoDTO == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    // 視聴済みにする動画ID
    int watchedVideoId = videoDTO.getId();

    // セッションスコープからプロフィールIDを取得
    Integer profileId = (Integer) session.getAttribute("profileId");

    if (profileId == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    // =====================================
    // 実録画保存用
    // MediaRecorderで送信された動画を受け取る
    // =====================================

    //(2) 反応動画データを取得
    Part videoPart = request.getPart("video");

    if (videoPart == null || videoPart.getSize() == 0) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    // webapp直下のvideoフォルダの実パスを取得
    String uploadPath = getServletContext().getRealPath("/video");

    // videoフォルダが存在しない場合は作成
    File uploadDir = new File(uploadPath);

    if (!uploadDir.exists()) {
      uploadDir.mkdirs();
    }

    // 保存するファイル名を作成
    String fileName = "reaction_"
        + profileId
        + "_"
        + System.currentTimeMillis()
        + ".webm";

    // 実際に保存するパス
    String savePath = uploadPath + File.separator + fileName;

    // 録画ファイルを保存
    videoPart.write(savePath);

    // DBに登録するファイルパス
    String filePath = "/video/" + fileName;

    // (3) Logicを生成
    ReactionRecordLogic logic = new ReactionRecordLogic();

    // リアクション動画登録処理実行
    boolean result = logic.saveReactionVideo(
        profileId,
        watchedVideoId,
        filePath);

    if (!result) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return;
    }

    // fetch側へ成功を返す
    response.setStatus(HttpServletResponse.SC_OK);
  }
}