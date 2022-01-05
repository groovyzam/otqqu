package com.groovyzam.otqqu.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;


import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;


@Data
@Alias("human")
public class HDTO {


    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String Hname;

    @NotBlank(message = "아이디를 입력해주세요")
    private String Hid;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String Hpw;

    @NotBlank(message = "이메일을 입력해주세요")
    private String Hemail;


    @Max(value = 200)
    @Positive
    private int Hage;

    @NotBlank(message = "키를 입력해주세요")
    @Positive
    private String Hheight;

    @NotBlank(message = "몸무게을 입력해주세요")
    @Positive
    private String Hweight;

    @NotBlank(message = "성별을 입력해주세요")
    private String Hgender;

    @NotBlank(message = "핸드폰을 입력해주세요")
    private String Hphone;

    private String Hfile;
    MultipartFile HProfile;

}
