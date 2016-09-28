package com.athene.api.client.test;

import com.athene.api.client.ClientInfoInitialize;
import org.junit.Test;

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
}
