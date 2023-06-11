package com.acorn.webappboard.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/model1/boardInsert.do") // 동적페이지 url 주소
public class Model1BoardInsert extends HttpServlet {
    @Override
    // 등록 form 태그 - input 태그 입력 // 웹페이지가 이동되면 시작되는게 doGET
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String html="<h1>게시글 등록폼</h1>";
        try{
            html+="<form method='POST' action='./boardInsert.do'>";
            html+="<p>아이디 : <input name='u_id'></p>";
            html+="<div>상태: <select name='status'><option>PUBLIC</option><option>PRIVATE</option><option>REPORT</option><option>BLOCK</option></select></div>";
            html+="<p>title: <input name='title'></p>";
            html+="<p>content : <textarea name='content'></textarea></p>";
            html+="<p><button type='reset'>초기화</button> <button>제출</button></p>";
            html+="</form>";
        }catch (Exception e){
            e.printStackTrace();
        }
        resp.getWriter().println(html);
    }
    // 등록 처리 - POST 방식 // 등록, 삭제하거나 입력하는 버튼을 눌렀을때 do post 가 실행 => url 쿼리스트링의 파라미터의 값을 가져와서 sql 문에
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // mysql db 접속
        String url="jdbc:mysql://localhost:3306/webAppBoard";
        String user="boardServerDev";
        String pw="mysql123";
        String driver="com.mysql.cj.jdbc.Driver";
        req.setCharacterEncoding("UTF-8");
        String uId=req.getParameter("u_id");
        String status=req.getParameter("status");
        String title=req.getParameter("title");
        String content=req.getParameter("content");
        String sql="INSERT INTO boards (u_id,status,title,content) VALUE (?,?,?,?)";
        Connection conn=null;
        PreparedStatement pstmt=null;
        int insert=0;
        try{
            Class.forName(driver); // 새로운 접속
            conn=DriverManager.getConnection(url,user,pw); // 새로운 쿼리실행
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,uId); // 쿼리문의 첫번째 물음표에 uId 대입
            pstmt.setString(2,status); // title 이 크기가 있어서 너무 길면 오류가 뜰수있다.
            pstmt.setString(3,title); // 쿼리문에 파라미터에서 받아온 값을 대입
            pstmt.setString(4,content);
            insert=pstmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(insert>0){
            resp.sendRedirect(req.getContextPath()+"/model1/boardList.do");
        }else{
            resp.sendRedirect(req.getContextPath()+"/model1/boardInsert.do");
        }

    }
}
