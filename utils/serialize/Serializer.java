package utils.serialize;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;

import java.io.IOException;

/**
 * 抽象出来的序列化与反序列化工具类接口
 */

public interface Serializer {
    /**
     * 序列化
     * @param obj 要序列化的对象
     * @return 字节数组
     */
    byte[] serialize(Object obj);


    /**
     * 反序列化
     * @param bytes 序列化后的字节数组
     * @param clazz 要反序列化的对象类型
     * @param <T>  对象类型
     * @return 反序列化后的对象
     */
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}