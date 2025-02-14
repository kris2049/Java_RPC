package clientTest.src.main.java.com.zjz;


import client.RpcClientProxy;
import service.HelloService;

public class Main {
    public static void main(String[] args) {
        // 创建客户端代理工厂实例
        RpcClientProxy clientProxy = new RpcClientProxy("127.0.0.1", 8080);

        // 获取服务接口的代理对象
        HelloService helloService = clientProxy.getProxy(HelloService.class);

        // 调用远程服务方法
        String result = helloService.hello("world");

        // 输出调用结果
        System.out.println(result);
    }
}