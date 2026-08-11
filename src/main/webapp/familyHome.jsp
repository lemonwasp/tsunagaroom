<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>つながるーむ 家族ホーム</title>
<link rel="stylesheet" href="/css/original-reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/familyHome.css">

</head>

<body>

	<div class="family-home">

		<!-- 上部ロゴ -->
		<header class="family-header">

			<img src="<%=request.getContextPath()%>/img/family_hedder.png" alt="つながるーむ" class="header-logo">

			<!-- 左の家 -->
			<form action="seniorTitle.jsp" method="post" class="header-home">
				<input type="submit" class="header-submit">
			</form>

			<!-- 右の家 -->
			<form action="familyTitle.jsp" method="post" class="header-family">
				<input type="submit" class="header-submit">
			</form>

			<!-- タイトル -->
			<form action="familyLock.jsp" method="get" class="header-title">
				<input type="submit" class="header-submit">
			</form>

		</header>

		<!-- ようこそ表示 -->
		<p class="welcome-text">ようこそ！三浦さん</p>



		<!-- 動画を見る -->
		<form action="<%=request.getContextPath()%>/VideoListServlet">
			<input type="hidden" name="sendKind" value="VideoList"> <input type="hidden" name="userId" value="2">

			<button class="image-button watch-button" type="submit">
				<img src="<%=request.getContextPath()%>/img/family_button_watch.png" alt="動画を見る">
			</button>

		</form>

		<!-- 新着動画通知 -->
		<c:if test="${!videoCountDTO.viewed}">
			<div class="new-video-message">新着動画があります</div>
		</c:if>

		<!-- 動画を撮って送る -->
		<form action="<%=request.getContextPath()%>/VideoRecordServlet" method="get">

			<!-- 家族側プロフィールID -->
			<input type="hidden" name="profileId" value="2">

			<button class="image-button send-button" type="submit">

				<img src="<%=request.getContextPath()%>/img/family_button_send.png" alt="動画を撮って送る">

			</button>

		</form>

		<!-- 撮影回数 -->
		<p class="count-text">本日はあと${videoCountDTO.remainingCount}回撮れます</p>

		<!-- あしあと -->
		<form action="<%=request.getContextPath()%>/LocationServlet" method="post">

			<input type="hidden" name="sendKind" value="FootPrint"> <input type="hidden" name="userId" value="1">

			<button class="image-button footprint-button" type="submit">

				<img src="<%=request.getContextPath()%>/img/family_button_footprint.png" alt="さちこさんのあしあと">

			</button>

		</form>

		<!-- 下部メニュー -->
		<footer class="family-footer">

			<div class="footer-item" onclick="location.href='familyStartupSound.jsp'">
				<img src="<%=request.getContextPath()%>/img/family_button_startupsound.png" alt="起動音設定" class="startup-image">
			</div>

			<div class="footer-item" onclick="location.href='familyStep.jsp'">
				<img src="<%=request.getContextPath()%>/img/family_button_step.png" alt="これまでのあゆみ" class="step-image">
			</div>

		</footer>

	</div>

</body>
</html>