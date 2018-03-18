package com.yang.rpc.common.bean;

import lombok.Data;

/**
 * Created by yz on 2018/1/28.
 */

/**
 * 封装 RPC 请求
 */
@Data
public class RpcRequest {
    private String requestId;
    private String interfaceName;
    private String serviceVersion;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
}
