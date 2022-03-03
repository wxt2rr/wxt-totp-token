package com.wangxt.totp.token.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * 结果返回 VO
 * @author wangxt
 * @date 2021/06/07 15:41
 */
@Data
@AllArgsConstructor
public class ResultVo<T> {
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 相应描述
     */
    private String msg;

    /**
     * 响应体
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T body;
}
