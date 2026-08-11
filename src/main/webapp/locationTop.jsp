<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
ArrayList<LocationDTO> locationList = (ArrayList<LocationDTO>) session.getAttribute("locationList");
%>

<%@ page import="java.util.ArrayList"%>
<%@ page import="dto.LocationDTO"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>あしあと一覧</title>
<link rel="stylesheet" href="/css/original-reset.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/locationTop.css">
</head>

<body>

	<div class="screen">

		<header class="header">
			<img src="${pageContext.request.contextPath}/img/family_hedder.png" alt="ヘッダー">
		</header>

		<div class="title-area">
			<div class="title-text">
				<p>さちこさんの</p>
				<p>あしあと</p>
			</div>

			<img class="footprint" src="${pageContext.request.contextPath}/img/footprintmark.png" alt="足跡">
		</div>

		<main class="location-list">

			<%
			if (locationList != null && !locationList.isEmpty()) {
				for (LocationDTO location : locationList) {
			%>

			<form action="${pageContext.request.contextPath}/LocationServlet" method="post" class="location-card">

				<input type="hidden" name="sendKind" value="FootPrintMap"> <input type="hidden" name="locationId" value="<%=location.getId()%>">

				<%
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
				%>

				<p class="date">
					<%=location.getCreatedAt().format(formatter)%>
				</p>
				<p class="address">
					<%
					String address = location.getAddress();

					if (address != null) {
						address = address.replaceFirst("(.+?(区|町))", "$1<br>");
						out.print(address);
					}
					%>
				</p>

				<!-- 詳細ボタン -->
				<button type="submit" class="detail-link"></button>
			</form>

			<%
			}
			} else {
			%>

			<p>位置情報がありません。</p>

			<%
			}
			%>

		</main>

		<div class="back-area">
			<form action="${pageContext.request.contextPath}/FamilyHomeServlet" method="get">

				<input type="hidden" name="sendKind" value="FootPrintBack">

				<button type="submit" class="back-button">
					<img src="${pageContext.request.contextPath}/img/family_button_back.png" alt="戻る">
				</button>
			</form>
		</div>

	</div>
</body>
</html>