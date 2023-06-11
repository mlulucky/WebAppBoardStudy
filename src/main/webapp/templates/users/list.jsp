<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        List<UsersDto> users= (List<UsersDto>) request.getAttribute("users");
    %>
    <%@include file="/templates/headerNav.jsp"%>
    <table class="table table-striped table-hover container">
        <thead>
            <tr>
                <th>u_id</th>
                <th>name</th>
                <th>가입일</th>
                <th>생년월일</th>
                <th>성별</th>
                <th>주소</th>
                <th>권한</th>
            </tr>
        </thead>
        <tbody>

        <!-- users 유저리스트에서 데이터를 하나씩 꺼내면서 행을 생성하는 반복문 -->
        <%
            for(UsersDto user : users) {
                // 변수선언 값 대입
                String uId = user.getUId();
                String name = user.getName();
                Date postTime = user.getPostTime();
//                String birth = user.getBirth();
                String birthStr = user.getBirth();
                String gender = user.getGender();
                String address = user.getAddress();
                String permission = user.getPermission();

                // SimpleDateFormat 객체 생성 - 데이트 날짜포맷 변경
                SimpleDateFormat format = new SimpleDateFormat("yyyy. MM. dd");
                // 포맷 적용하여 문자열로 변환
                String postTimeDate = format.format(postTime);
                // yyyy-mm-dd 형식의 문자열을 Date 객체로 변환
                SimpleDateFormat birthDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate = birthDateFormat.parse(birthStr);
                // 원하는 형식으로 변환
                String birth =format.format(birthDate);
        %>
        <tr>
            <td><%=uId%></td>
            <td><%=name%></td>
            <td><%=postTimeDate%></td>
            <td><%=birth%></td>
            <td><%=gender%></td>
            <td><%=address%></td>
            <td><%=permission%></td>
        </tr>

        <%
            }
        %>

        </tbody>
    </table>



</body>
</html>
