package com.groovyzam.otqqu.controller;


import com.groovyzam.otqqu.dto.HDTO;
import com.groovyzam.otqqu.service.HService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
public class HController {


    @Autowired
    private HService hsvc;

    @Autowired
    private HttpSession session;


    private ModelAndView mav = new ModelAndView();

    // 처음화면 실행
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String main(){
        return "Main";
    }

    // 메인화면
    @RequestMapping(value="/Main", method= RequestMethod.GET)
    public String main1(){
        return "Main";
    }

    // hjoinForm : 회원가입 페이지로 이동
    @RequestMapping(value="/hJoinForm", method = RequestMethod.GET)
    public String hjoinForm(){
        return "Join";
    }

    // hLoginForm : 회원가입 페이지로 이동
    @RequestMapping(value="/hLoginForm", method = RequestMethod.GET)
    public String hLoginForm(){
        return "Login";
    }

    // hJoin 회원가입
    @RequestMapping(value="/hJoin", method = RequestMethod.POST)
    public ModelAndView hJoin(@ModelAttribute HDTO human){

        mav = hsvc.hJoin(human);

        return mav;
    }

    // A_idOverlap : 아이디 중복검사
    @RequestMapping(value="/A_idOverlap", method= RequestMethod.POST)
    public @ResponseBody String idOverlap(@RequestParam("Hid") String Hid) {
        // JSON(Ajax)을 이용할 떄 추가
        String result = hsvc.idOverlap(Hid);

        return result;
    }

    // hList : 관리자용 회원목록
    @RequestMapping(value = "hList", method = RequestMethod.GET)
    public ModelAndView hList() {

        return mav;
    }

}

