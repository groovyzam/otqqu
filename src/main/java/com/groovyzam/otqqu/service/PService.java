package com.groovyzam.otqqu.service;

import com.groovyzam.otqqu.dao.PDAO;

import com.groovyzam.otqqu.dto.COMMENT;
import com.groovyzam.otqqu.dto.PDTO;
import com.groovyzam.otqqu.dto.ProductDTO;
import com.groovyzam.otqqu.dto.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Service
public class PService {


    @Autowired
    private PDAO pdao;

    ModelAndView mav = new ModelAndView();

    @Autowired
    private HttpSession session;


    public ModelAndView pUpload(PDTO post, PimgRatioDTO imgRatio, List<String> pcategory, List<String> pbrand, List<String> pproductName, List<Integer> pprice, List<MultipartFile> pproductFile, List<String> PproductFileImg) throws IOException {

        int result2 = 0;

        MultipartFile Pfile = post.getPfile();
        String originalFileName = Pfile.getOriginalFilename();

        String uuid = UUID.randomUUID().toString().substring(1, 7);

        String PfileName = uuid + "_" + originalFileName;


        String savePath = "C:/Users/G/IdeaProjects/otqqu/src/main/resources/static/photo/" + PfileName;


        if (!Pfile.isEmpty()) {
            post.setPfileName(PfileName);
            Pfile.transferTo(new File(savePath));
        } else {
            post.setPfileName("default.png");
        }

        int result1 = pdao.PostUpload(post);

        int result3 = pdao.imgRatioUpload(imgRatio);

        List<MultipartFile> MultiFile = pproductFile;

        for (int i = 0; i < pcategory.size(); i++) {

            String originalFileName2 = null;
            String ProductfileName = null;
            String fileUrl = null;
            String ProductfileNameImg = null;
            String uuid2 = UUID.randomUUID().toString().substring(1, 7);

            String savePath2 = "C:/Users/G/IdeaProjects/otqqu/src/main/resources/static/" + pcategory.get(i);
            String savePath3 = "/" + ProductfileName;

            ProductDTO productDTO = new ProductDTO();

            if (PproductFileImg.size() != 0 && pproductFile.size() != 0) {
                if (pproductFile.get(i).getOriginalFilename().equals("") && !PproductFileImg.get(i).equals("")) {
                    ProductfileNameImg = UUID.randomUUID().toString().substring(1, 7);
                    ProductfileName = uuid2 + "_" + ProductfileNameImg;
                    fileUrl = PproductFileImg.get(i);
                    Path target = Paths.get(savePath2, ProductfileName + ".jpg");

                    URL url = new URL(fileUrl);

                    InputStream in = url.openStream();
                    Files.copy(in, target);
                    in.close();

                    System.out.println(i + "번째 파일 업로드 (url)");
                } else if (!pproductFile.get(i).getOriginalFilename().equals("") && PproductFileImg.get(i).equals("")) {
                    originalFileName2 = pproductFile.get(i).getOriginalFilename();
                    ProductfileName = uuid2 + "_" + originalFileName2;
                    System.out.println(ProductfileName);
                    savePath3 = "/" + ProductfileName;
                    System.out.println(savePath2 + savePath3);
                    MultiFile.get(i).transferTo(new File(savePath2 + savePath3));
                    System.out.println(i + "번째 파일 업로드 (파일)");
                }


                if (!pproductFile.get(i).getOriginalFilename().equals("") || !PproductFileImg.get(i).equals("")) {
                    System.out.println(i + "번 : " + ProductfileName);
                    productDTO.setPproductFileName(ProductfileName);
                } else {
                    productDTO.setPproductFileName("default.png");
                }

            } else {
                ProductfileNameImg = UUID.randomUUID().toString().substring(1, 7);
                ProductfileName = uuid2 + "_" + ProductfileNameImg + ".jpg";
                fileUrl = PproductFileImg.get(i);

                Path target = Paths.get(savePath2, ProductfileName + ".jpg");


                URL url = new URL(fileUrl);

                InputStream in = url.openStream();
                Files.copy(in, target);
                in.close();

                System.out.println(i + "번째 파일 업로드 (url)");


                if (!MultiFile.get(i).isEmpty() || !PproductFileImg.get(i).equals("")) {
                    System.out.println(i + "번 : " + ProductfileName);
                    productDTO.setPproductFileName(ProductfileName);
                } else {
                    productDTO.setPproductFileName("default.png");

                    if (!PproductFileImg.get(i).equals("")) {
                        System.out.println(i + "번 : " + ProductfileName);
                        productDTO.setPproductFileName(ProductfileName);
                    } else {
                        productDTO.setPproductFileName("default.png");
                    }

                }

                productDTO.setPcategory(pcategory.get(i));
                productDTO.setPbrand(pbrand.get(i));
                productDTO.setPproductName(pproductName.get(i));

                productDTO.setPprice(pprice.get(i));
                productDTO.setPproductFileName(ProductfileName);

                result2 = pdao.ProductUpload(productDTO);

            }


            if (result1 > 0 && result2 > 0 && result3 > 0) {

                mav.setViewName("Main");
                System.out.println("게시글 등록 성공");
            } else {
                mav.setViewName("Main");
                System.out.println("게시글 실패");
            }
        }

            return mav;
        }

