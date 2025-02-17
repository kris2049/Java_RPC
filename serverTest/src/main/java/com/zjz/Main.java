package serverTest.src.main.java.com.zjz;


import server.RpcServer;
import service.HelloService;
import service.serviceImpl.HelloServiceImpl;
import utils.serialize.Serializer;
import utils.serialize.protoStuff.ProtoStuffSerializer;

public class Main {
    public static void main(String[] args) {
        try{
            // 创建序列化工具
            Serializer serializer = new ProtoStuffSerializer();
            // 创建服务端实例， 指定端口号
            RpcServer rpcServer = new RpcServer(8080,serializer);
            // 创建服务实现类实例
            HelloService helloService = new HelloServiceImpl();

            // 注册服务
            rpcServer.registerService(HelloService.class, helloService);

            // 启动服务端
            rpcServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}