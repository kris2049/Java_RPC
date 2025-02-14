package utils.serialization;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;

import java.io.IOException;

/**
 * 抽象出来的序列化与反序列化工具类
 */
public class SerializationUtils {
    // 序列化对象为 JSON 字符串
    public static String serialize(Object obj) {
        return JSON.toJSONString(obj);
    }

    // 反序列化 JSON 字符串为对象
    public static <T> T deserialize(String json, Class<T> clazz) throws IOException {
        return JSON.parseObject(json, clazz,JSONReader.Feature.SupportClassForName);
    }
}