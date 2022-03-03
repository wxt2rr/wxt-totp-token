package com.wangxt.totp.token.service.impl;

import com.wangxt.totp.token.util.DynamicToken;
import com.wangxt.totp.token.service.PostService;
import org.springframework.stereotype.Service;

/**
 *
 * 服务层实现
 * @author wangxt
 * @date 2021/06/07 15:36
 */
@Service("postService")
public class PostServiceImpl implements PostService {

    public String generate() {
        String result = "error";
        DynamicToken dt = new DynamicToken("");
        try {
            result = dt.getDynamicCode();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
