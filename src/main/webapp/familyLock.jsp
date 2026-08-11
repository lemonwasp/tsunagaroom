<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/original-reset.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/lock.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/notification.css">
</head>
<body>
  <div id="notificationBanner"></div>
  <img src="${pageContext.request.contextPath}/img/lock.png" alt="家族側画面"class="lock-image">
  <script src="${pageContext.request.contextPath}/assets/js/notification.js"></script>
  
  <form action="familyTitle.jsp" method="post">
    <input type="submit" value="家族側">
  </form>
</body>

</html>