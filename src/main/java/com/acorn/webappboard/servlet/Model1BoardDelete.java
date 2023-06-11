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

@WebServlet("/model1/boardDelete.do") // url 동적리소스 주기
public class Model1BoardDelete extends HttpServlet { // 동적페이지 - HttpServlet
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bId_str=req.getParameter("b_id"); // 파라미터 값 구하기
//        resp.getWriter().println(bId_str); // 화면출력 테스트
        String url="jdbc:mysql://localhost:3306/webAppBoard"; // 접속 url (만든 웹앱모듈 webAppBoard)
        String user="boardServerDev"; // db 접속계정 아이디
        String pw="mysql123"; // db 접속 비밀번호
        String driver="com.mysql.cj.jdbc.Driver"; // jdbc 가 db 에 접속할 때 필요한 driver - 다운로드 받거나 maven(pom.xml)에서 의존성주입 => mysql Connector 추가
        Connection conn=null;
        PreparedStatement pstmt=null; //
        //delete,insert,update 쿼리(sql) 은 == dml(data manafacture language) 은 결과가 정수로 반환 (몇개몇개 성공했어요~)
        int delete=0;
        try{
            int bId=Integer.parseInt(bId_str); // 이때 오류가 뜬다는 것은 잘못된 요청
            String sql="DELETE FROM boards WHERE b_id=?"; // sql 문은 preparedStatement 에 들어가야지 쿼리인지 인지한다!!
            // String sql="DELETE FROM boards WHERE b_id=? and= u_id=?"; // sql 문은 preparedStatement 에 들어가야지 쿼리인지 인지한다!!
            Class.forName(driver); // 드라이버 불러오기
            conn= DriverManager.getConnection(url,user,pw); // db 접속
            pstmt=conn.prepareStatement(sql); // db 접속하여 쿼리 연결
            pstmt.setInt(1,bId); // sql 쿼리문 첫번째 물음표에 bId 를 넣는다
            // pstmt.setString(2,"user06"); // sql 쿼리문 첫번째 물음표에 bId 를 넣는다
            delete=pstmt.executeUpdate(); // dml 실행 // 실행된 결과 개수가 나온다. 예를들어 게시물을 삭제하는 경우 게시물이 6개이면
        }catch (NumberFormatException e){
            e.printStackTrace();
            resp.sendError(400,"잘못된 요청입니다."); // 파라미터가 있어야하는데 없는 경우 400 에러
        }catch (Exception e){
            e.printStackTrace();
        }
//        String msg=(delete>0)?"성공":"실패";
//        resp.getWriter().println("삭제: "+msg); // 화면출력
//        http://localhost:8080/WebAppBoard/model1/boardList.do
        // "/model1/boardList.do" => http://localhost:8080/WebAppBoard/model1/boardList.do
        String contextPath=req.getContextPath(); // == /WebAppBoard (컨텍스트패스)
        if(delete>0){ // 삭제 성공시
            resp.sendRedirect(contextPath+"/model1/boardList.do"); // 페이지 이동
        }else{
            resp.sendRedirect(contextPath+"/model1/boardUpdate.do?b_id="+bId_str);
        }


    }
}
