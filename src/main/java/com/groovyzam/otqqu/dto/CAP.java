package com.groovyzam.otqqu.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("cap")
public class CAP{

    private int Pnum;
    private String Cbrand;
    private String Cproduct;
    private int Cprice;
    private String CfileName;

}
