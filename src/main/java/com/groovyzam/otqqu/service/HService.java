package com.groovyzam.otqqu.service;

import com.groovyzam.otqqu.dao.HDAO;
import com.groovyzam.otqqu.dto.HDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class HService {

    @Autowired
    private HDAO hdao;

    @Autowired
    private HttpSession session;

    @Autowired
    private PasswordEncoder pwEnc;

    @Autowired
    private JavaMailSender mailSender;

    private ModelAndView mav = new ModelAndView();


    // 회원가입
    public ModelAndView hJoin(HDTO human) {
        System.out.println("암호화전 비밀번호 : " + human.getHpw());

        human.setHpw(pwEnc.encode(human.getHpw()));

        System.out.println("암호화 후 비밀번호 : " + human.getHpw());

        int result = hdao.hJoin(human);

        if(result>0){
            //성공
            mav.setViewName("Main");
        }else{
            //실패
            mav.setViewName("Join");
        }

        return mav;
    }

    // ID 중복체크
    public String idOverlap(String Hid) {
        String idCheck = hdao.idOverlap(Hid);
        String result = null;

        if(idCheck == null) {
            result = "OK"; // 중복 x
        }else {
            result = "NO"; // 중복 o
        }

        return result;
    }

    // 로그인
    public ModelAndView hLogin(HDTO human) {
        String loginId = hdao.hLogin(human);

        if(loginId!=null) {
            // 성공
            session.setAttribute("loginId",loginId);
            mav.setViewName("Main");
        } else {
            // 실패
            mav.setViewName("Login");
        }

        return mav;
    }


    // 회원목록
    public ModelAndView hList() {


        List<HDTO> humanList = hdao.hList();

        mav.setViewName("Hlist");
        mav.addObject("humanList",humanList);

        return mav;
    }



}
