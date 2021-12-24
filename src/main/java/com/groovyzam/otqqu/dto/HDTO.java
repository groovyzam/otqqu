package com.groovyzam.otqqu.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("human")
public class HDTO {
    private int Hnum;
    private String Hname;
    private String Hid;
    private String Hpw;
    private String Hemail;
    private int Hage;
    private String Hheight;
    private String Hweight;
    private String Hgender;
    private String Hphone;
    private String Hfile;

}
