package com.acorn.webappboard.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


// 개발자 추세를 따라서 발전
@WebServlet("/users/logout.do")
public class UsersLogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        // session.invalidate();// 쿠키 만료(유효)시간을 0으로 한다. => 세션 전체 삭제!
        session.removeAttribute("loginUser"); // 세션 객체에 있는 로그인 유저만 삭제

        resp.sendRedirect(req.getContextPath()+"/"); // 로그아웃시 홈화면으로 이동
        // 톰캣 배포하면 자동으로 세션삭제

    }
}
