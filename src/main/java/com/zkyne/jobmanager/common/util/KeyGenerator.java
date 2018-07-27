package com.zkyne.jobmanager.common.util;

import java.util.UUID;

/**
 * @ClassName: KeyGenerator
 * @Description:
 * @Author: zkyne
 * @Date: 2018/4/13 11:09
 */
public class KeyGenerator {
    private KeyGenerator(){}

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString();
        key = key.replaceAll("-", "");
        return key;
    }
}
