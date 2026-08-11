<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">

<meta name="viewport" content="width=device-width,initial-scale=1.0">

<title>動画再生</title>

<!--cssを選択-->
<c:choose>
	<c:when test="${profileId == 2}">
		<link rel="stylesheet" href="/css/original-reset.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/familyVideoPlay.css">
	</c:when>

	<c:when test="${profileId == 1}">
		<link rel="stylesheet" href="/css/original-reset.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/seniorVideoPlay.css">
	</c:when>

	<c:otherwise>
		<link rel="stylesheet" href="/css/original-reset.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/familyVideoPlay.css">
	</c:otherwise>
</c:choose>

<script>
	const contextPath = "${pageContext.request.contextPath}";
	const reactionCount = ${empty todayReactionVideoCount ? 0 : todayReactionVideoCount};
	const profileId = ${empty profileId ? sessionScope.profileId : profileId};
	const isAlbumPlay = ${empty isAlbumPlay ? false : isAlbumPlay};
</script>
<script src="${pageContext.request.contextPath}/assets/js/reactionRecord.js" defer></script>

</head>
<body>

	<div class="page">

		<header class="header">

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


		<main class="main">

			<c:if test="${profileId == 2 && videoDTO.videoType == 2}">
				<p class="message-title reaction-title" id="messageTitle">さちこさんのようす</p>
			</c:if>

			<c:if test="${profileId == 1}">
				<p class="message-title" id="messageTitle">動画が届きました！</p>
			</c:if>

			<!--			<p class="message-title" id="messageTitle">動画が届きました！</p>-->

			<div class="video-area">
				<video id="videoPlayer" class="video-thumbnail">
					<source src="${pageContext.request.contextPath}${videoDTO.filePath}" type="video/webm">
				</video>

				<button type="button" id="playButton" class="play-button">
					<img src="${pageContext.request.contextPath}/img/button_play.png" alt="再生">
				</button>

				<div class="video-time" id="videoTime">0:00/0:00</div>

			</div>

			<!--	年月日を表示する-->
			<p class="video-date">${videoDTO.createdAt.year}/${videoDTO.createdAt.monthValue}/${videoDTO.createdAt.dayOfMonth}</p>
			<p class="message-bottom" id="messageBottom">
				見ているようすを動画にして<br> ご家族へお届けします
			</p>

			<a href="${pageContext.request.contextPath}/VideoListServlet" id="albumButton" class="album-button" aria-label="アルバムへ"></a>

		</main>


	</div>

	<!-- 通知のJSを読み込む -->
	<script src="${pageContext.request.contextPath}/assets/js/notification.js"></script>

</body>
</html>