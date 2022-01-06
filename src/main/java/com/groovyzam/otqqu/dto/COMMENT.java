package com.groovyzam.otqqu.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("comment")
public class COMMENT {

    private int Cnum;
    private int Pnum;
    private String Cwriter;
    private String Ccontent;

}
