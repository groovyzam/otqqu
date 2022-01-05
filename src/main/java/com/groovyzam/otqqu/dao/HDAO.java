package com.groovyzam.otqqu.dao;

import com.groovyzam.otqqu.dto.HDTO;
import com.groovyzam.otqqu.dto.PDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.servlet.ModelAndView;

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

    List<HDTO> Search(String keyword);

    List<HDTO> autocomplete(String result);

    //팔로우
    int hFollow(String Hid, String sessionId);

    //팔로우 취소
    int hUnFollow(String Hid, String sessionId);

    //팔로우 여부 확인
    String followList(String sessionId, String Hid);

    //팔로잉 회원
    List<PDTO> following(String Hid);

    //팔로워 회원
    List<PDTO> follower(String Hid);

    //회원 정보 수정 페이지에 기본 정보
    HDTO hModifyForm(String hid);

    //회원 정보 수정
    int hModify(HDTO hdto);

    int HpwModify(String sessionId, String hpw);
}
