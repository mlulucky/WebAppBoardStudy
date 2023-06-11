package com.acorn.webappboard.controller;
import com.acorn.webappboard.dto.UsersDto;
import com.acorn.webappboard.service.UsersService;
import com.acorn.webappboard.service.UsersServiceImp;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/uIdCheck.do")
public class UsersCheckIdController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // JSON 문자열 // JSON 은 오브젝트가 아니라 오브젝트를 문자열로 표현한 것
        // JSON 문자열을 JSONObject로 파싱하기
        resp.setContentType("application/json;charset=UTF-8");
        String uId=req.getParameter("u_id");
        boolean checkId = false;
        UsersDto user = null;
        try {
            UsersService usersService = new UsersServiceImp();
            user = usersService.idCheck(uId);
            if(!(user == null)) {
                checkId = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("checkId", checkId);
        jsonObject.put("user", user);

        PrintWriter out = resp.getWriter();
        out.print(jsonObject);

    }
}