        // 게시글 정보
        public ModelAndView pView ( int Pnum){
            String sessionId = (String) session.getAttribute("loginId");

            POSTLIKE postlike = new POSTLIKE();
            postlike.setHid(sessionId);
            postlike.setPnum(Pnum);

            PDTO post = pdao.pView(Pnum);
            List<CAP> cap = pdao.cap(Pnum);
            List<OUTER> outer = pdao.outer(Pnum);
            List<TOP> top = pdao.top(Pnum);
            List<BOTTOM> bottom = pdao.bottom(Pnum);
            List<SHOES> shoes = pdao.shoes(Pnum);
            List<ACCESSORIES> accessories = pdao.accessories(Pnum);
            int like = pdao.postLikeNum(Pnum);
            String likeId = pdao.postLikeId(postlike);


            if (post != null) {
                mav.addObject("cap", cap);
                mav.addObject("outer", outer);
                mav.addObject("top", top);
                mav.addObject("bottom", bottom);
                mav.addObject("shoes", shoes);
                mav.addObject("accessories", accessories);
                mav.addObject("post", post);
                mav.addObject("like", like);
                mav.addObject("likeId", likeId);
                mav.setViewName("Pview");
            } else {
                mav.setViewName("Main");
            }

            return mav;
        }

        // 댓글
        public List<COMMENT> cList ( int Pnum){
            List<COMMENT> commentList = pdao.cList(Pnum);

            return commentList;
        }

        // 댓글 작성하기
        public List<COMMENT> cWirte (COMMENT Comment){
            List<COMMENT> commentList = null;

            int result = pdao.cWrite(Comment);

            if (result > 0) {
                commentList = pdao.cList(Comment.getPnum());
            } else {
                commentList = null;
            }

            return commentList;
        }

        // 댓글 삭제하기
        public List<COMMENT> cDelete (COMMENT comment){
            List<COMMENT> commentList = null;

            int result = pdao.cDelete(comment);

            if (result > 0) {
                commentList = pdao.cList(comment.getPnum());
            } else {
                commentList = null;
            }

            return commentList;
        }


