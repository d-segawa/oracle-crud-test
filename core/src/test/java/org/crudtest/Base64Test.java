package org.crudtest;

import java.util.Base64;

import org.junit.jupiter.api.Test;

public class Base64Test {

    @Test
    void test() {
        String msg = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        byte[] res = Base64.getEncoder().encode(msg.getBytes());
        String r = new String(res);
        System.out.println(r);
    }
}
