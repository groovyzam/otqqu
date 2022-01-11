package com.groovyzam.otqqu.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("like")
public class POSTLIKE {

    private String Hid;
    private int Pnum;

}
