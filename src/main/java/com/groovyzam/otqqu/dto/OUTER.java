package com.groovyzam.otqqu.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("outer")
public class OUTER {

    private int Pnum;
    private String Obrand;
    private String Oproduct;
    private int Oprice;
    private String OfileName;

}
