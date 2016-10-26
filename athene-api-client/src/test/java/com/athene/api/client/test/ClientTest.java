package com.athene.api.client.test;

import com.athene.api.client.ClientInfoInitialize;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fe on 16/9/20.
 */
public class ClientTest {

    @Test
    public void testInit() {
        try {
            Class<?> clazz = Class.forName("com.athene.api.client.ClientInfoInitialize");
        } catch (Exception e) {

        }

    }

    @Test
    public void test01() {
        String path = "/Users/Fe/coding";
        System.out.println(path.lastIndexOf("/"));
    }



    @Test
    public void test02() {

    }
}
