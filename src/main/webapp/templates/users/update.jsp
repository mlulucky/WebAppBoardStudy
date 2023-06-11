<%@ page import="com.acorn.webappboard.dto.UsersDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <!-- 유저 상세페이지 => 유저가 필요하다! UsersDto 타입 user -->
    <%
        UsersDto user=(UsersDto) request.getAttribute("user");
        // (UsersDto) 타입으로 형변환 // DetailController 에서 setAttribute 로 보내면 오브젝트타입을 전달하기 때문에
    %>
    <%@include file="/templates/headerNav.jsp"%>
    <main class="container"> <!-- class container => block 여백이 좀 예뻐진다(부트스트랩) -->
        <form action="./update.do" method="post" style="width: 500px; margin: 0 auto">
            <h1 class="my-4">유저 수정폼</h1> <!-- form 태그안에 넣는다. -->
            <input type="hidden" name="u_id" value="<%=user.getUId()%>"> <!-- u_id 를 파라미터로 보내야 한다. -->
            <p>아이디 : <strong><%=user.getUId()%></strong></p> <!-- 유저 출력 -->
            <p>가입일 : <strong><%=user.getPostTime()%></strong></p>
            <p>등급(상태) : <strong><%=user.getPermission()%></strong></p> <!-- 유저서비스 페이지 - 못바꾸게 설정 -->

            <p class="form-floating">
                <input type="text" name="name" value="<%=user.getName()%>" class="form-control" placeholder="??">
                <label>이름 : </label>
            </p>

            <p class="form-floating">
                <input type="password" name="pw" value="<%=user.getPw()%>" class="form-control" placeholder="??">
                <label>패스워드 : </label>
            </p>

            <p>프로필 : <strong><%=user.getImgPath()%></strong></p>
            <p class="input-group">
                <label class="input-group-text">새 프로필 : </label> <!-- 이미지니까 input type=file -->
                <input type="file" name="img_path" value="<%=user.getName()%>" class="form-control" placeholder="??">
            </p>

            <p class="form-floating">
                <input type="text" name="phone" value="<%=user.getPhone()%>" class="form-control" placeholder="??">
                <label>핸드폰 : </label>
            </p>
            <p class="form-floating">
                <input type="text" name="email" value="<%=user.getEmail()%>" class="form-control" placeholder="??">
                <label>이메일 : </label>
            </p>
            <p class="form-floating">
                <input type="date" name="birth" value="<%=user.getBirth()%>" class="form-control" placeholder="??">
                <label>생일 : </label> <!-- 생일은 input type=date -->
            </p>
            <%--
                속성자체가 있는 것이 true  => readonly, selected, checked, defer, async...
                readonly="false"(X) // readonly 존재 자체가 true
                false 를 하고 싶으면 안쓰거나 removeAtrribute("readmonly")로 속성을 지워야 한다.
            --%>
            <p class="form-floating">
                <select name="gender" class="form-control">
                    <option value="MALE" <%if(user.getGender().equals("MALE")){%>selected<%}%>>남자</option> <!-- 셀렉트 기본설정 // selected=true 는 퍼그 문법 -->
                    <option value="FEMALE" <%if(user.getGender().equals("FEMALE")){%>selected<%}%>>여자</option>
                    <option value="NONE" <%if(user.getGender().equals("NONE")){%>selected<%}%>>없음</option>
                </select>
                <label>성별 : </label>
            </p>
            <p class="form-floating">
                <input type="text" name="phone" value="<%=user.getPhone()%>" class="form-control" placeholder="??">
                <label>핸드폰 : </label>
            </p>
            <p class="form-floating">
                <input type="text" name="email" value="<%=user.getEmail()%>" class="form-control" placeholder="??">
                <label>이메일 : </label>
            </p>
            <p class="form-floating">
                <input type="text" name="address" value="<%=user.getAddress()%>" class="form-control" placeholder="??">
                <label>주소 : </label>
            </p>
            <p class="form-floating">
                <input type="text" name="detail_address" value="<%=user.getDetailAddress()%>" class="form-control" placeholder="??">
                <label>주소상세 : </label>
            </p>

            <p class="text-end">
                <a class="btn btn-outline-danger" href="./delete.do?u_id=<%=user.getUId()%>">탈퇴</a>
                <button class="btn btn-outline-primary">수정</button>
            </p>

        </form>
    </main>

</body>
</html>
