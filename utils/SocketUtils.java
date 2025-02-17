package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;


/**
 * Socket通信工具类
 */
public class SocketUtils {
    // 客户端建立Socket链接并发送请求，接受响应
    public static byte[] sendRequest(String host, int port, byte[] request) throws IOException {
        // 建立socket连接，使用host和端口
        try (Socket socket = new Socket(host, port)) {
            // 发送请求
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(request);
            outputStream.flush();
            socket.shutdownOutput();

            // 接收响应
            InputStream inputStream = socket.getInputStream();
            java.io.ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while( (bytesRead = inputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            byte[] responseBytes = byteArrayOutputStream.toByteArray();

            System.out.println(Arrays.toString(responseBytes));

            return responseBytes;
        }
    }

    // 服务端接收客户端请求
    public static byte[] receiveRequest(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        // 使用动态数组来处理不确定长度的数据
        java.io.ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return byteArrayOutputStream.toByteArray();
    }

    // 服务端发送响应给客户端
    public static void sendResponse(Socket socket, byte[] response) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(response);
        outputStream.flush();
        outputStream.close();
    }
}
