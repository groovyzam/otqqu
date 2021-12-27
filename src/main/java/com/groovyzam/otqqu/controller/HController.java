package com.groovyzam.otqqu.controller;

import com.groovyzam.otqqu.dto.HDTO;
import com.groovyzam.otqqu.service.HService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class HController {

    @Autowired
    private HService hsvc;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();

    // Main : 메인페이지
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String Main() {

        return "Main";
    }

    // hjoinForm : 회원가입 페이지로 이동
    @RequestMapping(value="hjoinForm", method = RequestMethod.GET)
    public String hjoinForm(){
        return "Join";
    }


    // hJoin : 회원가입
    @RequestMapping(value="hJoin", method = RequestMethod.POST)
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
    @RequestMapping(value = "hLogin", method = RequestMethod.GET)
    public String Hlogin() {

        return "Login";

    }

    // hLogin : 로그인
    @RequestMapping(value = "hLogin", method = RequestMethod.POST)
    public ModelAndView mLogin(@ModelAttribute HDTO human) {


        mav = hsvc.hLogin(human);

        return mav;
    }

    // hList : 관리자용 회원목록
    @RequestMapping(value = "hList", method = RequestMethod.GET)
    public ModelAndView hList() {

        mav = hsvc.hList();

        return mav;
    }


    @RequestMapping(value = "img")
    public ModelAndView getImg(@RequestParam("PIMG") String PIMG){

        mav = hsvc.PostProductImg(PIMG);

        return mav;
    }
}
