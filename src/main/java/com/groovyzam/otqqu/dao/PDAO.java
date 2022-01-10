package com.groovyzam.otqqu.dao;


import com.groovyzam.otqqu.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Mapper
public interface PDAO {

    int PostUpload(PDTO post);


    int ProductUpload(ProductDTO product);

    List<PDTO> mainPost(int startPnum, int lastPnum);

    PDTO pView(int pnum);

    List<COMMENT> cList(int Pnum);

    int cWrite(COMMENT comment);

    List<PDTO> PstyleList(String pstyle);

    List<PDTO> PcategoryList(String Pcategory);


    int cDelete(COMMENT comment);

    int imgRatioUpload(PimgRatioDTO imgRatio);

    List<CAP> cap(int pnum);

   List<OUTER> outer(int pnum);

    List<TOP> top(int pnum);

    List<BOTTOM> bottom(int pnum);

    List<SHOES> shoes(int pnum);
    List<ACCESSORIES> accessories(int pnum);

    int postModify1(PDTO pdto);

    int postModify2(PDTO pdto);

    int postDelete(int pnum);

    int postLike(POSTLIKE like);

    int postLikeNum(int Pnum);

    String postLikeId(POSTLIKE like);

    int postLikeDelete(POSTLIKE like);
}
