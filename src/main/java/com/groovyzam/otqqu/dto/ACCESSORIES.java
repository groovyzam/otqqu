package com.groovyzam.otqqu.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("accessories")
public class ACCESSORIES {
    private int Pnum;
    private String Abrand;
    private String Aproduct;
    private int Aprice;
    private String AfileName;

}
