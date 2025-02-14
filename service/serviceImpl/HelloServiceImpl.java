package service.serviceImpl;

import service.HelloService;

/**
 * 简单的服务实现类，测试rpc调用
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello" + name;
    }
}
