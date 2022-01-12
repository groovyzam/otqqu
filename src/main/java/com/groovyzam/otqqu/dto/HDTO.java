package com.groovyzam.otqqu.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;


@Data
@Alias("human")
public class HDTO {

    private String Hname;

    private String Hid;

    private String Hpw;

    @NotBlank(message = "핸드폰을 입력해주세요")
    private String Hphone;

    private String Hfile;
    MultipartFile HProfile;

}
