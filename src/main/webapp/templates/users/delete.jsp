<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
 <%@include file="/templates/headerNav.jsp"%>
    <div class="mx-auto mt-5" style="width: 400px"> <!-- form 태그 너비 조정 (div 로 감싸기) -->
        <h1 class="py-3">삭제 폼</h1> <!-- JSP 에서 자바코드를 출력하는 법 --> <!-- 마진상쇄 문제 - 마진이 부모가 가진 마진과 자식이 가진 마진이 상쇄된다. 둘다 위에 주어지면 마진상쇄. => 둘중 하나는 패딩으로 간격 주기  -->
        <form action="./delete.do" method="POST"> <!-- 현재 users 폴더안에 있는 파일 이므로 /users/login.do 아니고 현재폴더./ 파일명로 설정 -->
            <!-- ./users/login.do ==>결과) /WebAppBoardStudy/users/users/login.do -->
            <p class="form-floating"> <!-- 인라인태그는 공백도 메모리로 들어가기 때문에 공백(줄바꿈)을 최대한 없애는 편 -->
                <input class="form-control" type="text" name="u_id" value="user05">
                <label>유저 아이디</label>
            </p>
            <p class="form-floating">
                <input class="form-control" type="password" name="pw" value="1234">
                <label>패스워드</label>
            </p>
            <p class="text-end"> <!-- 인라인 블럭은 텍스트로 취급하여, text-end 로 정렬된다. -->
                <a class="btn btn-outline-warning" href="./delete.do">삭제</a>
                <!-- 정부에서 의뢰줄때 잘했는지 판단할때 표준 - (스프링, 화면 부트스트랩) == 전자정보 프레임워크라 한다.
                    회사에서 스프링을 하는 이유. 부트스트랩을 배우는 이유
                 -->
            </p>
        </form>
    </div>
</body>
</html>
