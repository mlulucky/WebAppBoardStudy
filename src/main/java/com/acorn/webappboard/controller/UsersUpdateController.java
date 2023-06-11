package com.acorn.webappboard.controller;

import com.acorn.webappboard.dto.UsersDto;
import com.acorn.webappboard.service.UsersService;
import com.acorn.webappboard.service.UsersServiceImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/users/update.do")
// 업데이트 수정폼과 디테일 상세페이지는 동일하므로 파일을 복사붙인다. => 경로만 update 로 수정하기
public class UsersUpdateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uId=req.getParameter("u_id");
        if(uId==null) {// 파라미터가 안넘어오는 에러 - 400 에러
            resp.sendError(400,"잘못된 요청입니다.");
            return; // 잘못된 요청으로 코드 종료
        }
        UsersDto user=null; // 처음엔 null 로 초기화 => db 에서 확인예정
        try{
            UsersService usersService=new UsersServiceImp();
            user=usersService.detail(uId);
        }catch (Exception e){
            // resp.sendError(500,"db 처리 오류!"); // 500에러처리 해도되고 안해도 되고..
            e.printStackTrace();
        }
        // resp.getWriter().println(user); // 화면출력 - 유저 있는지 없는지 테스트 => 출력하는 JSP 만들기
        req.setAttribute("user",user); // 🍎setAttribute => 모두 오브젝트 타입으로 전달이 된다. // 렌더링할 JSP 에 유저(오브젝트)를 전달
        req.getRequestDispatcher("/templates/users/update.jsp").forward(req,resp);
        // req.getRequestDispatcher => req(유저) 와 resp(응답경로 jsp) 를 기반으로 렌더링을 하겠다
    }

    // 수정버튼을 누르면 post req.body 에 파라미터가 넘어온다. POST 를 호출??


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        UsersDto user=new UsersDto(); // 파라미터로 넘어온 user 정보를 db 에 저장할 때 사용
        // 전달받은 파라미터의 값을 가져오기
        String uId=req.getParameter("u_id");
        String name=req.getParameter("name");
        String pw=req.getParameter("pw");
        String imgPath=req.getParameter("img_path");
        String phone=req.getParameter("phone");
        String email=req.getParameter("email");
        String gender=req.getParameter("gender");
        String birth=req.getParameter("birth");
//        String postTime=req.getParameter("post_time");
        String address=req.getParameter("address");
        String detailAddress=req.getParameter("detail_address");

        // birth db에서 date 타입 : 데이터베이스는 문자열을 자동으로 데이트타입으로 형변환 해준다.
        // 데이트 타입일때 파라미터로 불러오기
//        SimpleDateFormat bd=new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            Date birthDate=bd.parse(birth);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }

        // 유저에 가져온 파라미터 값을 셋팅해주기
        user.setUId(uId);
        user.setName(name);
        user.setPw(pw);
        user.setImgPath(imgPath);
        user.setPhone(phone);
        user.setEmail(email);
        user.setGender(gender);
        user.setBirth(birth);
//        user.setPostTime(postTime);
        user.setAddress(address);
        user.setDetailAddress(detailAddress);

        int update=0;
        try{
            UsersService usersService=new UsersServiceImp();
            update=usersService.modify(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(update>0){ // 업데이트 성공시
            resp.sendRedirect(req.getContextPath()+"/users/detail.do?u_id="+uId);
        }else {
            resp.sendRedirect(req.getContextPath()+"/users/update.do?u_id="+uId);
        }
//        resp.setContentType("text/html;charset=UTF-8");
//        resp.getWriter().println(user); // 잘 수정됬는지 출력
    }
    // 3/17 금 과제
    // user 회원가입! + ajax(u_idCheck 구현)
    // board CRUD + (search+paging)
}
