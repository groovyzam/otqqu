package com.groovyzam.otqqu.service;

import com.groovyzam.otqqu.dao.HDAO;
import com.groovyzam.otqqu.dto.HDTO;
import com.groovyzam.otqqu.dto.PDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;

@Service
public class HService {


    private ModelAndView mav = new ModelAndView();

    @Autowired
    private HDAO hdao;

    @Autowired
    private HttpSession session;

    @Autowired
    private PasswordEncoder pwEnc;

    @Autowired
    private JavaMailSender mailSender;


    // 회원가입
    public Map<String, String> validateHandling(BindingResult br) {
        Map<String,String> validatorResult = new HashMap<>();
        for(FieldError error : br.getFieldErrors()){
            String validKeyName = String.format("valid_%s",error.getField());
            validatorResult.put(validKeyName,error.getDefaultMessage());
        }
        return validatorResult;
    }

    public ModelAndView hJoin(HDTO human) {

        human.setHpw(pwEnc.encode(human.getHpw()));


        int result = hdao.hJoin(human);

        System.out.println(human.getHpw());
        if (result > 0) {
            //성공
            mav.setViewName("Login");
        } else {
            //실패
            mav.setViewName("Join");
        }
        return mav;
    }

    // ID 중복체크
    public String idOverlap(String Hid) {
        String idCheck = hdao.idOverlap(Hid);
        String result = null;

        if (idCheck == null) {
            result = "OK"; // 중복 x
        } else {
            result = "NO"; // 중복 o
        }

        return result;
    }

    // 로그인
    public ModelAndView hLogin(HDTO human) {

        HDTO secu1 = hdao.hLogin(human);

        // pwEnc.matches() 타입은 boolean => true or false
        if(pwEnc.matches(human.getHpw(),secu1.getHpw())){
            System.out.println("비밀번호 일치!");
            mav.setViewName("redirect:/mainPost");
            session.setAttribute("loginId", secu1.getHid());

        } else {
            System.out.println("비밀번호 불일치");
            mav.setViewName("Main");

        }

        // pwEnc.matches() 타입은 boolean => true or false


        return mav;
    }


    // 회원목록
    public ModelAndView hList() {


        List<HDTO> humanList = hdao.hList();

        mav.setViewName("Hlist");
        mav.addObject("humanList", humanList);

        return mav;
    }

    // 내 정보보기


    // 내 정보보기에서 프로필 수정
    public ModelAndView uploadFilea(HDTO human) throws IOException {

        MultipartFile HProfile = human.getHProfile();
        String originalFileName = HProfile.getOriginalFilename();

        String uuid = UUID.randomUUID().toString().substring(1, 7);

        String Hfile = uuid + "_" + originalFileName;

        System.out.println("Hfile" + Hfile);


        String savePath = "C:/Users/joype/Desktop/otqqu/src/main/resources/static/profile/" + Hfile;

        if (HProfile != null) {
            human.setHfile(Hfile);
            HProfile.transferTo(new File(savePath));
        } else {
            human.setHfile("default.png");
        }

        int result = hdao.hFileupload(human);

        HDTO human1 = hdao.hView(human.getHid());

        if (result > 0) {
            //성공

            mav.addObject("member", human1);
            mav.setViewName("redirect:/hView?Hid=" + human1.getHid());
        } else {
            //실패
            mav.setViewName("Main");
        }

        return mav;
    }

    // 기본프로필로 이동
    public ModelAndView uPdelete(HDTO human) {


        int result = hdao.uPdelete(human);


        HDTO human1 = hdao.hView(human.getHid());
        if (result > 0) {
            //성공
            mav.addObject("member", human1);
            mav.setViewName("redirect:/hView?Hid=" + human1.getHid());
        } else {
            //실패
            mav.setViewName("Main");
        }
        return mav;
    }

    // Search : 검색어 입력
    public List<HDTO> search(String keyword) {
        List<HDTO> hList=new ArrayList<HDTO>();
        if(keyword != "") {
           hList = hdao.Search(keyword);
            return hList;


        }
        return hList;

    }


    public ModelAndView hView(String Hid) {

        HDTO human = hdao.hView(Hid);

        List<PDTO> pmylist = hdao.pMylist(Hid);


        //팔로우 정보에 사용할 세션ID 불러오기
        String sessionId = (String) session.getAttribute("loginId");

        //팔로우 여부를 확인하기 위한 정보 가져오기
        String followList = hdao.followList(sessionId,Hid);

        // 팔로잉 회원 목록
        List<HDTO> following = hdao.following(Hid);

       //  팔로워 회원 목록
        List<HDTO> follower = hdao.follower(Hid);


        if (human != null) {
            mav.addObject("following", following);
            mav.addObject("follower", follower);

            mav.addObject("followList",followList);
            mav.addObject("pmylist", pmylist);
            mav.addObject("member", human);

            mav.setViewName("Hview");
        } else {
            mav.setViewName("redirect:/hList");
        }


        return mav;
    }

    public ModelAndView hFollow(String Hid, String sessionId) {

        //팔로우 목록에 추가
        int result=hdao.hFollow(Hid,sessionId);

        if(result>0){


            mav.setViewName("redirect:hView?Hid="+Hid+"");
        }else {
            mav.setViewName("Main");
        }
        return mav;

    }
    public ModelAndView hUnFollow(String Hid, String sessionId) {


        //팔로우 목록에서 제거
        int result=hdao.hUnFollow(Hid,sessionId);

        if(result>0){

            mav.setViewName("redirect:hView?Hid="+Hid+"");

        }else {

            mav.setViewName("Main");
        }
        return mav;

    }


    public ModelAndView hModifyForm(String hid) {

        HDTO hdto = hdao.hView(hid);

        mav.addObject("human",hdto);
        mav.setViewName("Hmodify");
         return mav;
    }

    public ModelAndView hModify(HDTO hdto) {

        int result = hdao.hModify(hdto);

        if(result>0){

            mav.setViewName("redirect:hView?Hid="+hdto.getHid()+"");
        }

        return mav;
    }

    public String pwOverlap(String hpw) {

        String sessionId= (String) session.getAttribute("loginId");
        HDTO human = hdao.hView(sessionId);

        String result = null;

        if(pwEnc.matches(hpw,human.getHpw())){
            result = "OK"; // 중복 x
        } else {
            result = "NO"; // 중복 o
        }

        return result;
    }

    public ModelAndView HpwModify(HDTO hdto) {

        String sessionId= (String) session.getAttribute("loginId");
        hdto.setHpw(pwEnc.encode(hdto.getHpw()));


        int result= hdao.HpwModify(sessionId,hdto.getHpw());

        if(result>0){
            mav.setViewName("redirect:/hLogout");
            System.out.println("비밀번호 변경");
        }else {
            mav.setViewName("Main");
        }

        return mav;
    }

    public ModelAndView mainProfile(String HID) {

        String profile = hdao.mainProfile(HID);

        System.out.println("프로필사진 : "+profile);

        if(profile != null) {
            mav.addObject("Hfile", profile);
        }

        mav.setViewName("mainPost");
        return mav;
    }

    public ModelAndView searchInfo(String hid) {

        mav.setViewName("redirect:hView?Hid="+hid+"");
        return mav;
    }
}




