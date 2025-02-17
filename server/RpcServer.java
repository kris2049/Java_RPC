package server;

import core.RpcRequest;
import core.RpcResponse;
import utils.serialize.Serializer;
import utils.SocketUtils;


import java.io.IOException;

import java.lang.reflect.Method;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 服务端
 */
public class RpcServer {
    private final int port;
    private final Serializer serializer;

    // 创建服务名与服务的映射表
    private final Map<String, Object> serviceMap = new HashMap<>();

    // 构造方法
    public RpcServer(int port, Serializer serializer) {
        this.port = port;
        this.serializer = serializer;
    }

    // 服务注册，传入服务接口类和实现类
    public void registerService(Class<?> serviceInterface, Object serviceImpl){
        // 将服务登记到服务表中
        serviceMap.put(serviceInterface.getName(), serviceImpl);
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("RPCServer started on port " + port);

            while(true){
                try(Socket socket = serverSocket.accept()){
                    // 接收客户端请求
                    byte[] request = SocketUtils.receiveRequest(socket);


                    // 将请求反序列化
                    RpcRequest rpcRequest = serializer.deserialize(request, RpcRequest.class);

                    System.out.println(rpcRequest.getClassName());
                    System.out.println(rpcRequest.getMethodName());
                    System.out.println(Arrays.toString(rpcRequest.getParameterTypes()));
                    System.out.println(Arrays.toString(rpcRequest.getParams()));


                    // 处理请求
                    RpcResponse rpcResponse = handleRequest(rpcRequest);


                    // 将响应对象序列化
                    byte[] response = serializer.serialize(rpcResponse);

                    // 发送响应给客户端
                    SocketUtils.sendResponse(socket, response);
                }catch (IOException e){
                    System.out.println("Error handling client request: "+e.getMessage());
                }
            }
        }
    }

    private RpcResponse handleRequest(RpcRequest rpcRequest){
        try{
            // 根据类名从映射表中获取服务实例
            Object service = serviceMap.get(rpcRequest.getClassName());
            if(service == null){
                throw new ClassNotFoundException("Service not found: "+rpcRequest.getClassName());
            }

            // 根据方法名和参数类型获取方法对象
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParameterTypes());

            // 调用方法并获取结果
            Object result = method.invoke(service, rpcRequest.getParams());

            System.out.println(result);

            return new RpcResponse(result,null);
        }catch (Exception e){
            return new RpcResponse(null,e);
        }
    }
}
