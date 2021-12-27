package com.groovyzam.otqqu.controller;

import com.groovyzam.otqqu.dto.PDTO;
import com.groovyzam.otqqu.dto.ProductDTO;
import com.groovyzam.otqqu.service.PService;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class PController {

    ModelAndView mav = new ModelAndView();

    @Autowired
    private PService psvc;

    //pUpload
    @ResponseBody
    @RequestMapping(value = "pUpload", method = RequestMethod.POST)
    public ModelAndView pUpload(@ModelAttribute PDTO pdto,
              @RequestParam(value = "Pcategory", required = true) List<String> Pcategory
            , @RequestParam(value = "Pbrand", required = true) List<String> Pbrand
            , @RequestParam(value = "PproductName", required = true) List<String> PproductName
            , @RequestParam(value = "Pprice", required = true) List<String> Pprice
            , @RequestParam(value = "PproductFile", required = true) List<MultipartFile> PproductFile

    ) throws IOException{
        System.out.println("asdf");
        mav=psvc.pUpload(pdto,Pcategory,Pbrand,PproductName,Pprice,PproductFile);

        return mav;
    }



}
