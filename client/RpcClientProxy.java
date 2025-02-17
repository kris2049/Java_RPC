package client;

import core.RpcRequest;
import core.RpcResponse;
import utils.serialize.Serializer;
import utils.SocketUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * 客户端动态代理，为服务接口生成代理对象，实现远程调用。
 */
public class RpcClientProxy {
    private final String host;
    private final int port;
    private final Serializer serializer;

    public RpcClientProxy(String host, int port, Serializer serializer) {
        this.host = host;
        this.port = port;
        this.serializer = serializer;
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
                        byte[] request = serializer.serialize(rpcRequest);

                        System.out.println(Arrays.toString(request));

                        // 发送请求并接收响应
                        byte[] response = SocketUtils.sendRequest(host,port,request);

                        System.out.println(Arrays.toString(response));


                        // 反序列化响应对象
                        RpcResponse rpcResponse = serializer.deserialize(response,RpcResponse.class);

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
