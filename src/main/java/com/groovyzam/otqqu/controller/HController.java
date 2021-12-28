package com.groovyzam.otqqu.controller;

import com.groovyzam.otqqu.dto.HDTO;
import com.groovyzam.otqqu.service.HService;
import com.groovyzam.otqqu.service.PService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpSession;

@Controller
public class HController {

    @Autowired
    private HService hsvc;

    @Autowired
    private PService psvc;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();

    // Main : 메인페이지
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Main() {


        if(session.getAttribute("loginId") == null){
            return "Login";
        }

        return "Main";
    }

    // hjoinForm : 회원가입 페이지로 이동
    @RequestMapping(value="/hjoinForm", method = RequestMethod.GET)
    public String hjoinForm(){
        return "Join";
    }



    // hJoin : 회원가입
    @RequestMapping(value="/hJoin", method = RequestMethod.POST)
    public ModelAndView hJoin(@ModelAttribute HDTO human){

        mav = hsvc.hJoin(human);

        return mav;
    }

    // A_idOverlap : 아이디 중복검사
    @RequestMapping(value="/A_idOverlap", method= RequestMethod.POST)
    public @ResponseBody
    String idOverlap(@RequestParam("Hid") String Hid) {
        // JSON(Ajax)을 이용할 떄 추가
        String result = hsvc.idOverlap(Hid);

        return result;
    }

    // Hlogin : 로그인 페이지로 이동
    @RequestMapping(value = "/hLoginForm", method = RequestMethod.GET)
    public String Hlogin() {

        return "Login";

    }

    // hLogin : 로그인
    @RequestMapping(value = "/hLogin", method = RequestMethod.POST)
    public ModelAndView mLogin(@ModelAttribute HDTO human) {


        mav = hsvc.hLogin(human);

        return mav;
    }
    // hLogout : 로그아웃
    @RequestMapping(value="hLogout", method = RequestMethod.GET)
    public String hLogout(){

        session.invalidate();
        return "Main";
    }


    // hList : 관리자용 회원목록
    @RequestMapping(value = "/hList", method = RequestMethod.GET)
    public ModelAndView hList() {

        mav = hsvc.hList();

        return mav;
    }


}
