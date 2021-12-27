package com.groovyzam.otqqu.dto;


import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Alias("product")
public class ProductDTO {

    private String Pcategory;
    private String Pbrand;
    private String PproductName;
    private int Pprice;
    private MultipartFile PproductFile;
    private String PproductFileName;

}
