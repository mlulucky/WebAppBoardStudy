<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>


    </style>
</head>
<body>
    <%@include file="/templates/headerNav.jsp"%>
    <div class="mx-auto mt-5" style="width:450px">
        <h1 class="py-3">회원가입 폼</h1>
        <form name="userInsertForm" action="./signup.do" method="POST" novalidate>
            <p class="form-floating">
                <input class="form-control" type="text" name="u_id" value="">
                <label>아이디</label>
                <p id="uIdMsg"></p>
            </p>

            <p class="form-floating">
                <input class="form-control" type="text" name="name" value="">
                <label>이름</label>
                <p id="nameMsg"></p>
            </p>
            <p class="form-floating">
                <input class="form-control" type="text" name="pw" value="">
                <label>패스워드</label>
            </p>
            <p class="form-floating">
                <input class="form-control" type="text" name="pw_check" value="">
                <label>패스워드체크</label>
            </p>
            <p class="input-group">
                <label class="input-group-text">프로필</label>
                <input class="form-control" type="file" name="img_path" value="">
            </p>
            <p class="form-floating">
                <input class="form-control" type="text" name="phone" value="">
                <label>핸드폰</label>
            </p>
            <p class="form-floating">
                <input class="form-control" type="text" name="email" value="">
                <label>이메일</label>
            </p>
            <p class="form-floating">
                <input class="form-control" type="date" name="birth" value="">
                <label>생일</label>
            </p>
            <p class="form-floating">
                <select class="form-control" name="gender">
                    <option value="FEMALE">여성</option>
                    <option value="MALE">남성</option>
                    <option value="NONE">미정</option>
                </select>
                <label>성별</label>
            </p>
            <p class="form-floating">
                <input class="form-control" type="text" name="address" value="">
                <label>주소</label>
            </p>
            <p class="form-floating">
                <input class="form-control" type="text" name="detail_address" value="">
                <label>상세주소</label>
            </p>
            <p class="text-end">
                <button class="btn btn-outline-warning" type="reset">초기화</button>
                <button class="btn btn-outline-primary">회원가입</button>
            </p>

        </form>
    </div>
</body>
</html>
