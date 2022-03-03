package com.wangxt.totp.token.controller;

import com.wangxt.totp.token.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RestController
@RequestMapping("token")
public class TokenController {

    @Autowired
    private PostService postService;

    @GetMapping("generate")
    public void generate(HttpServletResponse response) throws Exception{
        String generate = postService.generate();
        String[] split = generate.split(",");
        String token = split[0];
        String time = split[1];

        response.setContentType("text/html;charset=gb2312");

        PrintWriter p = response.getWriter();
        p.print("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>token</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "token <input id=\"token\" type=\"text\" width=\"10px\" name=\"\" value=\""+ token+"\">\n" +
                "\n" +
                "<button onclick=\"Copy()\">复制</button>\n" +
                "还剩 "+ time+" 秒过期\n" +
                "</body>\n" +
                "<script type=\"text/javascript\">\n" +
                "    function Copy() {\n" +
                "        var content = document.getElementById('token');\n" +
                "        content.select();\n" +
                "        document.execCommand('Copy');\n" +
                "    }\n" +
                "</script>\n" +
                "</html>");
        p.close();
    }
}
