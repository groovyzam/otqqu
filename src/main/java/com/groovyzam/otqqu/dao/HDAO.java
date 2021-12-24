package com.groovyzam.otqqu.dao;

import com.groovyzam.otqqu.dto.HDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HDAO {
    List<HDTO> hList();

    HDTO hLogin(HDTO human);

    int hJoin(HDTO human);

    String idOverlap(String hid);
}
