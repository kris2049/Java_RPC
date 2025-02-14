package serverTest.src.main.java.com.zjz;


import server.RpcServer;
import service.HelloService;
import service.serviceImpl.HelloServiceImpl;

public class Main {
    public static void main(String[] args) {
        try{
            // 创建服务端实例， 指定端口号
            RpcServer rpcServer = new RpcServer(8080);
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