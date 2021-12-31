package com.groovyzam.otqqu.controller;

import com.groovyzam.otqqu.dto.PDTO;
import com.groovyzam.otqqu.service.PService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

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
    public @ResponseBody List<PDTO> ajaxPost(@RequestParam ("page") int page){

        List<PDTO> list=psvc.ajaxPost(page);
        return list;
    }



    @RequestMapping(value = "PostProductImg")
    public ModelAndView getImg(@RequestParam("PIMG") String PIMG){
        ModelAndView mv = new ModelAndView("jsonView");
        mv = psvc.PostProductImg(PIMG);

        return mv;
    }
}
