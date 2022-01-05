package com.groovyzam.otqqu.dao;


import com.groovyzam.otqqu.dto.COMMENT;
import com.groovyzam.otqqu.dto.PDTO;
import com.groovyzam.otqqu.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

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

}
