<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <!-- 유저 상세페이지 => 유저가 필요하다! UsersDto 타입 user -->
    <%
        UsersDto user=(UsersDto) request.getAttribute("user");
        // (UsersDto) 타입으로 형변환 // DetailController 에서 **setAttribute 로 보내면 오브젝트타입을 전달하기 때문에
    %>
<%--    req 는 요청정보. 요청헤드에 파라미터 쿼리스트링이 포함되어있다. 요청정보는 유알엘 인코딩으로 되어있다. 자바문서에서 처리하려면 쿼리스트링을 유알엘 인코딩에서 유니에프 9 인코딩으로 바꿔야한다.--%>
<%--    응답할 문서를 작성 클라이언트가 그림을 그린다. html 파일을 utf-8 인코딩으로 변환 해야  컨텐츠타입에서 제이슨, 에이치티엠엘 이미지등을 포함한다.--%>
<%--    url 은컨텍스트 패스가 필요. 서버내부에서는 필요 없다. 클라이언트가 요청하는 유알엘에서는 필요.--%>
    <%@include file="/templates/headerNav.jsp"%> <!-- 인클루드는 파일을 서버내부 웹앱 파일 경로를 클라이언트에게 전달  - 상대경로 -->
    <main class="container"> <!-- class container => block 여백이 좀 예뻐진다(부트스트랩) -->
        <h1 class="my-4">유저 상세</h1>
        <p>아이디 : <strong><%=user.getUId()%></strong></p> <!-- 유저 출력 -->
        <p>이름 : <strong><%=user.getName()%></strong></p>
        <p>가입일 : <strong><%=user.getPostTime()%></strong></p>
        <p>프로필 : <strong><%=user.getImgPath()%></strong></p>
        <p>핸드폰 : <strong><%=user.getPhone()%></strong></p>
        <p>이메일 : <strong><%=user.getEmail()%></strong></p>
        <p>생일 : <strong><%=user.getBirth()%></strong></p>
        <p>성별 : <strong><%=user.getGender()%></strong></p>
        <p>주소 : <strong><%=user.getAddress()%></strong></p>
        <p>주소상세 : <strong><%=user.getDetailAddress()%></strong></p>
        <p>등급(상태) : <strong><%=user.getPermission()%></strong></p>
        <p><a href="./update.do?u_id=<%=user.getUId()%>">수정폼</a></p> <!-- 수정폼은 디테일 페이지와 동일하다 -->
    </main>
<%--    시멘틱요소 :
        검색에 등록할 때, 검색엔진 로봇이 해당 웹앱을 분석할 때 태그를 보면서 구조 및 정보를 파악하기 위해 태그를 사용한다.
        프로그래머가 해당 태그의 이름의 어떤 목적인지 파악할 때 사용
        시각장애인이 해당 웹앱을 이용할 때 말로 설명하는 것을 시멘틱요소를 이용한다.
        b 태그 : 두껍게
        strong 태그 : 두껍게 + 강조
        div : 블럭
        nav : 블럭+네비게이션
        header : 블럭 + 헤더정보
        footer : 블럭 + 회사정보
        main : 블럭 + 내용
--%>
</body>
</html>
