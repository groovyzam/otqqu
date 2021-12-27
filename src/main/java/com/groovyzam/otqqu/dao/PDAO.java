package com.groovyzam.otqqu.dao;


import com.groovyzam.otqqu.dto.PDTO;
import com.groovyzam.otqqu.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PDAO {

    int PostUpload(PDTO post);


    int ProductUpload(ProductDTO product);
}