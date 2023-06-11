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
import java.util.List;

@WebServlet("/users/userList.do")
public class UsersListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UsersDto> users =null;

        try {
            UsersService usersService=new UsersServiceImp();
            users=usersService.list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        req.setAttribute("users",users);
//        req.setAttribute("rs",rs);
        req.getRequestDispatcher("/templates/users/list.jsp").forward(req,resp);


    }
}
