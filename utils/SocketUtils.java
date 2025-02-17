package utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Socket通信工具类
 */
public class SocketUtils {
    // 客户端建立Socket链接并发送请求，接受响应
    public static byte[] sendRequest(String host, int port, byte[] request) throws IOException {
        // 建立socket连接，使用host和端口
        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(host, port))) {
            // 创建缓存区，将序列化之后的字符串请求打包到缓存区
            ByteBuffer buffer = ByteBuffer.wrap(request);
            // 将缓存区的内容写入socket
            socketChannel.write(buffer);

            // 清空缓存区
            buffer.clear();
            // 从socket读取数据到缓存
            socketChannel.read(buffer);
            // 根据缓存区中元素的数量创建字节数组当作响应
            byte[] responseBytes = new byte[buffer.remaining()];
            // 将缓存区中的内容写入到响应字节数组
            buffer.get(responseBytes);


            return responseBytes;
        }
    }

    // 服务端接收客户端请求
    public static byte[] receiveRequest(SocketChannel socketChannel) throws IOException {
        // 创建一个缓存区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // socket写入数据到缓存区
        socketChannel.read(buffer);

        // 指针指向缓存头部，并且设置最多只能读出之前写入的数据长度(不是整个缓存的大小)
        buffer.flip();

        // 创建请求字节数组
        byte[] requestBytes = new byte[buffer.remaining()];
        // 将缓存区的数据写入字节数组
        buffer.get(requestBytes);

        return requestBytes;
    }

    // 服务端发送响应给客户端
    public static void sendResponse(SocketChannel socketChannel, byte[] response) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(response);
//        System.out.println(Arrays.toString(response));
        socketChannel.write(buffer);
    }
}
