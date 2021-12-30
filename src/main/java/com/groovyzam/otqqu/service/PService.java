package com.groovyzam.otqqu.service;

import com.groovyzam.otqqu.dao.PDAO;
import com.groovyzam.otqqu.dto.COMMENT;
import com.groovyzam.otqqu.dto.HDTO;
import com.groovyzam.otqqu.dto.PDTO;
import com.groovyzam.otqqu.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PService {


    @Autowired
    private PDAO pdao;

    ModelAndView mav = new ModelAndView();

    @Autowired
    private HttpSession session;


    public ModelAndView pUpload(PDTO post, List<String> pcategory, List<String> pbrand, List<String> pproductName, List<String> pprice, List<MultipartFile> pproductFile) throws IOException {



        int result2 = 0;

        MultipartFile Pfile = post.getPfile();
        String originalFileName = Pfile.getOriginalFilename();

        String uuid = UUID.randomUUID().toString().substring(1, 7);

        String PfileName = uuid + "_" + originalFileName;

        String savePath = "C:/Users/PC/SpringBoot/otqqu/src/main/resources/static/photo/" + PfileName;


        if (!Pfile.isEmpty()) {
            post.setPfileName(PfileName);
            Pfile.transferTo(new File(savePath));
        } else {
            post.setPfileName("default.png");
        }



        int result1 = pdao.PostUpload(post);


        List<MultipartFile> MultiFile = pproductFile;

        for (int i = 0; i < pcategory.size(); i++) {
            

            ProductDTO productDTO = new ProductDTO();


            String originalFileName2 = MultiFile.get(i).getOriginalFilename();


             String uuid2 = UUID.randomUUID().toString().substring(1, 7);

            String ProductfileName = uuid2 + "_" + originalFileName2;

            String savePath2 = "C:/Users/PC/SpringBoot/otqqu/src/main/resources/static/" + pcategory.get(i)+"/" + ProductfileName;

            if (!MultiFile.get(i).isEmpty()) {
                productDTO.setPproductFileName(ProductfileName);
                    MultiFile.get(i).transferTo(new File(savePath2));

            } else {
                productDTO.setPproductFileName("default.png");
            }

            productDTO.setPcategory(pcategory.get(i).toString());
            productDTO.setPbrand(pbrand.get(i).toString());
            productDTO.setPproductName(pproductName.get(i).toString());
            productDTO.setPprice(Integer.parseInt(pprice.get(i).toString()));
            productDTO.setPproductFileName(ProductfileName);

            result2 = pdao.ProductUpload(productDTO);

        }


        if (result1 > 0 && result2 > 0) {

            mav.setViewName("Main");
            System.out.println("게시글 등록 성공");
        } else {
            mav.setViewName("Main");
            System.out.println("게시글 실패");
        }


        return mav;
    }


    public ModelAndView pView(int Pnum) {
        PDTO post = pdao.pView(Pnum);
        System.out.println("post ============== "  + post);

        if( post != null){
            mav.addObject("post", post);
            mav.setViewName("Pview");
        }else{
            mav.setViewName("Main");
        }

        return mav;
    }

    // 댓글
    public List<COMMENT> cList(int Pnum) {
        List<COMMENT> commentList = pdao.cList(Pnum);
        System.out.println("commentlist : " + commentList ) ;
        return commentList;
    }

    public List<COMMENT> cWirte(COMMENT Comment) {
        List<COMMENT> commentList = null;

        int result = pdao.cWrite(Comment);

        if(result>0){
            commentList = pdao.cList(Comment.getPnum());
        }else{
            commentList=null;
        }

        return commentList;
    }
}
