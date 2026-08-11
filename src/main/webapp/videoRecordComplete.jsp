<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ja">

<head>

<!-- Googleフォント -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Kosugi+Maru&display=swap" rel="stylesheet">

<!-- 文字コード設定 -->
<meta charset="UTF-8">

<!-- スマホ表示対応 -->
<meta name="viewport" content="width=device-width,initial-scale=1.0">

<!-- ブラウザタブタイトル -->
<title>動画撮影完了画面用</title>

<!-- プロフィール種別によって読み込むCSSを切り替える -->
<c:choose>

	<c:when test="${profileId == 1}">
		<link rel="stylesheet" href="/css/original-reset.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/seniorVideoRecord.css">
	</c:when>

	<c:otherwise>
		<link rel="stylesheet" href="/css/original-reset.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/familyVideoRecord.css">
	</c:otherwise>

</c:choose>

</head>

<body>

	<!-- 画面全体 -->
	<div class="screen">

		<!-- ヘッダー -->
		<header class="header">

			<c:choose>
				<c:when test="${profileId == 1}">

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

				</c:when>

				<c:otherwise>

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

				</c:otherwise>
			</c:choose>

		</header>

		<main class="main">

			<!-- 送信完了メッセージ -->
			<p class="complete-message">動画を送りました！</p>

			<!-- 送信完了イラスト -->
			<div class="complete-logo"></div>

			<!-- OKボタン -->
			<c:choose>

				<c:when test="${profileId == 1}">

					<form action="${pageContext.request.contextPath}/SeniorHomeServlet" method="get">
						<input type="hidden" name="sendKind" value="home"> <input type="hidden" name="from" value="list">
						<button type="submit" class="ok-btn"></button>
					</form>
				</c:when>

				<c:otherwise>

					<form action="${pageContext.request.contextPath}/FamilyHomeServlet" method="get">
						<input type="hidden" name="sendKind" value="home"> <input type="hidden" name="from" value="list">
						<button type="submit" class="ok-btn"></button>
					</form>

				</c:otherwise>

			</c:choose>

		</main>

	</div>

	<!-- 通知用JavaScript -->
	<script src="${pageContext.request.contextPath}/assets/js/notification.js"></script>

	<script>
		sendVideoNotification(${profileId});
	</script>

</body>
</html>