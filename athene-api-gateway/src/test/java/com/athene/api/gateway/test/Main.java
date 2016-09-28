package com.athene.api.gateway.test;


import com.athene.api.client.common.AtheneApiBuildHelper;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by fe on 16/9/9.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        com.alibaba.dubbo.container.Main.main(args);

        //getInfo();

       // getService();
    }

    public static void getService() {
        AtheneApiBuildHelper.buildAtheneApiGroup(UserService.class);
    }

    public static void getInfo() throws Exception  {
        Class<UserService> userServiceClass = UserService.class;



        Method[] methods = userServiceClass.getMethods();
        for (Method m : methods) {
              Type type = m.getGenericReturnType();
              if (type instanceof ParameterizedType) {
                  ParameterizedType pt = (ParameterizedType) type;
                  System.out.println(pt.getActualTypeArguments()[0]);
              }
//            Class<?> returnType = m.getReturnType();
//
//            if (returnType.equals(Result.class)) {
//                 Field[] fields = returnType.getDeclaredFields();
//                 for (Field f : fields) {
//                     System.out.println(f.getType().getName() + " | " + f.getName());
//                     if (f.getName().equals("data")) {
//                         Class<?> clazz = f.getClass();
//                         System.out.println(clazz.getName());
//
//                     }
//                 }
//            }

        }
    }
}
