<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.LocationDTO"%>

<%
LocationDTO selectedLocation = (LocationDTO) session.getAttribute("selectedLocation");
String googleMapsApiKey = System.getenv("GOOGLE_MAPS_API_KEY");
if (googleMapsApiKey == null) {
	googleMapsApiKey = "";
}
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>locationMap</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/original-reset.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/locationMap.css">
</head>

<body>
	<div class="screen">

		<header class="header">
			<img src="${pageContext.request.contextPath}/img/family_hedder.png" alt="家族ヘッダー画像">

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

		<div class="title-area">
			<div class="title-text">
				<p>さちこさんの</p>
				<p>あしあと</p>
			</div>

			<img class="footprint" src="${pageContext.request.contextPath}/img/footprintmark.png" alt="あしあとマーク">
		</div>

		<%
		if (selectedLocation != null) {
		%>

		<input type="hidden" id="latitude" value="<%=selectedLocation.getLatitude()%>"> <input type="hidden" id="longitude" value="<%=selectedLocation.getLongitude()%>"> <input type="hidden"
			id="address" value="<%=selectedLocation.getAddress()%>">

		<div class="map-box">
			<div id="map"></div>

			<div class="address-box">
				<p id="addressText"></p>
			</div>
		</div>

		<%
		} else {
		%>

		<p>位置情報が選択されていません。</p>

		<%
		}
		%>

		<div class="back-area">
			<form action="${pageContext.request.contextPath}/LocationServlet" method="post">
				<input type="hidden" name="sendKind" value="FootPrintTop">

				<button type="submit" class="back-button">
					<img src="${pageContext.request.contextPath}/img/family_button_back.png" alt="家族戻るボタン画像">
				</button>
			</form>
		</div>

	</div>

	<script src="${pageContext.request.contextPath}/assets/js/location.js"></script>
	<% if (!googleMapsApiKey.isBlank()) { %>
	<script async defer src="https://maps.googleapis.com/maps/api/js?key=<%=googleMapsApiKey%>&callback=initMap"></script>
	<% } else { %>
	<script>console.warn('GOOGLE_MAPS_API_KEY is not set; map loading is disabled.');</script>
	<% } %>

</body>
</html>