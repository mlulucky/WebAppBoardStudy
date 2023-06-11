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

@WebServlet("/users/delete.do")
public class UsersDeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.getRequestDispatcher("/templates/users/delete.jsp").forward(req,resp);
        String uId=req.getParameter("u_id");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String uId=req.getParameter("u_id");
        String pw=req.getParameter("pw");
        String redirectPage="";
//        UsersDto user=null;
        int delete=0;
        UsersService usersService= null;
        try {
            usersService = new UsersServiceImp();
            delete=usersService.dropOut(uId,pw);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(delete>0){
            redirectPage=(req.getContextPath()+"/users/list.do");
        }else{
            redirectPage=(req.getContextPath()+"/users/delete.do");
        }
        resp.sendRedirect(redirectPage);

    }
}
