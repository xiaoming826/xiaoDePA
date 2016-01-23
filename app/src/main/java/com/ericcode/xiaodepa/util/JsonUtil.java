package com.ericcode.xiaodepa.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tim@inspero.im on 15-1-8.
 */
public class JsonUtil {
    public final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        mapper.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonException("序列化失败", e);
        }
    }

    public static JsonNode deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        try {
            return mapper.readTree(data);
        } catch (IOException e) {
            throw new JsonException("数据解析失败", e);
        }
    }

    public static JsonNode deserialize(InputStream is) {
        if (is == null) {
            return null;
        }
        try {
            return mapper.readTree(is);
        } catch (IOException e) {
            throw new JsonException("数据解析失败", e);
        }
    }

    public static <T> T deserialize(String data, Class<T> clzss) {
        if (data == null || data.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(data, clzss);
        } catch (IOException e) {
            throw new JsonException("数据解析失败", e);
        }
    }


    public static <T> T deserialize(InputStream is, Class<T> clzss) {
        if (is == null) {
            return null;
        }
        try {
            return mapper.readValue(is, clzss);
        } catch (IOException e) {
            throw new JsonException("数据解析失败", e);
        }
    }

    public static <T> T deserialize(String data, TypeReference<T> typeReference) {
        if (data == null || data.length() == 0) {
            return null;
        }
        try {
            return mapper.readValue(data, typeReference);
        } catch (IOException e) {
            throw new JsonException("数据解析失败", e);
        }
    }

    public static <T> T deserialize(InputStream is, TypeReference<T> typeReference) {
        if (is == null) {
            return null;
        }
        try {
            return mapper.readValue(is, typeReference);
        } catch (IOException e) {
            throw new JsonException("数据解析失败", e);
        }
    }
}
