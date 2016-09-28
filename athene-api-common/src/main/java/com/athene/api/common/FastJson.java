package com.athene.api.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by fe on 16/9/18.
 */
public class FastJson {
    public static final Logger logger = LoggerFactory.getLogger(FastJson.class);

    public static final SerializeConfig mapping = new SerializeConfig();

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer(DEFAULT_DATE_FORMAT));
    }

    public static <T> String toJson(T t) {
        return JSON.toJSONString(t, mapping);
    }

    public static <T> String formatJson(T t) {
        return formatJson(toJson(t));
    }

    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     * @param sb
     * @param indent
     * @author   lizhgb
     * @Date   2015-10-14 上午10:38:04
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

    public static <T> T fromJson(String json,Class<T> t) {
        return (T) JSON.parseObject(json, t);
    }

    public static JSONObject fromJson(String json) {
        return JSON.parseObject(json);
    }


    public static <T> List<T> jsonToList(String json, Class<T> t) {
        return (List<T>) JSON.parseArray(json, t);
    }

    public static JSONArray jsonToList(String json) {
        return JSON.parseArray(json);
    }
}
