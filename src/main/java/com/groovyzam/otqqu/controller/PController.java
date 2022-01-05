package com.groovyzam.otqqu.controller;

import com.groovyzam.otqqu.dto.COMMENT;
import com.groovyzam.otqqu.dto.PDTO;
import com.groovyzam.otqqu.service.PService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PController {

    ModelAndView mav = new ModelAndView();

    @Autowired
    private PService psvc;

    @Autowired
    private HttpSession session;


    // PostForm : 게시글 등록 페이지로 이동
    @RequestMapping(value="/PostForm", method = RequestMethod.GET)
    public String PostForm(){


        return "Post";
    }

    //pUpload
    @ResponseBody
    @RequestMapping(value = "pUpload", method = RequestMethod.POST)

    public ModelAndView pUpload(@ModelAttribute PDTO pdto,
              @RequestParam(value = "Pcategory", required = true) List<String> Pcategory
            , @RequestParam(value = "Pbrand", required = true) List<String> Pbrand
            , @RequestParam(value = "PproductName", required = true) List<String> PproductName
            , @RequestParam(value = "Pprice", required = true) List<String> Pprice
            , @RequestParam(value = "PproductFile") List<MultipartFile> PproductFile
            , @RequestParam(value = "PproductFileImg") List<String> ProductFileImg

    ) throws IOException {
        pdto.setHid((String) session.getAttribute("loginId"));

        mav = psvc.pUpload(pdto, Pcategory, Pbrand, PproductName, Pprice, PproductFile);


        return mav;
    }
    @RequestMapping(value="/mainPost", method = RequestMethod.GET)
    public ModelAndView mainPost(){

        mav=psvc.mainPost();

        return mav;
    }
    @RequestMapping(value = "/ajaxPost", method = RequestMethod.POST)
    public @ResponseBody List<PDTO> ajaxPost(@RequestParam ("page") int page) {

        List<PDTO> list = psvc.ajaxPost(page);
        return list;
        // pView : 게시글 정보보기
    }



    // pView : 게시글 정보보기
    @RequestMapping(value = "pView", method = RequestMethod.GET)
    public ModelAndView pView(@RequestParam("Pnum") int Pnum){


        mav = psvc.pView(Pnum);
        return mav;
    }


    List<COMMENT> commentList = new ArrayList<COMMENT>();

    // C_list : 댓글 리스트불러오기
    @RequestMapping(value = "C_list", method = RequestMethod.POST)
    public @ResponseBody List<COMMENT> cList(@RequestParam("Pnum") int Pnum){

        List<COMMENT> commentList = psvc.cList(Pnum);

        return commentList;
    }

    // C_write : 댓글 작성하기
    @RequestMapping(value = "C_write", method = RequestMethod.POST)
    public @ResponseBody List<COMMENT> cWrite(@ModelAttribute COMMENT comment){

        List<COMMENT>  commentList = psvc.cWirte(comment);

        return commentList;
    }


    // C_delete : 댓글 삭제
    @RequestMapping(value = "C_delete", method = RequestMethod.GET)
    public @ResponseBody List<COMMENT> cDelete(@ModelAttribute COMMENT comment){
        List<COMMENT>  commentList = psvc.cDelete(comment);
        return commentList;
    }

    @RequestMapping(value = "PostProductImg")
    public ModelAndView getImg(@RequestParam("PIMG") String PIMG){
        ModelAndView mv = new ModelAndView("jsonView");
        mv = psvc.PostProductImg(PIMG);

        return mv;
    }

    //pStyleList 게시글 스타일별 목록
    @RequestMapping(value = "pStyleList", method = RequestMethod.GET)
    public ModelAndView pStyleList(@RequestParam("Pstyle") String Pstyle){

        mav = psvc.PstyleList(Pstyle);
        return mav;
    }
    //pCategoryList 상품 카테고리별 목록
    @RequestMapping(value = "pCategoryList", method = RequestMethod.GET)
    public ModelAndView  pCategoryList(@RequestParam("Pcategory") String Pcategory){

        mav = psvc.PcategoryList(Pcategory);
        return mav;
    }


}
