package com.groovyzam.otqqu.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("top")
public class TOP {


    private int Pnum;
    private String Tbrand;
    private String Tproduct;
    private int Tprice;
    private String TfileName;

}
