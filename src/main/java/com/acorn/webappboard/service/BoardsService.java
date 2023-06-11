package com.acorn.webappboard.service;

import com.acorn.webappboard.dto.BoardsDto;

import java.util.List;

public interface BoardsService { // 게시글 서비스
    // 서비스 기본 : CRUD (생성,삭제,업데이트,상세)
    //🍎서비스 기획이 중요하다 // 다른 웹앱을 분석하면서 보는게 좋다
    // AI 시대.. 기획이 중요하다. 기획을 하면 코드는 ai 가 만들어준다.. 물론 코드도 구현할 수 있어야 한다!
    List<BoardsDto> list(); // 리스트에 필요한 것들 : paging(페이징) + search(검색)
    public BoardsDto detail(int bId); // 상세보기 + 이미지리스트 + 조회수 + 댓글리스트(댓글,대댓글,좋아요) + 좋아요
    public int modify(BoardsDto board); // 이미지 리스트 수정(등록,삭제), 게시글 PRIVATE(비공개) 로 상태 수정
    public int register(BoardsDto board); // 이미지 리스트 등록
    public int remove(int bId); // 이미지 리스트가 참조하는 서버(db)에 저장된 이미지들 삭제


}
