package com.acorn.webappboard.dao;

import com.acorn.webappboard.dto.BoardsDto;
import com.acorn.webappboard.vo.PageVo;

import java.util.List;

public interface BoardsDao {
    // Boardimp, imptest 구현 -> 페이징 작업

    List<BoardsDto> findAll() throws Exception;
    List<BoardsDto> findBySearchAndPaging(PageVo pageVo) throws Exception;


    BoardsDto findByBId(int bId) throws  Exception;
    int save(BoardsDto board) throws Exception;
    int updateByPk(BoardsDto board) throws Exception;
    int deleteByPk(int bId) throws  Exception;


}
