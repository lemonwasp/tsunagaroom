<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>動画撮影画面用</title>
</head>
<body>

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

	<!-- 画面全体 -->
	<div class="screen">

		<!-- ヘッダー -->
		<header class="header"></header>

		<main class="main">


			<!-- 追加：録画時間表示バー -->
			<div class="record-bar">
				<span class="record-dot"></span> <span id="recordTime">00:00:00</span>
			</div>
			<!-- カメラ映像表示エリア -->
			<div class="camera-area">

				<!-- カメラ映像表示用 -->
				<video id="camera" autoplay muted playsinline></video>

			</div>

			<!-- 録画開始ボタン -->
			<button type="button" id="startBtn" class="start-btn"></button>

			<!-- 録画終了ボタン（初期状態は非表示） -->
			<button type="button" id="stopBtn" class="stop-btn hidden"></button>

			<!-- 戻るボタン -->
			<c:choose>


				<c:when test="${profileId == 1}">

					<form action="${pageContext.request.contextPath}/SeniorHomeServlet" method="get">
						<input type="hidden" name="sendKind" value="home">
						<button type="submit" class="back-btn"></button>
					</form>

				</c:when>


				<c:otherwise>

					<form action="${pageContext.request.contextPath}/FamilyHomeServlet" method="get">
						<input type="hidden" name="sendKind" value="home">
						<button type="submit" class="back-btn"></button>
					</form>

				</c:otherwise>

			</c:choose>

		</main>


		<!-- JavaScriptへprofileIdを渡す -->
		<input type="hidden" id="profileId" value="${profileId}">

	</div>

	<script>
		const contextPath = '${pageContext.request.contextPath}';
	</script>
	<script src="${pageContext.request.contextPath}/assets/js/videoRecord.js?v=12"></script>


</body>
</html>