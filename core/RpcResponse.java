package core;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcResponse implements Serializable {
    // 序列化的类的版本号
    private static final long serialVersionUID = 1L;

    private Object result;      // 方法调用结果
    private Throwable error;    // 异常信息

    // 构造方法
    public RpcResponse() {};

    // 构造方法
    public RpcResponse(Object result, Throwable error) {
        this.result = result;
        this.error = error;
    }


}
