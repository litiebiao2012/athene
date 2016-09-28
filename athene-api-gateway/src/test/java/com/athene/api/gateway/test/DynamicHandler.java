package com.athene.api.gateway.test;

import com.athene.api.gateway.test.impl.UserServiceImpl;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by fe on 16/9/23.
 */
public class DynamicHandler implements InvocationHandler {

    private Object target;

    public DynamicHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("begin......");
        Object result = method.invoke(target,args);
        System.out.println("end......");
        return null;
    }


    public static void main(String args[]) {
        UserServiceImpl userService = new UserServiceImpl();

        UserService proxyService = (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(),
                new Class[]{UserService.class},new DynamicHandler(userService));
        proxyService.queryAllUser();

        createProxyClassFile();

    }

    public static void createProxyClassFile()
    {
        String name = "Proxy";
        byte[] data = ProxyGenerator.generateProxyClass( name, new Class[] { UserService.class } );
        try
        {
            FileOutputStream out = new FileOutputStream( "/Users/Fe/Desktop/" + name + ".class" );
            out.write( data );
            out.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    public static void say(String str) {

    }

}
