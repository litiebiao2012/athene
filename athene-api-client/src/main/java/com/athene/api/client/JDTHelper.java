package com.athene.api.client;

import com.athene.api.client.exception.AtheneClientException;
import com.athene.api.client.mapping.AtheneApiGroupMapping;
import com.athene.api.common.StringUtils;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fe on 16/10/26.
 */
public class JDTHelper {

    public static final Logger logger = LoggerFactory.getLogger(JDTHelper.class);

    //创建解析器
    public static ASTParser parsert = ASTParser.newParser(AST.JLS3);


    public static Map<String,String> buildImportClassMapping(List<ImportDeclaration> importList) {
        Map<String,String> importMap = new HashMap<String,String>();
        for (ImportDeclaration importDec : importList) {
            String fullImportName = importDec.getName().getFullyQualifiedName();
            String simpleName = fullImportName.substring(fullImportName.lastIndexOf("."),fullImportName.length() - 1);
            importMap.put(simpleName,fullImportName);
        }
        return importMap;
    }

    public static AtheneApiGroupMapping buildApiGroup(TypeDeclaration typeDec) {
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

        AtheneApiGroupMapping apiGroup = null;
        if (apiGroupNormalAnnotation != null) {
            apiGroup = new AtheneApiGroupMapping();
            List<MemberValuePair> memberValuePairList = apiGroupNormalAnnotation.values();
            for (MemberValuePair memberValuePair : memberValuePairList) {
                   String name = memberValuePair.getName().getFullyQualifiedName();

                   if (name.equals("description")) {
                       StringLiteral stringLiteral = (StringLiteral) memberValuePair.getValue();
                       String value = stringLiteral.getLiteralValue();
                       apiGroup.setDescription(value);
                   }

                   if (name.equals("owner")) {
                       StringLiteral stringLiteral = (StringLiteral) memberValuePair.getValue();
                       String value = stringLiteral.getLiteralValue();
                       apiGroup.setOwner(value);
                   }

                   if (name.equals("needAuth")) {
                       BooleanLiteral booleanLiteral = (BooleanLiteral) memberValuePair.getValue();
                       boolean value = booleanLiteral.booleanValue();
                       apiGroup.setNeedAuth(value);
                   }

                   if (name.equals("version")) {
                       StringLiteral stringLiteral = (StringLiteral) memberValuePair.getValue();
                       String value = stringLiteral.getLiteralValue();
                       apiGroup.setVersion(value);
                   }

                   if (name.equals("group")) {
                       StringLiteral stringLiteral = (StringLiteral) memberValuePair.getValue();
                       String value = stringLiteral.getLiteralValue();
                       apiGroup.setGroup(value);
                   }

            }
        }
        return apiGroup;
    }


    public static void analysisJavaSourceFile(String fullJavaSourceFilePath) {
        if (StringUtils.isEmpty(fullJavaSourceFilePath)) throw  new AtheneClientException("fullJavaSourceFilePath can not be null!");

        try {
            File file = new File(fullJavaSourceFilePath);

            if (!file.exists() || file.isDirectory()) throw  new AtheneClientException("path[  "+ fullJavaSourceFilePath + "]  not exits or is not file!");

            byte[] b = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(b);
            String content = new String(b,"UTF-8");

            //设定解析器的源代码字符
            parsert.setSource(content.toCharArray());
            //使用解析器进行解析并返回AST上下文结果(CompilationUnit为根节点)
            CompilationUnit result = (CompilationUnit) parsert.createAST(null);


            //获取类型
            List types = result.types();
            //取得类型声明
            TypeDeclaration typeDec = (TypeDeclaration) types.get(0);


            List importList = result.imports();


            MethodDeclaration methodDec[] = typeDec.getMethods();
        } catch (Exception e) {

        }
    }


}
