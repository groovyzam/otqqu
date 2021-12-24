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
    public ModelAndView hJoin(HDTO human) {

        human.setHpw(pwEnc.encode(human.getHpw()));


        int result = hdao.hJoin(human);

        if(result>0){
            //성공
            mav.setViewName("Login");
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
        HDTO secu1 = hdao.hLogin(human);

        // pwEnc.matches() 타입은 boolean => true or false
        if(pwEnc.matches(human.getHpw(), secu1.getHpw())){
            System.out.println("비밀번호 일치!");
            mav.setViewName("Main");




        } else {
            System.out.println("비밀번호 불일치");
            System.out.println(human.getHpw());
            System.out.println(secu1.getHpw());
            mav.setViewName("Main");
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
