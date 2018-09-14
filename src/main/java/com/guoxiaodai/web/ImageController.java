package com.guoxiaodai.web;

import com.guoxiaodai.pojo.common.Result;
import com.guoxiaodai.utils.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 文件上传管理controller
 */
@Controller
public class ImageController {

    @RequestMapping(value = "/image/imageUpload",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> uploadFile(@RequestParam("file") MultipartFile files,
                                     HttpServletRequest request){
        return new ResultUtil<Object>().setData(null);
    }
}
