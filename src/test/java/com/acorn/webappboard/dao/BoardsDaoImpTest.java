package com.acorn.webappboard.dao;

import com.acorn.webappboard.WebAppBoardConn;
import com.acorn.webappboard.dto.BoardsDto;
import com.acorn.webappboard.vo.PageVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoardsDaoImpTest {
    // 단위 테스트 중요!!
    // 전체를 테스트하는 경우는 실패하는 테스트가 생긴다
    // 🍎테스트는 모두 멀티스레드로 동작!
    // 각 테스트에서 해당되는 값들이 있을지 모른다.
    // 해결방법 => BeforeAll 테스트에서 미리 테스트할 내역을 등록한다!(수정 및 삭제할 레코드)
    private static BoardsDao boardsDao;
    private static int testPk; // init 함수에서 테스트 등록에 사용
    @BeforeAll  // 테스트 맨 처음 가장 처음에 실행되는 것
    static void init() throws Exception {
        Connection conn= WebAppBoardConn.getConn(); // 커넥션 객체
        boardsDao=new BoardsDaoImp(conn); // 생성자의 매개변수에 커넥션 객체전달
        // 🍎미리 테스트할 내역을 등록(수정 및 삭제할 레코드)
        // save 테스트의 코드
        BoardsDto board=new BoardsDto();
        board.setUId("admin"); // 🍎있는 유저 아이디(외래키 - 유저테이블의 유저아이디를 참조)만 게시글 등록가능하다!! // bid 게시글 번호는 자동생성이라 등록하지 않는다.
        board.setTitle("BoardDaoImp 테스트");
        board.setContent("안녕하세요 테스트글입니다.");
        boardsDao.save(board);
        // 자바의 jdbc 는 auto increment 했을 때 등록된 번호를 반환하지 않는다.
        //=> mysql 에 auto Increment pk 등록된 경우 : SELECT LAST_INSERT_ID() 라는 함수가 있다.(마지막에 등록된 번호를 불러온다.)
        // mysql(Auto Increment pk) : SELECT LAST_INSERT_ID()
        String sql="SELECT LAST_INSERT_ID() pk"; // 결과는 하나만 나온다.
        PreparedStatement pstmt=conn.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery(); //
        if(rs.next()){ // 결과가 하나라서 if문
            testPk=rs.getInt("pk");
            //🍎이 값으로 수정삭제를 한다 => updateByPk, deleteByPk 에서 사용
        }

    }

    @Test
    void findBySearchAndPaging() {
        Map<String,String> params = new HashMap<>();
        params.put("field","status");
        params.put("value","public");
//        PageVo pageVo=new PageVo(1,10,params); // 🔥뭘까.. reqQuey 문자열? 맵? 어떻게..?
    }

    @Test
    void findAll() throws Exception {
        List<BoardsDto> boards=boardsDao.findAll();
        System.out.println("boards = " + boards); // sout + v 단축키 // 콘솔출력
        Assertions.assertNotNull(boards); // null 인지 아닌지(데이터가 있는지) 검사
        Assertions.assertEquals(boards.size()>0,true); // 결과가 있는지
    }

    @Test
    void findByBId() throws Exception {
        BoardsDto board=boardsDao.findByBId(10);
        System.out.println("board = " + board);
        Assertions.assertNotNull(board);
        // BoardsDto(bId=10, uId=user10, postTime=2023-03-13, updateTime=2023-03-13, status=PUBLIC, title=열 번째 글입니다., content=안녕하세요. 열 번째 글입니다., viewCount=0)
    }

    @Test
    void save() throws Exception {
        BoardsDto board=new BoardsDto();
        board.setUId("user07"); // 🍎있는 유저 아이디(외래키 - 유저테이블의 유저아이디를 참조)만 게시글 등록가능하다!! // bid 게시글 번호는 자동생성이라 등록하지 않는다.
//        board.setStatus("PRIVATE"); // 등록시 상태는 기본 PUBLIC 으로 지정했어서 등록안해도 된다.
        board.setTitle("테스트 글입니다(테스트)");
        board.setContent("안녕하세요 테스트글입니다.");
        int save=boardsDao.save(board);
        Assertions.assertEquals(save,1); // save 의 결과가 1과 같은지 검사
    }

    @Test
    void updateByPk() throws Exception {
        BoardsDto board=new BoardsDto();
        board.setBId(testPk); // boards 테이블의 pk b_id(게시글번호) 로 해당 게시글 수정
//        board.setBId(30); // boards 테이블의 pk b_id(게시글번호) 로 해당 게시글 수정
//        board.setUId("user10"); // 게시글 수정은 있는 유저로 가능
        board.setStatus("REPORT");
        board.setTitle("게시글 업데이트 수정 테스트");
        board.setContent("30 게시글 수정 테스트내용");
        int update=boardsDao.updateByPk(board);
        Assertions.assertEquals(update,1);
//        System.out.println(update);
//        BoardsDto user10=boardsDao.findByBId(6);
//        System.out.println(user10);
    }

    @Test
    void deleteByPk() throws Exception {
        int delete=boardsDao.deleteByPk(testPk);
//        int delete=boardsDao.deleteByPk(30);
        Assertions.assertEquals(delete,1); // 1 은 삭제(delete) 성공한 개수
    }
}