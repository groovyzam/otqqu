package com.groovyzam.otqqu.controller;
import com.groovyzam.otqqu.dto.COMMENT;
import com.groovyzam.otqqu.dto.PDTO;
import com.groovyzam.otqqu.dto.PimgRatioDTO;
import com.groovyzam.otqqu.service.PService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PController {

    ModelAndView mav = new ModelAndView();

    @Autowired
    private PService psvc;

    @Autowired
    private HttpSession session;


    // PostForm : 게시글 등록 페이지로 이동
    @RequestMapping(value="PostForm", method = RequestMethod.GET)
    public String PostForm(){


        return "Post";
    }

    //pUpload
    @ResponseBody
    @RequestMapping(value = "pUpload", method = RequestMethod.POST)
    public ModelAndView pUpload(@ModelAttribute PDTO pdto,
                                @ModelAttribute PimgRatioDTO imgRatio,
                                @RequestParam(value = "Pcategory", required = true) List<String> Pcategory
            , @RequestParam(value = "Pbrand", required = true) List<String> Pbrand
            , @RequestParam(value = "PproductName", required = true) List<String> PproductName
            , @RequestParam(value = "Pprice", required = true) List<Integer> Pprice
            , @RequestParam(value = "PproductFile") List<MultipartFile> PproductFile
            ,@RequestParam(value = "PproductFileImg") List<String> PproductFileImg
    ) throws IOException {
        pdto.setHid((String) session.getAttribute("loginId"));

        //List<MultipartFile> 비어있으면 isEmpty()
        //List<String> 비어있으면 equals("")
        pdto.setHid((String) session.getAttribute("loginId"));
        mav = psvc.pUpload(pdto,imgRatio, Pcategory, Pbrand, PproductName, Pprice, PproductFile,PproductFileImg);

        return mav;
    }

    @RequestMapping(value="mainPost", method = RequestMethod.GET)
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
    //postModifyForm 게시글 수정 페이지 이동
    @RequestMapping(value = "postModifyForm", method = RequestMethod.GET)
    public ModelAndView  postModifyForm(@RequestParam("Pnum") int  Pnum){

        mav = psvc.postModifyForm(Pnum);

        return mav;
    }

    //postModify 게시글 수정
    @RequestMapping(value = "postModify", method = RequestMethod.POST)
    public ModelAndView  postModify(@ModelAttribute PDTO pdto) throws IOException{

        mav = psvc.postModify(pdto);
        return mav;
    }
    //postDelete 게시글 삭제
    @RequestMapping(value = "postDelete", method = RequestMethod.GET)
    public ModelAndView  postDelete(@RequestParam("Pnum") int  Pnum){

        mav = psvc.postDelete(Pnum);

        return mav;
    }

    //postLike 게시글 좋아요
    @RequestMapping(value = "postLike", method = RequestMethod.GET)
    public ModelAndView  postLike(@RequestParam("Pnum") int  Pnum){

        mav = psvc.postLike(Pnum);

        return mav;
    }
    //postLikeDelete 좋아요 취소
    @RequestMapping(value = "postLikeDelete", method = RequestMethod.GET)
    public ModelAndView  postLikeDelete(@RequestParam("Pnum") int  Pnum){

        mav = psvc.postLikeDelete(Pnum);

        return mav;
    }

}


