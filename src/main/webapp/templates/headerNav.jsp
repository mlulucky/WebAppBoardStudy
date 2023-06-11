<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %> <!-- index.jsp 파일과 충돌됨 text/html;charset=UTF-8 --> <!-- JSP 문서타입 선언 - 절대 지우면 안된다! -->--%>
<%@ page language="java" pageEncoding="UTF-8"%> <!-- 한글깨짐 방지 인코딩 적용 -->
<%@ page import="com.acorn.webappboard.dto.UsersDto" %>
<%
String contextPath=request.getContextPath(); // 컨텍스트패스 변수로 선언해서 사용
%>
<link rel="stylesheet" href="<%=contextPath%>/public/bootstrap/css/bootstrap.css">
<script src="<%=contextPath%>/public/bootstrap/js/bootstrap.bundle.js"></script>
<script src="<%=contextPath%>/public/js/userInsert.js", defer=true></script>
<!-- 🍎톰캣이 배포할때 자동으로 붙여주는 컨텍스트 패스 추가(배포할때 붙이는 애플리케이션 컨텍스트 이름)자바파일을 표시하겠다~  -->
<!-- 스프링에서는 컨텍스트패스가 없다 - 왜? 스프링안에 톰캣이 있어서. 톰캣이 배포하는 파일이 스프링밖에 없어서, 컨텍스트패스로 구분할 필요가 없음 -->
<!-- navbar => 항상 포함되는 링크와 스크립트 요소 -->
<!-- bootstrap.js - 몇개의 컴포넌트 디자인을 사용x (외부라이브러리 포함x) // bundle.js - 외부라이브러리도 포함 -->
<!-- min.css : 압축버전. 배포할때 // css : 클래스 자동완성 됨 -->
<!-- 부트스트랩 navbar 복붙 -->
<!-- jsp 파일은 자바이지만 인터프린트 언어(컴파일 x)로 파일 수정 후 톰캣 배포없이 페이지 새로고침을 하면 반영된다. -->
<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="<%=contextPath%>/">HOME</a> <!-- 컨텍스트 패스 주소 + 인덱스 주소 -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Model1Board
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="<%=contextPath%>/model1/boardList.do">리스트</a></li> <!-- 어떤경로에서든 적용이 될려면 꼭 최상위 컨텍스트를 붙인 경로 ~ 최하위 경로 를 적용 -->
                        <li><a class="dropdown-item" href="<%=contextPath%>/model1/boardInsert.do">등록폼</a></li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Model2User
                    </a>
                    <ul class="dropdown-menu">
<%--                        🔥컨텍스트패스 붙이는이유?!--%>
                        <li><a class="dropdown-item" href="<%=contextPath%>/users/userList.do">리스트</a></li>
                    </ul>
                </li>
            </ul>
<%
    Object loginUser_obj=session.getAttribute("loginUser");
%>
            <!-- 로그인이 되면 안보이기 // 로그인 아니면 보이기 -->
            <ul class="d-flex breadcrumb mb-0" role="search">
                <%if(loginUser_obj==null){%>
                    <li class="breadcrumb-item"><a href="<%=contextPath%>/users/login.do">로그인</a></li>
                    <li class="breadcrumb-item"><a href="<%=contextPath%>/users/signup.do">회원가입</a></li>
                <%
                    }else{ // { 열고 닫기
                        UsersDto loginUser=(UsersDto) loginUser_obj; // 타입의 다형성
                        // 다형성
                        // 타입의 다형성 : 매개변수나 변수가 여러 타입의 객체를 참조할 수 있다. => 타입을 형변환시 오류가 날 수 있어서 해결법 => 제네릭!!
                        // 오버로드 : 함수는 1개인데 매개변수, 타입을 다르게 하여 함수는 여러개
                        // 오버라이딩 : 부모의 함수를 재정의
                %>
                    <li class="breadcrumb-item">
                        <a href="<%=contextPath%>/users/detail.do?u_id=<%=loginUser.getUId()%>">
                            (<%=loginUser.getUId()%>)<%=loginUser.getName()%>
                        </a>
                    </li>
                    <li class="breadcrumb-item">
                        <a href="<%=contextPath%>/users/logout.do">로그아웃</a>
                    </li>
                <%}%>
            </ul>
        </div>
    </div>
</nav>
