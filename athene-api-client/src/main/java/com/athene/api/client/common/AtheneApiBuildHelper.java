package com.athene.api.client.common;

import com.athene.api.client.annotation.ApiField;
import com.athene.api.client.annotation.ApiGroup;
import com.athene.api.client.annotation.ApiMethod;
import com.athene.api.client.annotation.ApiParam;
import com.athene.api.client.exception.AtheneClientException;
import com.athene.api.client.mapping.AtheneApiGroupMapping;
import com.athene.api.client.mapping.AtheneApiMethodMapping;
import com.athene.api.client.mapping.AtheneApiMethodParamMapping;
import com.athene.api.client.mapping.AtheneApiMethodResultMapping;
import com.athene.api.common.FastJson;
import com.athene.api.common.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by fe on 16/9/18.
 */
public class AtheneApiBuildHelper {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final Logger logger = LoggerFactory.getLogger(AtheneApiBuildHelper.class);

    public static AtheneApiGroupMapping buildAtheneApiGroup(Class<?> clazz) {
        if (clazz == null) throw new AtheneClientException("clazz can not be null!");

        if (!clazz.isInterface()) throw new AtheneClientException(clazz.getName() + ", is not interface!");
        AtheneApiGroupMapping atheneApiGroupMapping = null;
        try {
            List<AtheneApiMethodMapping> atheneApiServiceMappingList = null;
            ApiGroup apiGroup = clazz.getAnnotation(ApiGroup.class);
            if (apiGroup != null) {
                atheneApiGroupMapping = new AtheneApiGroupMapping();
                atheneApiGroupMapping.setServiceName(clazz.getName());
                atheneApiGroupMapping.setOwner(apiGroup.owner());
                atheneApiGroupMapping.setVersion(apiGroup.version());
                atheneApiGroupMapping.setNeedAuth(apiGroup.needAuth());
                atheneApiGroupMapping.setDescription(apiGroup.description());
                atheneApiGroupMapping.setGroup(apiGroup.group());

                Method[] methods = clazz.getMethods();
                if (methods != null && methods.length > 0) {
                    atheneApiServiceMappingList = new LinkedList<AtheneApiMethodMapping>();
                    for (Method m : methods) {
                        m.setAccessible(true);
                        AtheneApiMethodMapping atheneApiMethodMapping = parseMethod(m);
                        atheneApiServiceMappingList.add(atheneApiMethodMapping);
                        atheneApiGroupMapping.setAtheneApiMethodMappings(atheneApiServiceMappingList);

                        AtheneApiMethodResultMapping atheneApiMethodResultMapping = parseMethodResult(m);
                        atheneApiMethodMapping.setAtheneApiMethodResultMapping(atheneApiMethodResultMapping);
                    }
                }
            }
            System.out.println(FastJson.formatJson(atheneApiGroupMapping));
        } catch (Exception e) {
            logger.error("buildAtheneApiGroup error !, e : {}",e);

        }
        return atheneApiGroupMapping;
    }

    private static AtheneApiMethodMapping parseMethod(Method m) throws Exception {
        AtheneApiMethodMapping atheneApiMethodMapping = new AtheneApiMethodMapping();
        String methodName = m.getName();
        ApiMethod apiMethod = m.getAnnotation(ApiMethod.class);
        String description = apiMethod.description();

        atheneApiMethodMapping.setMethodName(methodName);
        atheneApiMethodMapping.setDescription(description);
        atheneApiMethodMapping.setNeedAuth(apiMethod.needAuth());

        Class<?>[] parameterTypes = m.getParameterTypes();
        if (parameterTypes != null && parameterTypes.length > 0) {
            List<AtheneApiMethodParamMapping> atheneApiMethodParamMappings = parseMethodParam(m);
            atheneApiMethodMapping.setAtheneApiMethodParamMappingList(atheneApiMethodParamMappings);
        }
        return atheneApiMethodMapping;
    }

