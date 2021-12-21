package com.groovyzam.otqqu.dao;


import com.groovyzam.otqqu.dto.HDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HDAO {

    int hJoin(HDTO human);

    String idOverlap(String hid);
}
