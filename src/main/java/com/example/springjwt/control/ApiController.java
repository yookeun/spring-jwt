package com.example.springjwt.control;

import com.example.springjwt.util.ResultCode;
import com.example.springjwt.util.ResultJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @GetMapping("/hello")
    @ResponseBody
    public ResultJson hello(HttpServletRequest request) {
        ResultJson resultJson = new ResultJson();
        resultJson.setCode(ResultCode.SUCCESS.getCode());
        resultJson.setMsg("Hello, " + request.getSession().getAttribute("userId").toString());
        return resultJson;
    }
 }
