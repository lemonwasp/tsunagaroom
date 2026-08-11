<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>つながるーむ 高齢者ホーム</title>

<!-- CSSファイルを読み込む -->
<link rel="stylesheet" href="/css/original-reset.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/seniorHome.css">
</head>

<body>

	<div class="senior-home">

		<!-- 上部ロゴ -->
		<header class="senior-header">
			<img src="<%=request.getContextPath()%>/img/senior_hedder.png" alt="つながるーむ" class="senior-header-logo">
			<!-- 左の家 -->
			<form action="seniorTitle.jsp" method="post" class="header-home">
				<input type="submit" class="header-submit">
			</form>

			<!-- 右の家 -->
			<form action="familyTitle.jsp" method="post" class="header-family">
				<input type="submit" class="header-submit">
			</form>

			<!-- タイトル -->
			<form action="seniorLock.jsp" method="get" class="header-title">
				<input type="submit" class="header-submit">
			</form>
		</header>

		<!-- ようこそ表示 -->
		<p class="welcome-text">ようこそ！さちこさん</p>

		<!-- 動画を見るボタン -->
		<form action="<%=request.getContextPath()%>/VideoPlayServlet" method="get">
			<button class="image-button watch-button" type="submit">
				<img src="<%=request.getContextPath()%>/img/senior_button_watch.png" alt="動画を見る">
			</button>
		</form>


		<!-- 動画を撮って送る -->
		<form action="<%=request.getContextPath()%>/VideoRecordServlet" method="get">

			<!-- 家族側プロフィールID -->
			<input type="hidden" name="profileId" value="2">

			<button class="image-button send-button" type="submit">

				<img src="<%=request.getContextPath()%>/img/senior_button_send.png" alt="動画を撮って送る">

			</button>

		</form>

		<!-- 撮影回数 -->
		<p class="count-text">今日はあと${videoCountDTO.remainingCount}回撮れます</p>

	</div>

</body>
</html>