package com.acorn.webappboard.dao;

import com.acorn.webappboard.dto.UsersDto;
import java.sql.*; // java sql 패키지 전체
import java.util.ArrayList;
import java.util.List;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;

public class UsersDaoImp implements UsersDao { // 유저다오 임플먼트
    // UsersDao 인터페이스 추상메서드를 구현한다

    private Connection conn; // db 접속 객체
    private PreparedStatement pstmt;
    private ResultSet rs; // java.sql 의 resultset

    public UsersDaoImp(Connection conn){
        // * 생성자를 구현할때 커넥션 객체를 가져와서 디비에 접속한다
        this.conn=conn;
    }

    @Override
    public List<UsersDto> findAll() throws Exception {
        List<UsersDto> users=null;
        String sql="SELECT * FROM users";
        pstmt=conn.prepareStatement(sql);
        rs=pstmt.executeQuery();
        users=new ArrayList<>();
        while(rs.next()){
            UsersDto user=parseUserDto(rs);
            users.add(user);
        }
        return users;
    }

    @Override
    public UsersDto findByUIdAndPw(String uId, String pw) throws Exception {
        UsersDto user=null;
        String sql="SELECT * FROM users WHERE u_id=? AND pw=?";
        pstmt=conn.prepareStatement(sql);
        // 맵핑
        pstmt.setString(1,uId); // sql 쿼리문 첫번째 물음표에 uId 대입
        pstmt.setString(2,pw);  // sql 쿼리문 두번째 물음표에 pw 대입
        rs=pstmt.executeQuery(); // rs : sql 쿼리문 셀렉트 결과
        if(rs.next()){ // [user1, user2, user3 ...] // 셀렉트의 결과가 있다면 // 하나가 있으면 넥스트가 있다. 없으면 null, // 결과가 하나이면 if 문. 복수이면 while 반복문으로 출력
            user=parseUserDto(rs);
        }
        return user;
    }

    @Override
    public UsersDto findByUId(String uId) throws Exception {
        UsersDto user=null;
        String sql="SELECT * FROM users WHERE u_id=?";
        pstmt= conn.prepareStatement(sql);
        pstmt.setString(1,uId);
        rs=pstmt.executeQuery();
        if(rs.next()){
            user=parseUserDto(rs);
        }
        return user;
    }

    @Override
    public int updateByPk(UsersDto user) throws Exception { // 예외 위임
        int update=0;
        String sql="UPDATE users set name=?,email=?,img_path=?,phone=?,pw=?,gender=?,address=?,detail_address=?,birth=? WHERE u_id=?";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,user.getName());
        pstmt.setString(2, user.getEmail());
        pstmt.setString(3, user.getImgPath());
        pstmt.setString(4, user.getPhone());
        pstmt.setString(5, user.getPw());
        pstmt.setString(6, user.getGender());
        pstmt.setString(7, user.getAddress());
        pstmt.setString(8, user.getDetailAddress());
        pstmt.setString(9, user.getBirth());
        pstmt.setString(10, user.getUId());
        update=pstmt.executeUpdate(); // 예외 위임했으니까 최종사용자가 이걸 쓸때 try catch 가 된다
        return update;
    }

    @Override
    public int save(UsersDto user) throws Exception {
        int save=0;
        String sql="INSERT INTO users (u_id, pw, name, phone, img_path, email, birth, gender, address, detail_address) VALUE (?,?,?,?,?,?,?,?,?,?)";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,user.getUId());
        pstmt.setString(2,user.getPw());
        pstmt.setString(3,user.getName());
        pstmt.setString(4,user.getPhone());
        pstmt.setString(5,user.getImgPath());
        pstmt.setString(6,user.getEmail());
        pstmt.setString(7,user.getBirth());
        pstmt.setString(8,user.getGender());
        pstmt.setString(9,user.getAddress());
        pstmt.setString(10,user.getDetailAddress());
        save=pstmt.executeUpdate();
        return save;
    }

    @Override
    public int updatePermissionByUIdAndPw(UsersDto user) throws Exception {
        int update;
        String sql="UPDATE users SET permission=? WHERE u_id=? AND pw=?";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,user.getPermission());
        pstmt.setString(2,user.getUId());
        pstmt.setString(3,user.getPw());
        update=pstmt.executeUpdate(); // executeUpdate dml - 몇개성공했어 결과 반환 ~  // executeQuery dql - resultset 결과 반환
        return update;
    }

    @Override
//    public int deleteByUIdAndPw(String uId) throws Exception {
//        int delete;
//        String sql="DELETE FROM users WHERE u_id=? "; // where and : 둘다 만족할때
//        pstmt=conn.prepareStatement(sql);
//        pstmt.setString(1,uId);
//        delete=pstmt.executeUpdate(); // dml - 몇개성공했는지 결과
//        return delete;
//    }
    public int deleteByUIdAndPw(String uId, String pw) throws Exception {
        int delete;
        String sql="DELETE FROM users WHERE u_id=? AND pw=?"; // where and : 둘다 만족할때
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1,uId);
        pstmt.setString(2,pw);
        delete=pstmt.executeUpdate(); // dml - 몇개성공했는지 결과
        return delete;
    }

    public UsersDto parseUserDto(ResultSet rs) throws SQLException { // UsersDto 를 반환
        UsersDto user;
        user=new UsersDto(); // 객체 생성
        user.setUId(rs.getString("u_id"));
        user.setPw(rs.getString("pw"));
        user.setAddress(rs.getString("address"));
        user.setDetailAddress(rs.getString("detail_address"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setGender(rs.getString("gender"));
        user.setPermission(rs.getString("permission"));
        user.setBirth(rs.getString("birth"));
        user.setName(rs.getString("name"));
        user.setImgPath(rs.getString("img_path"));
        user.setPostTime(rs.getDate("post_time"));


        return user; // 만들어진 유저의 객체를 파싱해서 넘긴다
    } // parseUserDto // parse : 파싱 // ResultSet 매개변수 -> ResultSet 결과를 파싱하겠다~

    // 자바에서 객체로 파싱한다는것
    // 데이터베이스의 테이블의 row, 데이터들을
    // 클래스의 필드 = 밸류 로 클래스 객체로 파싱한다는것!
    // 자바에서는 key : value 오브젝트로
}
