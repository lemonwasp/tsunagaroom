<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>動画一覧</title>

<c:choose>
	<c:when test="${sessionScope.profileId == 1}">
		<link rel="stylesheet" href="/css/original-reset.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/seniorVideoList.css">
	</c:when>

	<c:when test="${sessionScope.profileId == 2}">
		<link rel="stylesheet" href="/css/original-reset.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/familyVideoList.css">
	</c:when>
</c:choose>

</head>

<body>

	<div class="container">

		<div class="header"></div>

		<div class="title-area">
			<div class="title-text">アルバム(${videoCount}件)</div>
			<button class="search-button"></button>
		</div>

		<div class="video-grid">

			<c:forEach var="video" items="${videoList}">

				<div class="video-item">

					<a href="${pageContext.request.contextPath}/VideoPlayServlet?videoId=${video.id}">

						<div class="thumbnail-area">

							<video class="thumbnail" preload="metadata" muted playsinline>
								<source src="${pageContext.request.contextPath}${video.filePath}" type="video/webm">
							</video>

							<img src="${pageContext.request.contextPath}/img/button_play.png" alt="再生" class="play-button">

						</div>
					</a>

					<!-- 年月日を表示する-->
					<div class="video-date">${video.createdAt.year}/${video.createdAt.monthValue}/${video.createdAt.dayOfMonth}</div>

				</div>

			</c:forEach>

		</div>
		<div class="paging-area">
			<button class="previous-button"></button>
			<span class="page-number">1/1</span>
			<button class="next-button"></button>
		</div>


		<!-- 戻るボタン -->
		<div class="back-area">

			<c:choose>

				<c:when test="${sessionScope.profileId == 1}">
					<button class="back-button" type="button" onclick="location.href='${pageContext.request.contextPath}/SeniorHomeServlet?sendKind=home&from=list'"></button>
				</c:when>

				<c:when test="${sessionScope.profileId == 2}">
					<button class="back-button" type="button" onclick="location.href='${pageContext.request.contextPath}/FamilyHomeServlet?sendKind=home'"></button>
				</c:when>

			</c:choose>

		</div>

	</div>

</body>
</html>