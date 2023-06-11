package com.acorn.webappboard.dao;

import com.acorn.webappboard.dto.BoardsDto;
import com.acorn.webappboard.vo.PageVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardsDaoImp implements BoardsDao { // implement 인터페이스 구현
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    public BoardsDaoImp(Connection conn) {
        this.conn=conn;
    } // 다오를  커넥션을 하는 곳에는 반드시 커넥션 객체를 보내야 한다.


    @Override
    public List<BoardsDto> findBySearchAndPaging(PageVo pageVo) throws Exception {
        List<BoardsDto> boardsList=new ArrayList<>();
        String sql="SELECT * FROM boards";
        if(pageVo.getSearchField()!=null && pageVo.getSearchValue()!=null){
            sql+=" WHERE" + pageVo.getSearchField() + " LIKE '%" + pageVo.getSearchValue() + "%'";
        }
        if(pageVo.getOrderField()!=null){
            sql+=" ORDER BY " + pageVo.getOrderField();
        }else if(pageVo.getOrderField()==null) {
            sql+=" ORDER BY " + "post_time";
        }
        sql+=" LIMIT ?,?";

        pstmt=conn.prepareStatement(sql);
        pstmt.setInt(1,pageVo.getOffset());
        pstmt.setInt(2,pageVo.getRowLength());
        rs=pstmt.executeQuery();
        while(rs.next()){
            BoardsDto board=new BoardsDto();
            board.setBId(rs.getInt("b_id"));
            board.setUId(rs.getString("u_id"));
            board.setPostTime(rs.getDate("post_time"));
            board.setUpdateTime(rs.getDate("update_time"));
            board.setStatus(rs.getString("status"));
            board.setTitle(rs.getString("title"));
            board.setContent(rs.getString("content"));
            board.setViewCount(rs.getInt("view_count"));
            // 자동생성되는 게시글번호, 날짜 등은 등록할때만 값을 저장하지 않는다.
            // 출력할때는 값을 넣어줘야 한다.
            boardsList.add(board);
        }
        return boardsList;
    }

    @Override
    public List<BoardsDto> findAll() throws Exception {
        List<BoardsDto> boards=null; // null 은 실패냐 성공이냐를 체크하는 용도
        String sql="SELECT * FROM boards";
        pstmt=conn.prepareStatement(sql); // 오류가 없다면 실행은 됬다는 것!
        rs=pstmt.executeQuery();  // 오류가 없다면 실행은 됬다는 것!
        boards=new ArrayList<>(); // db에서 쿼리를 성공적으로 가져왔을때 값을 담는다.
        while(rs.next()){ // 복수의 결과 // rs 한줄이 보드
            BoardsDto board=parseBoardsDto(rs);
            boards.add(board);
        }
        return boards;
    }

    @Override
    public BoardsDto findByBId(int bId) throws Exception { // 게시글(board) 찾기
        BoardsDto board=null; // null 은 데이터가 없는 것 // 값이 없을수도 있어서 null 초기화
        //🍎JPA(==Sequelize) : 아래 코드 전부 자동으로 생성해준다.
        String sql="SELECT * FROM boards WHERE b_id=?"; // 결과가 하나만 나오거나 없거나 // 왜? b_id 는 하나라서
        //🍎mybatis 를 쓰게되면 쿼리랑 dto 를 보고, 다음 아래 코드를 자동으로 생성해준다
        pstmt=conn.prepareStatement(sql);
        pstmt.setInt(1,bId);
        rs=pstmt.executeQuery();
        if(rs.next()){ // 배열에서 요소가 있으면 요소를 하나씩 빼오는 것
            board=parseBoardsDto(rs);
        }
        return board;
    }

    @Override
    public int save(BoardsDto board) throws Exception { // 등록
        int save=0; // 0 기본설정
        String sql="INSERT INTO boards (u_id, title, content) VALUE (?,?,?)"; // b_id 는 자동 등록
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,board.getUId());
        pstmt.setString(2,board.getTitle());
        pstmt.setString(3,board.getContent());
        save=pstmt.executeUpdate(); // 쿼리 등록 성공시 1(성공한 개수), 실패시 0
        return save;
    }

    // 생일이 데이트타입, 문자열타입 인 경우 문자열타입인 경우에는 나이를 구하는게 안되서 나이를 구하려면 다시 데이트타입으로 변환해야한다.
    @Override
    public int updateByPk(BoardsDto board) throws Exception {
        // 게시글 수정 => 게시글 board 가 매개변수로 필요
        int updateByPk=0; // 유저서비스 // update 성공한 수 / 성공시 1(업데이트 성공한 수) / 실패시 0(업데이트 성공한 수)
        String sql="UPDATE boards SET status=?,title=?,content=? WHERE b_id=?"; // 유저아이디 못바꾸게..
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,board.getStatus());
        pstmt.setString(2,board.getTitle());
        pstmt.setString(3,board.getContent());
        pstmt.setInt(4,board.getBId());
        updateByPk=pstmt.executeUpdate(); // executeUpdate dml 실행(성공했는가 결과)
        return updateByPk;
    }

    @Override
    public int deleteByPk(int bId) throws Exception {
        int deleteByPk=0; // int 타입 기본값은 0 // int delete=null (오류) null 은 객체에만 사용(자바에서 null은 객체가 없음을 뜻함)
        String sql="DELETE FROM boards WHERE b_id=?";
        pstmt=conn.prepareStatement(sql);
        pstmt.setInt(1,bId);
        deleteByPk=pstmt.executeUpdate();
//        이렇게 먼저 셋팅해 놓으면 sql 쿼리문 작성시 구문 자동선택? 미리보기? 가능해짐
//        String sql="";
//        pstmt=conn.prepareStatement(sql);
        return deleteByPk;
    }

    // 자주
    public BoardsDto parseBoardsDto(ResultSet rs) throws SQLException { // 파싱(형변환)하는 오류, 가져오는 오류가 있을수 있어서 .. 등등
        BoardsDto board=new BoardsDto(); // 한줄이 있다는 것은 객체가 있다는 것
        board.setBId(rs.getInt("b_id"));
        board.setUId(rs.getString("u_id"));
        board.setPostTime(rs.getDate("post_time"));
        board.setUpdateTime(rs.getDate("update_time"));
        board.setStatus(rs.getString("status"));
        board.setTitle(rs.getString("title"));
        board.setContent(rs.getString("content"));
        board.setViewCount(rs.getInt("view_count"));
        // 자동생성되는 게시글번호, 날짜 등은 등록할때만 값을 저장하지 않는다.
        // 출력할때는 값을 넣어줘야 한다.
        return board;
    }


}
