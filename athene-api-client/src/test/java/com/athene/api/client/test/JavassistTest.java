package com.athene.api.client.test;

import com.athene.api.client.test.remote.Result;
import com.athene.api.client.test.remote.user.UserService;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by fe on 16/9/20.
 */
public class JavassistTest {
    public static void main(String args[]) throws  Exception {
        ClassPool pool = ClassPool.getDefault();

        CtClass ctClass = pool.makeClass("com.athene.UserQuery");


        CtField userNameField = new CtField(pool.getCtClass("java.lang.String"),"userName",ctClass);
        userNameField.setModifiers(Modifier.PRIVATE);
        ctClass.addField(userNameField);

        ctClass.addMethod(CtNewMethod.getter("getUserName",userNameField));
        ctClass.addMethod(CtNewMethod.setter("setUserName",userNameField));

        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{},ctClass);
        StringBuffer constructBuffer = new StringBuffer();
        constructBuffer.append("{\n");
        constructBuffer.append("userName = \"litiebiao\"; \n");
        constructBuffer.append("}");
        ctConstructor.setBody(constructBuffer.toString());


        ctClass.addConstructor(ctConstructor);

        Class<?> clazz = ctClass.toClass();
//        Object obj = clazz.newInstance();
//        Object result = clazz.getMethod("getUserName",new Class[]{}).invoke(obj,new Object[]{});
//        System.out.println(result);

//        byte[] byteArr = ctClass.toBytecode();
//        FileOutputStream fos = new FileOutputStream(new File("/Users/Fe/Desktop/UserQuery.class"));
//        fos.write(byteArr);
//        fos.close();

//        ctClass.defrost();
//
//        for (int i = 0;i < 999; i++) {
//            CtClass cacheClass = pool.get("com.athene.UserQuery");
//            CtField ageField = new CtField(pool.getCtClass("java.lang.Integer"),"age" + i,cacheClass);
//            ctClass.addField(ageField);
//
//            ctClass.addMethod(CtNewMethod.getter("getAge" + i + "Name",ageField));
//            ctClass.addMethod(CtNewMethod.setter("setAge" + i + "Name",ageField));
//
//        }
//
//        Class<?> clazz01 = ctClass.getClass();
//
//
//        byte[] byteArr = ctClass.toBytecode();
//        FileOutputStream fos = new FileOutputStream(new File("/Users/Fe/Desktop/UserQuery.class"));
//        fos.write(byteArr);
//        fos.close();


        CtClass javassistClass = pool.get("com.athene.api.client.test.JavassistTest");
        CtField ageField = new CtField(pool.getCtClass("java.lang.Integer"),"age",javassistClass);
        javassistClass.addField(ageField);

        javassistClass.addMethod(CtNewMethod.getter("getAgeName",ageField));
        javassistClass.addMethod(CtNewMethod.setter("setAgeName",ageField));

        byte[] byteArr = javassistClass.toBytecode();
        FileOutputStream fos = new FileOutputStream(new File("/Users/Fe/Desktop/JavassistTest.class"));
        fos.write(byteArr);
        fos.close();


        methodInfo();



    }

    public static void methodInfo() {
        Class clazz = Result.class;
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(clazz.getName());
            CtMethod cm = cc.getDeclaredMethod("setMsg");

            // 使用javaassist的反射方法获取方法的参数名
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr == null) {
                // exception
            }
            String[] paramNames = new String[cm.getParameterTypes().length];
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++)
                paramNames[i] = attr.variableName(i + pos);
            // paramNames即参数名
            for (int i = 0; i < paramNames.length; i++) {
                 System.out.println(paramNames[i]);
            }

        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
