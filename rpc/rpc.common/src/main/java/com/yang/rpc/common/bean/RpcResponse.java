package com.yang.rpc.common.bean;

import lombok.Data;

/**
 * Created by yz on 2018/1/28.
 */

/**
 * 封装 RPC 响应
 */
@Data
public class RpcResponse {
    private String requestId;
    private Exception exception;
    private Object result;

    public boolean hasException() {
        return null != exception;
    }
}