        public ModelAndView PostProductImg (String PIMG){


            ModelAndView mv = new ModelAndView("jsonView");
            Map map = new HashMap();

            String URL = "https://www.google.com/search?q=" + PIMG + "&source=lnms&tbm=isch";

            Connection conn = Jsoup.connect(URL);

            try {
                Document html = conn.get();

                System.out.println("Attribute 탐색");
                Elements link = html.getElementsByTag("img");

                int j = 0;
                String attrKey[] = new String[3];

                for (Element e : link) {
                    if (e.attr("data-src") != "") {
                        System.out.println(e.attr("data-src"));
                        attrKey[j] = e.attr("data-src");
                        map.put("Img" + j + "", attrKey[j]);
                        j++;
                    }
                    if (j == 3) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            map.put("suc", "성공");
            mv.addAllObjects(map);
            mv.setViewName("jsonView");

            return mv;
        }

        //기존 Main화면 게시글
        public ModelAndView mainPost () {

            int StartPnum = 1;
            int LastPnum = 12;
            List<PDTO> postList = pdao.mainPost(StartPnum, LastPnum);

            mav.addObject("postList", postList);
            mav.setViewName("Main");

            return mav;
        }

        //Main화면 무한스크롤
        public List<PDTO> ajaxPost ( int page){
            int StartPnum = page;
            int LastPnum = page;


            List<PDTO> postList = pdao.mainPost(StartPnum, LastPnum);


            return postList;

        }


        //스타일별 게시글

        public ModelAndView PstyleList (String pstyle){

            List<PDTO> list = pdao.PstyleList(pstyle);

            System.out.println(list);

            mav.addObject("StyleList", list);
            mav.setViewName("Pstyle");

            return mav;
        }

        //카테고리별 게시글
        public ModelAndView PcategoryList (String Pcategory){


            List<PDTO> list = pdao.PcategoryList(Pcategory);

            System.out.println(Pcategory);
            mav.addObject("CategoryList", list);
            mav.setViewName("Pcategory");
            return mav;
        }

        //게시글 수정 페이지 이동
        public ModelAndView postModifyForm ( int Pnum){

            PDTO post = pdao.pView(Pnum);
            List<CAP> cap = pdao.cap(Pnum);
            List<OUTER> outer = pdao.outer(Pnum);
            List<TOP> top = pdao.top(Pnum);
            List<BOTTOM> bottom = pdao.bottom(Pnum);
            List<SHOES> shoes = pdao.shoes(Pnum);
            List<ACCESSORIES> accessories = pdao.accessories(Pnum);

            if (post != null) {
                mav.addObject("cap", cap);
                mav.addObject("outer", outer);
                mav.addObject("top", top);
                mav.addObject("bottom", bottom);
                mav.addObject("shoes", shoes);
                mav.addObject("accessories", accessories);
                mav.addObject("post", post);
                mav.setViewName("Pmodify");
            } else {
                mav.setViewName("Main");
            }

            return mav;
        }

        public ModelAndView postModify (PDTO pdto) throws IOException {

            int result = 0;
            if (pdto.getPfile() == null) {
                result = pdao.postModify1(pdto);
            } else {
                MultipartFile Pfile = pdto.getPfile();
                String originalFileName = Pfile.getOriginalFilename();

                String uuid = UUID.randomUUID().toString().substring(1, 7);

                String PfileName = uuid + "_" + originalFileName;

                String savePath = "C:/Users/G/IdeaProjects/otqqu/src/main/resources/static/photo/" + PfileName;

                if (!Pfile.isEmpty()) {
                    pdto.setPfileName(PfileName);
                    Pfile.transferTo(new File(savePath));
                } else {
                    pdto.setPfileName("default.png");
                }

                result = pdao.postModify2(pdto);
            }

            if (result > 0) {

                mav.setViewName("redirect:/pView?Pnum=" + pdto.getPnum() + "");
            }


            return mav;
        }

        public ModelAndView postDelete ( int pnum){

            int result = pdao.postDelete(pnum);

            if (result > 0) {
                mav.setViewName("redirect:/");
            }
            return mav;
        }

        public ModelAndView postLike ( int pnum){

            String sessionId = (String) session.getAttribute("loginId");

            POSTLIKE like = new POSTLIKE();
            like.setHid(sessionId);
            like.setPnum(pnum);

            int result = pdao.postLike(like);

            if (result > 0) {
                mav.setViewName("redirect:pView?Pnum=" + pnum + "");
            }

            return mav;
        }

        public ModelAndView postLikeDelete (int pnum){

            String sessionId = (String) session.getAttribute("loginId");

            POSTLIKE like = new POSTLIKE();
            like.setHid(sessionId);
            like.setPnum(pnum);

            int result = pdao.postLikeDelete(like);

            if (result > 0) {
                mav.setViewName("redirect:pView?Pnum=" + pnum + "");
            }
            return mav;
        }

    public List<String> searchBrand(String keyword) {
        List<String> searchBrand = pdao.searchBrand(keyword);

        return searchBrand;
    }
}
