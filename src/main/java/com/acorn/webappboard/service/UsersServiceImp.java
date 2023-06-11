package com.acorn.webappboard.service;

import com.acorn.webappboard.WebAppBoardConn;
import com.acorn.webappboard.dao.UsersDao;
import com.acorn.webappboard.dao.UsersDaoImp;
import com.acorn.webappboard.dto.UsersDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsersServiceImp implements UsersService {
    private Connection conn;
    private UsersDao usersDao; // 🍎서비스 imp 을 생성할때, UserDao 객체를 생성한다.
    // 트랜잭션 : 친구한테 계좌송금(서비스) - 내 계좌 업데이트(4000->3000), 은행기록등록(실패), 친구계좌 업데이트(dao)
    // 내가 송금했던 내역을 취소해야 한다 => 롤백!
    public UsersServiceImp() throws Exception { // 🍎생성자 꼭 초기화를 해야한다.
        // 여기서 usersDao 를 만들꺼라 매개변수에 안담는다.
        conn= WebAppBoardConn.getConn();
        this.usersDao = new UsersDaoImp(conn);
    }

    @Override
    public List<UsersDto> list() throws Exception {
        return usersDao.findAll();
    }

    @Override
    public UsersDto login(String uId, String pw) throws Exception {
        return usersDao.findByUIdAndPw(uId,pw);
    }

    @Override
    public UsersDto detail(String uId) throws Exception {
        return usersDao.findByUId(uId);
    }

    @Override
    public UsersDto idCheck(String uId) throws Exception {
        return usersDao.findByUId(uId);
    }

    @Override
    public int modify(UsersDto user) throws Exception {
        int modify=0;
        modify=usersDao.updateByPk(user);
        return modify;
//        🍎트랜잭션 사용법! (서비스 개념을 이해시키기 위해 미리 선행해본 것)
//        conn.commit(); // 롤백지점!! 취소하는 지점
//        int modify=0;
//        try{ // 서비스에 오류가 떠야지 서비스 취소를 하니까 try catch 로 서비스 코드 오류처리
//            modify=usersDao.updateByPk(user); // 서비스들 ..
//            // ....
//            // ....(X) // 잘못처리된 서비스
//            // ....
//        }catch (Exception e){
//            conn.rollback(); // try 에서 오류가 떳따는건 이중에 실패했다는게 있다 => 롤백지점으로 디비등록내용 되돌리기 취소하기
//        }
//        return modify;
    }

    @Override
    public int register(UsersDto user) throws Exception {
        return usersDao.save(user);
    }

    @Override
    public int closeAccount(UsersDto user) throws Exception {
        return usersDao.updatePermissionByUIdAndPw(user);
    }

    @Override
    public int dropOut(String uId, String pw) throws Exception { // 계정삭제(탈퇴)
        return usersDao.deleteByUIdAndPw(uId,pw);
    }
//    public int dropOut(String uId) throws Exception { // 계정삭제(탈퇴)
//        return usersDao.deleteByUIdAndPw(uId);
//    }

}
