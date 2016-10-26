package com.athene.api.client.test;

import com.athene.api.client.test.remote.Result;
import com.athene.api.client.test.remote.user.UserService;
import com.athene.api.client.test.remote.user.impl.UserServiceImpl;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.Modifier;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

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


    }

    public static int getChangeWay(int total,int[] numArray) {
        int way = 0;


        return  way;
    }

    @Test
    public  void methodInfo() {
        Class clazz = UserService.class;
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(clazz.getName());
            CtMethod cm = cc.getDeclaredMethod("queryById");

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

    @Test
    public void testJdt() {
        String fileName = "/Users/fe/coding/workspace/sourcecode-workspace/athene/athene-api-client/src/test/java/com/athene/api/client/test/remote/user/UserService.java"; //java源文件
        try {
            File file = new File(fileName);
            byte[] b = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(b);
            String content = new String(b,"UTF-8");


            //创建解析器
            ASTParser parsert = ASTParser.newParser(AST.JLS3);
            //设定解析器的源代码字符
            parsert.setSource(content.toCharArray());
            //使用解析器进行解析并返回AST上下文结果(CompilationUnit为根节点)
            CompilationUnit result = (CompilationUnit) parsert.createAST(null);

            //获取类型
            List types = result.types();
            //取得类型声明
            TypeDeclaration typeDec = (TypeDeclaration) types.get(0);


            NormalAnnotation apiGroupNormalAnnotation = null;
            List<Object> typeList = typeDec.modifiers();
            for (Object obj : typeList) {
                if (obj instanceof  NormalAnnotation) {
                    NormalAnnotation normalAnnotation = (NormalAnnotation)obj;
                    if (normalAnnotation.getTypeName().getFullyQualifiedName().equals("ApiGroup")) {
                        apiGroupNormalAnnotation = normalAnnotation;
                        break;
                    }
                }
            }

            if (apiGroupNormalAnnotation != null) {
                List<MemberValuePair> memberValuePairList = apiGroupNormalAnnotation.values();
                for (MemberValuePair memberValuePair : memberValuePairList) {
                    System.out.println(memberValuePair.getName() + " | " + memberValuePair.getValue());
                }
            }
            //##############获取源代码结构信息#################
            //引用import
            List importList = result.imports();
            //取得包名
            PackageDeclaration packetDec = result.getPackage();
            //取得类名
            String className = typeDec.getName().getFullyQualifiedName();
            //取得函数(Method)声明列表
            MethodDeclaration methodDec[] = typeDec.getMethods();
            //取得函数(Field)声明列表
            FieldDeclaration fieldDec[] = typeDec.getFields();

            //输出包名
            System.out.println("包:");
            System.out.println(packetDec.getName().getFullyQualifiedName());
            //输出引用import
            System.out.println("引用import:");
            for (Object obj : importList) {
                ImportDeclaration importDec = (ImportDeclaration) obj;
                System.out.println(importDec.getName());
            }
            //输出类名
            System.out.println("类:");
            System.out.println(className);
            //循环输出函数名称
            System.out.println("========================");
            System.out.println("函数:");
            for (MethodDeclaration method : methodDec) {
         /* System.out.println(method.getName());
          System.out.println("body:");
          System.out.println(method.getBody());
          System.out.println("Javadoc:" + method.getJavadoc());

          System.out.println("Body:" + method.getBody());

          System.out.println("ReturnType:" + method.getReturnType());*/
                System.out.println("=============");
                System.out.println(method);
                System.out.println("methodName : " + method.getName().getFullyQualifiedName() + " | methodReturnType : " + method.getReturnType2() );
                List<SingleVariableDeclaration> parameterList = method.parameters();
                for (SingleVariableDeclaration singleVariableDeclaration : parameterList) {
                    NormalAnnotation apiParamNormalAnnotation = null;
                    List<Object> paramList = singleVariableDeclaration.modifiers();
                    for (Object obj : paramList) {
                        if (obj instanceof  NormalAnnotation) {
                            NormalAnnotation normalAnnotation = (NormalAnnotation)obj;
                            if (normalAnnotation.getTypeName().getFullyQualifiedName().equals("ApiParam")) {
                                apiParamNormalAnnotation = normalAnnotation;
                                break;
                            }
                        }
                    }

                    if (apiParamNormalAnnotation != null) {
                        List<MemberValuePair> memberValuePairList = apiParamNormalAnnotation.values();
                        for (MemberValuePair memberValuePair : memberValuePairList) {
                            System.out.println(memberValuePair.getName() + " | " + memberValuePair.getValue());
                        }
                    }
                    System.out.println("paramName : " + singleVariableDeclaration.getName().getFullyQualifiedName());
                    System.out.println("paramType : " + singleVariableDeclaration.getType());


                    System.out.println("################");

                }
            }

            //循环输出变量
//            System.out.println("变量:");
//            for (FieldDeclaration fieldDecEle : fieldDec) {
//                //public static
//                for (Object modifiObj : fieldDecEle.modifiers()) {
//                    Modifier modify = (Modifier) modifiObj;
//                    System.out.print(modify + "-");
//                }
//                System.out.println(fieldDecEle.getType());
//                for (Object obj : fieldDecEle.fragments()) {
//                    VariableDeclarationFragment frag = (VariableDeclarationFragment) obj;
//                    System.out.println("[FIELD_NAME:]" + frag.getName());
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

}