    private static List<AtheneApiMethodParamMapping> parseMethodParam(Method m) throws Exception {
        Type[] types = m.getGenericParameterTypes();
        List<AtheneApiMethodParamMapping> atheneApiMethodParamMappings = null;
        if (types != null && types.length > 0) {
            Annotation[][] parameterAnnotations = m.getParameterAnnotations();

            atheneApiMethodParamMappings = new LinkedList<AtheneApiMethodParamMapping>();
            int index = 0;
            for (Type type : types) {
                AtheneApiMethodParamMapping atheneApiMethodParamMapping = new AtheneApiMethodParamMapping();
                Annotation annotation = parameterAnnotations[index][0];
                if (annotation instanceof ApiParam) {
                    ApiParam apiParam = (ApiParam) annotation;

                    atheneApiMethodParamMapping.setDescription(apiParam.description());
                    atheneApiMethodParamMapping.setRequired(apiParam.required());

                    String genericType = ReflectionUtils.fixTypeName(type);
                    atheneApiMethodParamMapping.setParamType(genericType);

                    //TODO 函数参数是否是List类型泛型
                    if (ReflectionUtils.isCollectionGenericType(genericType)) {
                        String innerFullClassName = ReflectionUtils.getCollectionGenericType(genericType);
                        setAtheneApiMethodParamValue(innerFullClassName,atheneApiMethodParamMapping,apiParam);
                    } else {
                        //TODO 是否是用户自定义类型
                        setAtheneApiMethodParamValue(genericType,atheneApiMethodParamMapping,apiParam);
                    }
                }
                index++;
                atheneApiMethodParamMappings.add(atheneApiMethodParamMapping);
            }
        }
        return atheneApiMethodParamMappings;
    }

    private static void setAtheneApiMethodParamValue(String fullClassName,AtheneApiMethodParamMapping atheneApiMethodParamMapping,ApiParam apiParam) throws Exception{
        boolean isCustomized = ReflectionUtils.isCustomized(fullClassName);
        if (isCustomized) {
            Class<?> customizedClazz = Class.forName(fullClassName);

            Field[] fields = customizedClazz.getDeclaredFields();
            List<AtheneApiMethodParamMapping> defaultValueList = new LinkedList<AtheneApiMethodParamMapping>();
            for (Field f : fields) {
                ApiField apiField = f.getAnnotation(ApiField.class);
                AtheneApiMethodParamMapping childAtheneApiMethodParamMapping = new AtheneApiMethodParamMapping();
                childAtheneApiMethodParamMapping.setDefaultValue(apiField.defaultValue());
                childAtheneApiMethodParamMapping.setDescription(apiField.description());
                childAtheneApiMethodParamMapping.setRequired(apiField.required());
                childAtheneApiMethodParamMapping.setParamType(ReflectionUtils.fixTypeName(f.getGenericType()));
                defaultValueList.add(childAtheneApiMethodParamMapping);
            }
            atheneApiMethodParamMapping.setDefaultValue(defaultValueList);
        } else {
            String defaultValue = apiParam.defaultValue();
            atheneApiMethodParamMapping.setDefaultValue(defaultValue);
        }
    }


    private static AtheneApiMethodResultMapping parseMethodResult(Method m) {
        Type type = m.getGenericReturnType();

        AtheneApiMethodResultMapping atheneApiMethodResultMapping = new AtheneApiMethodResultMapping();
        try {
            AtheneGeneric atheneGeneric = getAtheneGeneric(type);

            Class<?> genericType = Class.forName(atheneGeneric.getFullClassName());
            atheneApiMethodResultMapping.setSuccess(true);
            atheneApiMethodResultMapping.setCode("200");
            atheneApiMethodResultMapping.setMsg("success!");

            Object data = createInstance(genericType);
            if (atheneGeneric.isCollectionGenericType()) {
                List<Object> dataList = new LinkedList<Object>();
                dataList.add(data);
                atheneApiMethodResultMapping.setData(dataList);
            } else {
                atheneApiMethodResultMapping.setData(data);
            }

            Class<?> clazz = m.getReturnType();
        } catch (ClassNotFoundException e) {
            logger.error("class forName error!, {}", e);
            throw new AtheneClientException("class forName error!");
        }
        return atheneApiMethodResultMapping;
    }

