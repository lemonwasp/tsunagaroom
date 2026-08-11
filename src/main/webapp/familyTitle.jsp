<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>つながるーむ</title>

<!-- CSSファイルを読み込む -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/original-reset.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/familyTitle.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/notification.css">
</head>

<body>
  <div id="notificationBanner"></div>

  <!-- 画面全体をボタンにするフォーム -->
  <form action="${pageContext.request.contextPath}/FamilyHomeServlet" method="get">

    <button type="submit" name="sendKind" value="home" class="start-screen"></button>

  </form>
  <script src="${pageContext.request.contextPath}/assets/js/notification.js"></script>

</body>
</html>