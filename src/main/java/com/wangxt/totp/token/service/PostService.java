package com.wangxt.totp.token.service;

import org.springframework.stereotype.Service;

/**
 *
 * 服务层
 * @author wangxt
 * @date 2021/06/07 15:36
 */
@Service
public interface PostService {

    String generate();
}
