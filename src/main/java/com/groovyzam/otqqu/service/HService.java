package com.groovyzam.otqqu.service;

import com.groovyzam.otqqu.dao.HDAO;
import com.groovyzam.otqqu.dto.HDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Service
public class HService {

    private ModelAndView mav = new ModelAndView();

    @Autowired
    private HDAO hdao;

    @Autowired
    private PasswordEncoder pwEnc;

    @Autowired
    private HttpSession session;

    @Autowired
    private JavaMailSender mailSender;


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

}
