package com.groovyzam.otqqu.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("mainPostlike")
public class MainPostLike {

    private int Rownum;
    private int Pnum;
    private int LikeNum;

}