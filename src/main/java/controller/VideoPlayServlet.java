package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dto.VideoDTO;
import logic.VideoCountLogic;
import logic.VideoPlayLogic;

@WebServlet("/VideoPlayServlet")
public class VideoPlayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// アルバム一覧から動画ID付きで遷移してきた場合
		String videoIdStr = request.getParameter("videoId");

		if (videoIdStr != null && !videoIdStr.isEmpty()) {

			int videoId = Integer.parseInt(videoIdStr);

			VideoPlayLogic logic = new VideoPlayLogic();
			VideoDTO videoDTO = logic.getVideo(videoId);

			if (videoDTO == null) {
				request.setAttribute("message", "動画が見つかりません。");
				request.getRequestDispatcher("/WEB-INF/jsp/error.jsp")
						.forward(request, response);
				return;
			}

			request.setAttribute("videoDTO", videoDTO);

			// アルバムから再生する場合
			request.setAttribute("isAlbumPlay", true);

			// リアクション録画を開始させない
			request.setAttribute("todayReactionVideoCount", 1);

			// JSPでCSSやJSを切り替えるために登録
			HttpSession session = request.getSession();
			int profileId = (int) session.getAttribute("profileId");
			request.setAttribute("profileId", profileId);

			logic.updateReadStatus(videoId);

			request.getRequestDispatcher("/videoPlay.jsp")
					.forward(request, response);
			return;
		}

		// (1) VideoPlayLogicのgetOldestUnreadVideoメソッドを呼び出す
		VideoPlayLogic logic = new VideoPlayLogic();
		VideoDTO videoDTO = logic.getOldestUnreadVideo();

		// セッション取得
		HttpSession session = request.getSession();
		// ログイン機能未実装のため、高齢者側プロフィールIDを固定する
		int profileId = 1;

		// ReactionRecordServletでも使うため、セッションスコープに登録
		session.setAttribute("profileId", profileId);

		// JSPでCSSを切り替えるため、リクエストスコープに登録
		request.setAttribute("profileId", profileId);

		// (2) VideoCountLogicのcheckReactionCountメソッドを呼び出す
		VideoCountLogic videoCountLogic = new VideoCountLogic();
		int todayReactionVideoCount = videoCountLogic.checkReactionCount(profileId);

		// JSPでCSS切替用に登録
		request.setAttribute("profileId", profileId);

		// (3) 未読動画あり かつ 本日リアクション動画未撮影の場合
		if (videoDTO != null && todayReactionVideoCount == 0) {

			// VideoDTOをセッションスコープに登録
			session.setAttribute("videoDTO", videoDTO);

			// 本日の反応動画数をリクエストスコープに登録
			request.setAttribute("todayReactionVideoCount", todayReactionVideoCount);

			// 再生しながらリアクション動画撮影画面へ遷移
			System.out.println("ReactionRecordServlet");
			request.getRequestDispatcher("/videoPlay.jsp")
					.forward(request, response);
			return;

			// (4) 未読動画ありの場合
		} else if (videoDTO != null) {

			// VideoDTOをセッションスコープに登録
			session.setAttribute("videoDTO", videoDTO);

			// 本日の反応動画数をリクエストスコープに登録
			request.setAttribute("todayReactionVideoCount", todayReactionVideoCount);

			// 動画再生画面へ遷移
			System.out.println("videoPlay");
			request.getRequestDispatcher("/videoPlay.jsp")
					.forward(request, response);
			return;

			// (5) 未読動画なしの場合
		} else {

			// セッションスコープからVideoDTOを削除
			session.removeAttribute("videoDTO");

			// 動画一覧画面へ遷移
			System.out.println("VideoListServlet");
			request.getRequestDispatcher("/VideoListServlet")
					.forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}