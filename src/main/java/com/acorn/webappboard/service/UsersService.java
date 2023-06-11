package com.acorn.webappboard.service;

import com.acorn.webappboard.dto.UsersDto;

import java.util.List;

// 기획단계
// 기획=>와이어프레임(유즈케이스 ux ui)=>service 파일생성=>dao 파일생성
public interface UsersService { // 인터페이스
    // 인터페이스는 모두 public 이라서 안적어도 public 으로 설정
    // 메서드반환타입 메서드이름()
    // DTO 는 db 데이터의 정보를 맵핑 -> 컨트폴러와 뷰에 전달하는 역할

    // 로그인 시 SELECT 선택된 결과를 반환 => dto(db 정보) 타입 반환 메서드
    List<UsersDto> list() throws Exception;
    UsersDto login(String uId, String pw) throws Exception; // 로그인(유저아이디,비밀번호 필요 => 매개변수로 받는다)
    UsersDto detail(String uId) throws Exception; // 개인 상세정보 (유저아이디 필요)
    UsersDto idCheck(String uId) throws Exception; // 회원가입시 중복 아이디 체크 (유저아이디 필요)
    // *성공했냐 안했냐 개수 결과 반환 => int 타입 메서드
    int modify(UsersDto user) throws Exception; // 개인정보 수정 (유저객체(정보) 필요)
    // 수정하기 위해서는 유저정보가 필요하므로, 매개변수로 user 를 받는다.
    int register(UsersDto user) throws Exception; // 회원가입 (유저객체(정보) 필요)
    int closeAccount(UsersDto user) throws Exception; // 회원 비공개 permission="PRIVATE" (유저객체(정보) 필요)
//    int dropOut(String uId) throws Exception; // 탈퇴(삭제)(유저아이디,비밀번호 필요 => 매개변수로 받는다)
    int dropOut(String uId, String pw) throws Exception; // 탈퇴(삭제)(유저아이디,비밀번호 필요 => 매개변수로 받는다)

    // 🍎서비스 기획(페이지, 기능 기획)
    // 로그인, 상세정보 => dql(SELECT) 조회 서비스 => 조회결과를 반환 => 메서드 반환 타입 DTO(db 테이블의 정보)
    // 등록, 수정, 탈퇴 => dml(INSERT,UPDATE,DELETE) 업데이트 서비스 => 성공결과를 반환 => 메서드 반환타입 int

    // UsersDto 타입 메서드 => UsersDto(users db 테이블의 정보) 반환

}
