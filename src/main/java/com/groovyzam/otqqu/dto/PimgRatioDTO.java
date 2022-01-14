package com.groovyzam.otqqu.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("imgRatio")
public class PimgRatioDTO {
    private String imgRatioWidth, imgRatioHeight, imgTop, imgLeft;
    private int Ratio;
}
