package com.groovyzam.otqqu.dao;

import com.groovyzam.otqqu.dto.HDTO;
import com.groovyzam.otqqu.dto.PDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HDAO {
    // 회원목록
    List<HDTO> hList();

    // 로그인
    HDTO hLogin(HDTO human);

    // 회원가입
    int hJoin(HDTO human);

    // 아이디 중복 검사
    String idOverlap(String hid);

    // 내 정보보기
    HDTO hView(String hid);

    // 프로필 업데이트
    int hFileupload(HDTO human);

    // 기본프로필로 이동
    int uPdelete(HDTO human);

    // 내 게시글 보기
    List<PDTO> pMylist(String Hid);
}
