package com.groovyzam.otqqu.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("bottom")
public class BOTTOM {

    private int Pnum;
    private String Bbrand;
    private String Bproduct;
    private int Bprice;
    private String BfileName;

}
