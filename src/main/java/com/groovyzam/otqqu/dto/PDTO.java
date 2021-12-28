package com.groovyzam.otqqu.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Alias("post")
public class PDTO {
    private int Pnum;
    private String Hid;
    private String Ptitle;
    private String Pcontent;
    private String Pstyle;
    private int Pup;
    private MultipartFile Pfile;
    private String PfileName;
}
