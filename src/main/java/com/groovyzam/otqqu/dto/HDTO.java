package com.groovyzam.otqqu.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

@Data
@Alias("human")
public class HDTO {

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
    MultipartFile HProfile;

}
