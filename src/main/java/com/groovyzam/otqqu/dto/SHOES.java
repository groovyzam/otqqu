package com.groovyzam.otqqu.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("shoes")
public class SHOES {

    private int Pnum;
    private String Sbrand;
    private String Sproduct;
    private int Sprice;
    private String SfileName;

}
