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

// 컨트롤러는 요청,응답을 처리 (라우터와 같다.)
// dto, dao, service 에서 구현한 것들을 처리 => db 접속을 하지 않는다.
@WebServlet("/users/signup.do")
public class UsersSignupController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/templates/users/signup.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        UsersDto user=new UsersDto();
        String uId=req.getParameter("u_id");
        String name=req.getParameter("name");
        String pw=req.getParameter("pw");
        String imgPath=req.getParameter("img_path");
        String phone=req.getParameter("phone");
        String email=req.getParameter("email");
        String birth=req.getParameter("birth");
        String gender=req.getParameter("gender");
        String address=req.getParameter("address");
        String detailAdress=req.getParameter("detail_address");

        user.setUId(uId);
        user.setName(name);
        user.setPw(pw);
        user.setImgPath(imgPath);
        user.setPhone(phone);
        user.setEmail(email);
        user.setGender(gender);
        user.setAddress(address);
        user.setDetailAddress(detailAdress);

        if(!birth.trim().equals("")){ // 입력값이 공백이 아닐 경우에만 값 저장
            user.setBirth(birth);
        }else{
            user.setBirth(null);
        }

        int insert=0;
        try {
            UsersService usersService=new UsersServiceImp();
            insert=usersService.register(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(insert>0){
            resp.sendRedirect(req.getContextPath()+"/users/detail.do?u_id="+uId);
        }else {
            resp.sendRedirect(req.getContextPath()+"/users/signup.do");
        }

    }
}
