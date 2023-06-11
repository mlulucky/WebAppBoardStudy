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

@WebServlet("/users/detail.do")
public class UsersDetailController extends HttpServlet {
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
        req.getRequestDispatcher("/templates/users/detail.jsp").forward(req,resp);
        // req.getRequestDispatcher => req(유저) 와 resp(응답경로 jsp) 를 기반으로 렌더링을 하겠다
    }
}
