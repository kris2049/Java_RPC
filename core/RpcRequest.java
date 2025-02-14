package core;

import lombok.Data;

import java.io.Serializable;


@Data
public class RpcRequest implements Serializable {
    // 序列化的类的版本号
    private static final long serialVersionUID = 1L;

    private String className;           // 想要调用的服务类名
    private String methodName;          // 想要调用的方法名
    private Object[] params;            // 方法参数
    private Class<?>[] parameterTypes;  // 参数类型

    // 构造方法
    public RpcRequest(){};

    // 构造方法
    public RpcRequest(String className, String methodName, Object[] params, Class<?>[] parameterTypes){
        this.className = className;
        this.methodName = methodName;
        this.params = params;
        this.parameterTypes = parameterTypes;
    }

    // getter和setter 由@Data处理


}
