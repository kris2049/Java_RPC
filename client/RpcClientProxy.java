package client;

import core.RpcRequest;
import core.RpcResponse;
import utils.serialization.SerializationUtils;
import utils.SocketUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 客户端动态代理，为服务接口生成代理对象，实现远程调用。
 */
public class RpcClientProxy {
    private final String host;
    private final int port;

    public RpcClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // 获得服务类的代理
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> serviceClass){
        return (T) Proxy.newProxyInstance(          // 创建代理实例
                serviceClass.getClassLoader(),      // 获得服务类的类加载器
                new Class<?>[]{serviceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 创建RpcRequest对象
                        RpcRequest rpcRequest = new RpcRequest(
                                serviceClass.getName(),
                                method.getName(),
                                args,
                                method.getParameterTypes()
                        );

                        // 序列化请求对象
                        String request = SerializationUtils.serialize(rpcRequest);

                        // 发送请求并接收响应
                        String response = SocketUtils.sendRequest(host,port,request);

                        System.out.println(response);


                        // 反序列化响应对象
                        RpcResponse rpcResponse = SerializationUtils.deserialize(response,RpcResponse.class);

                        // 处理异常
                        if (rpcResponse.getError() != null) {
                            throw rpcResponse.getError();
                        }

                        return rpcResponse.getResult();
                    }
                }
        );
    }

}
