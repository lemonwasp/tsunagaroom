<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0">
<title>エラーメッセージ</title>

</head>
<body>
  <!-- プロフィール種別によって読み込むCSSを切り替える -->
  <c:choose>
    <c:when test="${profileId ==1}">

      <link rel="stylesheet" href="/css/original-reset.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seniorVideoRecord.css">

    </c:when>

    <c:otherwise>

      <link rel="stylesheet" href="/css/original-reset.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/css/familyVideoRecord.css">


    </c:otherwise>
  </c:choose>

  <div class="screen error-screen">

    <header class="header"></header>

    <main class="error-main">

      <p class="error-title">撮影数上限</p>

      <p class="error-message">
        本日はたくさん<br> 撮影しましたね！<br> また明日<br> 撮影しましょう
      </p>

      <c:choose>


        <c:when test="${profileId == 1}">

          <form action="${pageContext.request.contextPath}/seniorHome.jsp" method="get">
            <button type="submit" class="ok-btn"></button>
          </form>

        </c:when>


        <c:otherwise>

          <form action="${pageContext.request.contextPath}/familyHome.jsp" method="get">
            <button type="submit" class="ok-btn"></button>
          </form>

        </c:otherwise>

      </c:choose>

    </main>
  </div>
</body>
</html>