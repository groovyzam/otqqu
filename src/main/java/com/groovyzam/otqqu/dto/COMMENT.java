package com.groovyzam.otqqu.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("comment")
public class COMMENT {

    private int Cnum;
    private int Pnum;
    private int Cwriter;
    private int Ccomment;

}
