package com.groovyzam.otqqu.controller;

import com.groovyzam.otqqu.dao.HDAO;
import com.groovyzam.otqqu.dto.HDTO;
import com.groovyzam.otqqu.service.HService;


import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import java.util.HashMap;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class HController {

    @Autowired
    private HService hsvc;

    @Autowired
    private HttpSession session;

    @Autowired
    private HDAO hdao;

    private ModelAndView mav = new ModelAndView();

    // Main : 메인페이지
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Main() {


        if (session.getAttribute("loginId") == null) {
            return "Login";
        }

        return "redirect:/mainPost";
    }

    @RequestMapping(value = "Main", method = RequestMethod.GET)
    public String MainPage() {


        if (session.getAttribute("loginId") == null) {
            return "Login";
        }

        return "redirect:/mainPost";
    }

    // hjoinForm : 회원가입 페이지로 이동
    @RequestMapping(value = "hjoinForm", method = RequestMethod.GET)
    public String hjoinForm() {
        return "Join";
    }

    // hjoinForm : 회원가입 페이지로 이동
    @RequestMapping(value="HpwModifyForm", method = RequestMethod.GET)
    public String  HpwModifyForm(){
        return "HpwModify";
    }

    // hJoin : 회원가입
    @RequestMapping(value = "hJoin", method = RequestMethod.POST)
    public String hJoin(@Valid HDTO human, BindingResult br, Model model) {
        if(br.hasErrors()){
            model.addAttribute("human",human);
            Map<String,String> validatorResult = hsvc.validateHandling(br);
            for(String key : validatorResult.keySet()){
                model.addAttribute(key,validatorResult.get(key));
            }
            return "/Join";
        }
        mav = hsvc.hJoin(human);

        return "redirect:hLoginForm";
    }

    // A_idOverlap : 아이디 중복검사
    @RequestMapping(value = "A_idOverlap", method = RequestMethod.POST)
    public @ResponseBody
    String idOverlap(@RequestParam("Hid") String Hid) {
        // JSON(Ajax)을 이용할 떄 추가
        String result = hsvc.idOverlap(Hid);

        return result;
    }

    // hLoginForm : 로그인 페이지로 이동
    @RequestMapping(value = "hLoginForm", method = RequestMethod.GET)
    public String Hlogin() {

        return "Login";

    }

    // hLogin : 로그인
    @RequestMapping(value = "hLogin", method = RequestMethod.POST)
    public ModelAndView mLogin(@ModelAttribute HDTO human){

            mav = hsvc.hLogin(human);
            return mav;

    }

    // hLogout : 로그아웃
    @RequestMapping(value = "hLogout", method = RequestMethod.GET)
    public String hLogout() {

        session.invalidate();
        return "redirect:/";
    }


    // hList : 관리자용 회원목록
    @RequestMapping(value = "hList", method = RequestMethod.GET)
    public ModelAndView hList() {

        mav = hsvc.hList();

        return mav;
    }

    // hView : 내 정보보기(회원)
    @RequestMapping(value = "hView", method = RequestMethod.GET)
    public ModelAndView hView(@RequestParam("Hid") String Hid) throws UnsupportedEncodingException {


        System.out.println("한글깨짐3"+Hid);
        mav = hsvc.hView(Hid);


        return mav;
    }

    // uPloadFile : 내 정보보기에서 프로필 수정
    @RequestMapping(value = "uPloadFile", method = RequestMethod.POST)
    public ModelAndView uPloadFile(@ModelAttribute HDTO human) throws IOException {

        mav = hsvc.uploadFilea(human);

        return mav;

    }

    // uPdelete : 기본프로필로 변경
    @RequestMapping(value = "uPdelete", method = RequestMethod.POST)
    public ModelAndView uPdelete(@ModelAttribute HDTO human) throws IOException {

        mav = hsvc.uPdelete(human);


        return mav;
    }

    // Search : 검색어 입력
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public @ResponseBody List<HDTO> Search(@RequestParam(value="keyword") String keyword) {

        List<HDTO> searchList = hsvc.search(keyword);

        return searchList;
    }

    // 자동완성 검색
    @RequestMapping(value = "autocomplete", method = RequestMethod.POST)
    public void aSearch(Locale locale, Model model, HttpServletRequest request,
                        HttpServletResponse resp, HDTO human) throws IOException {
        String result = request.getParameter("term");

        List<HDTO> list = hdao.autocomplete(result);

        JSONArray ja = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            ja.put(list.get(i).getHid());
        }

        PrintWriter out = resp.getWriter();

        out.print(ja.toString());

    }
    //hFollow 회원 팔로우
    @RequestMapping(value = "hFollow", method = RequestMethod.GET)
    public ModelAndView hFollow(@RequestParam ("Hid") String Hid){

        System.out.println("한글깨짐2+"+Hid);
        String sessionId= (String) session.getAttribute("loginId");

        mav=hsvc.hFollow(Hid,sessionId);


        return mav;
    }
    //hUnFollow 회원 언팔로우
    @RequestMapping(value = "hUnFollow", method = RequestMethod.GET)
    public ModelAndView hUnFollow(@RequestParam ("Hid") String Hid){

        System.out.println("한글깨짐2"+Hid);
        String sessionId= (String) session.getAttribute("loginId");

        mav=hsvc.hUnFollow(Hid,sessionId);


        return mav;
    }

    //hModifyForm 회원 수정 기본 데이터불러오기
    @RequestMapping(value = "hModifyForm", method = RequestMethod.GET)
    public ModelAndView hModifyForm(@RequestParam ("Hid") String Hid){


        mav=hsvc.hModifyForm(Hid);


        return mav;
    }

    //hModify 회원 수정
    @RequestMapping(value = "hModify", method = RequestMethod.POST)
    public ModelAndView hModify(@ModelAttribute HDTO hdto){

        mav=hsvc.hModify(hdto);

        return mav;
    }

    // A_pwOverlap : 비밀번호 일치 검사
    @RequestMapping(value="/A_pwOverlap", method= RequestMethod.POST)
    public @ResponseBody
    String pwOverlap(@RequestParam("Hpw") String Hpw) {
        // JSON(Ajax)을 이용할 떄 추가
        String result = hsvc.pwOverlap(Hpw);

        return result;
    }
    @RequestMapping(value = "HpwModify" , method = RequestMethod.POST)
    public ModelAndView HpwModify(@ModelAttribute HDTO hdto){

        mav = hsvc.HpwModify(hdto);
        return mav;
    }


    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public  @ResponseBody String sendSms(HttpServletRequest request) throws Exception {

        String api_key = "NCS0IMJVIMR6GLKZ";
        String api_secret = "U3HT9Q0PWGSICBP2AZSZAVEKXSWO3NJK";

        Message coolsms = new Message(api_key, api_secret);

        HashMap<String, String> set = new HashMap<String, String>();

        set.put("to", (String)request.getParameter("Hphone")); // 받는 사람
        set.put("from", "01083668581"); // 발신번호
        set.put("text", "안녕하세요 인증번호는 [" + (String) request.getParameter("text") + "]입니다"); // 문자내용
        set.put("type", "sms"); // 문자 타입
        set.put("app_version","test app 1.2");

        try {
            JSONObject object = coolsms.send(set);
        } catch (CoolsmsException e) {
            e.printStackTrace();
        }
        return "ok";
    }


    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public @ResponseBody String emailConfirm(@RequestParam("Hphone") String Hphone, HttpServletRequest request) throws Exception{

        String api_key = "NCS0IMJVIMR6GLKZ";
        String api_secret = "U3HT9Q0PWGSICBP2AZSZAVEKXSWO3NJK";
        Message coolsms = new Message(api_key,api_secret);

        HashMap<String,String> params = new HashMap<String,String>();
        params.put("to",Hphone);
        params.put("from","01083668581");
        params.put("type", "SMS");
        params.put("text", "안녕하세요 인증번호는 [" + (String) request.getParameter("text") + "]입니다");
        params.put("app_version","test app 1.2");

        try {
            JSONObject object = coolsms.send(params);

        } catch (CoolsmsException e) {
            e.printStackTrace();
        }

        return "ok";

    }

    @RequestMapping(value = "mainProfile", method = RequestMethod.POST)
    public ModelAndView mainProfile(@RequestParam String HID){

        mav=hsvc.mainProfile(HID);

        return mav;
    }
    @RequestMapping(value = "searchInfo" ,method = RequestMethod.GET)
    public ModelAndView searchInfo(@RequestParam("keyword") String Hid){

        mav= hsvc.searchInfo(Hid);
        return mav;

    }


}

