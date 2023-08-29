package com.lion.utcompanytraining.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 资源辅助类
 */
public final class ResourceHelper {

    /**
     * 构造方法
     */
    private ResourceHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * 以字符串方式获取资源
     *
     * @param clazz 类
     * @param name 资源名称
     * @return 字符串
     */
    public static <T> String getResourceAsString(Class<T> clazz, String name) {
        try (InputStream is = clazz.getResourceAsStream(name)) {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException  e) {
            throw new RuntimeException(String.format("以字符串方式获取资源(%s)异常", name), e);
        }
    }

    /**
     * 以字符串方式获取对象
     *
     * @param clazz 类
     * @param name 资源名称
     * @return 字符串
     */
    public static <T,Z> Z getResourceAsObject(Class<T> clazz, String name,Class<Z> clazz1) {
        String string = getResourceAsString(clazz, name);
        return JSON.parseObject(string, clazz1);
    }

    /**
     * 反序列化List对象
     *
     * @param clazz 类
     * @param name 资源名称
     * @return 字符串
     */
    public static <T,Z> List<Z> getResourceAsList(Class<T> clazz, String name, Class<Z> clazz1) {
        String string = getResourceAsString(clazz, name);
        return JSON.parseArray(string, clazz1);
    }

    /**
     * 反序列化映射对象
     *
     * @param clazz 类
     * @param name 资源名称
     * @return 字符串
     */
    public static <T,K,Y> Map<K,Y> getResourceAsMap(Class<T> clazz, String name, Class<K> clazz1, Class<Y> clazz2) {
        String string = getResourceAsString(clazz, name);
        return JSON.parseObject(string, new TypeReference<Map<K, Y>>() {});
    }
}