    private static Object createInstance (Class<?> clazz) {
        Object obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                    f.setAccessible(true);
                    dynamicSetValue(obj,f);
            }
        } catch (InstantiationException e) {
            logger.error("newInstance error ,e : {}",e);
            throw new AtheneClientException("class forName error!");
        } catch (IllegalAccessException e1) {
            logger.error("newInstance error ,e : {}",e1);
            throw new AtheneClientException("class forName error!");
        }
        return obj;
    }

    private static void dynamicSetValue(Object instance,Field f) {
        ApiField apiField = f.getAnnotation(ApiField.class);
        if (instance != null && f != null && apiField != null) {
            try {
                Class<?> filedType = f.getType();
                if (filedType == boolean.class)
                    f.setBoolean(instance,Boolean.parseBoolean(apiField.defaultValue()));

                else if (filedType == byte.class)
                    f.setByte(instance,Byte.parseByte(apiField.defaultValue()));

                else if (filedType == char.class)
                    f.setChar(instance, apiField.defaultValue().charAt(0));

                else if (filedType == double.class)
                    f.setDouble(instance, Double.parseDouble(apiField.defaultValue()));

                else if (filedType == float.class)
                    f.setFloat(instance, Float.parseFloat(apiField.defaultValue()));

                else if (filedType == int.class)
                    f.setInt(instance, Integer.parseInt(apiField.defaultValue()));

                else if (filedType == long.class)
                    f.setLong(instance, Long.parseLong(apiField.defaultValue()));


                else if (filedType == short.class)
                    f.setShort(instance, Short.parseShort(apiField.defaultValue()));

                else if (filedType == Boolean.class)
                    f.set(instance, Boolean.parseBoolean(apiField.defaultValue()));

                else if (filedType == Byte.class)
                    f.set(instance, Byte.parseByte(apiField.defaultValue()));

                else if (filedType == Character.class)
                    f.set(instance, Character.valueOf(apiField.defaultValue().charAt(0)));

                else if (filedType == Double.class)
                    f.set(instance, Double.parseDouble(apiField.defaultValue()));

                else if (filedType == Float.class)
                    f.set(instance, Float.parseFloat(apiField.defaultValue()));

                else if (filedType == Integer.class)
                    f.set(instance, Integer.parseInt(apiField.defaultValue()));

                else if (filedType == Long.class)
                    f.set(instance, Long.parseLong(apiField.defaultValue()));

                else if (filedType == Short.class)
                    f.set(instance, Short.parseShort(apiField.defaultValue()));

                else if (filedType == BigDecimal.class)
                    f.set(instance, new BigDecimal(apiField.defaultValue()));

                else if (filedType == BigInteger.class)
                    f.set(instance, new BigInteger(apiField.defaultValue()));

                else if (filedType == String.class)
                    f.set(instance, apiField.defaultValue());

                else if (filedType == Date.class)
                    f.set(instance, new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(apiField.defaultValue()));

                else if (filedType == AtomicBoolean.class)
                    f.set(instance, new AtomicBoolean(Boolean.parseBoolean(apiField.defaultValue())));

                else if (filedType == AtomicLong.class)
                    f.set(instance, new AtomicLong(Long.parseLong(apiField.defaultValue())));

                else if (filedType == AtomicInteger.class)
                    f.set(instance, new AtomicInteger(Integer.parseInt(apiField.defaultValue())));

                else {
                    //TODO 自定类型,对象嵌套,递归映射
                    Type type = f.getGenericType();
                    AtheneGeneric atheneGeneric = getAtheneGeneric(type);

                    Object obj = null;
                    if (atheneGeneric.isCollectionGenericType()) {
                        List<Object> objList = new LinkedList<Object>();
                        String fullClassName = atheneGeneric.getFullClassName();
                        filedType = Class.forName(fullClassName);
                        obj = filedType.newInstance();
                        objList.add(obj);
                        f.set(instance,objList);
                    } else {
                        obj = filedType.newInstance();
                        f.set(instance,obj);
                    }

                    Field[] fields = filedType.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        dynamicSetValue(obj,field);
                    }
                }
            } catch (Exception e) {
                logger.error("dynamicSetValue error ! , e {}",e);
                throw new AtheneClientException("dynamicSetValue error!");
            }
        }
    }

    private static AtheneGeneric getAtheneGeneric(Type type) {
        AtheneGeneric atheneGeneric = new AtheneGeneric();;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type t = parameterizedType.getActualTypeArguments()[0];

            t = getActualType(t);
            String fullClassName = t.toString().split("\\s")[1];

            String typeFullName = type.toString();

            boolean isCollectionGenericType = ReflectionUtils.isCollectionGenericType(typeFullName);
            if (isCollectionGenericType) {
                atheneGeneric.setCollectionGenericType(true);
                atheneGeneric.setFullClassName(fullClassName);
            } else {
                atheneGeneric.setCollectionGenericType(false);
                atheneGeneric.setFullClassName(fullClassName);
            }
        } else {
            atheneGeneric.setCollectionGenericType(false);
            atheneGeneric.setFullClassName(type.toString().split("\\s")[1]);
        }
        return atheneGeneric;
    }

    private static Type getActualType(Type type) {
        Type t = type;

        if (!(t instanceof ParameterizedType)) return t;

        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            t = parameterizedType.getActualTypeArguments()[0];

            getActualType(t);
        }
        return t;
    }

    public static void main(String args[]) {

    }

}

class AtheneGeneric {
    private boolean isCollectionGenericType;
    private String fullClassName;

    public boolean isCollectionGenericType() {
        return isCollectionGenericType;
    }

    public void setCollectionGenericType(boolean collectionGenericType) {
        isCollectionGenericType = collectionGenericType;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }
}
