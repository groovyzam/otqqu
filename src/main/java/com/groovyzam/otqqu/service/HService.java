package com.groovyzam.otqqu.service;

import com.groovyzam.otqqu.dao.HDAO;
import com.groovyzam.otqqu.dto.HDTO;
import com.groovyzam.otqqu.dto.PDTO;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

        HDTO secu = hdao.hLogin(human);

        HDTO secu1 = hdao.hLogin(human);
        // pwEnc.matches() 타입은 boolean => true or false

        if (pwEnc.matches(human.getHpw(), secu.getHpw())) {

            mav.setViewName("Main");
            session.setAttribute("loginId", human.getHid());
        } else {
            System.out.println("비밀번호 불일치");
        }

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
    public ModelAndView hView(String Hid) {
        HDTO human = hdao.hView(Hid);
        List<PDTO> pmylist = hdao.pMylist(Hid);

        System.out.println("pmylist : " + pmylist);

        if (human != null) {

            mav.addObject("pmylist", pmylist);
            mav.addObject("member", human);
            mav.setViewName("Hview");
        } else {
            mav.setViewName("redirect:/hList");
        }


        return mav;
    }

    // 내 정보보기에서 프로필 수정
    public ModelAndView uploadFilea(HDTO human) throws IOException {

        MultipartFile HProfile = human.getHProfile();
        String originalFileName = HProfile.getOriginalFilename();

        String uuid = UUID.randomUUID().toString().substring(1, 7);

        String Hfile = uuid + "_" + originalFileName;

        System.out.println("Hfile" + Hfile);

        String savePath = "C:/Users/PC/SpringBoot/otqqu/src/main/resources/static/profile/" + Hfile;

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




        // 이미지 검색
        public ModelAndView PostProductImg (String PIMG){
            String URL = "https://www.google.com/search?q="+ PIMG +"&source=lnms&tbm=isch";

            Connection conn = Jsoup.connect(URL);

            try {
                Document html = conn.get();

                System.out.println("Attribute 탐색");
                Elements link = html.getElementsByTag("img");

                int i = 0, j = 0;

                for (Element e : link) {
                    if (e.attr("data-src") != "") {
                        System.out.println(e.attr("data-src"));
                        mav.addObject("Img" + j, e.attr("data-src"));
                        j++;
                    }
                    i++;
                    if (j == 3) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            mav.setViewName("img");

            return mav;
        }


}