package com.lp.tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName com.lp.tools.LocalRegister
 * @Deacription TODO
 * @Author LP
 * @Date 2021/4/5 18:58
 * @Version 1.0
 **/
public class LocalRegister {
    private final static Map<String, Class> REGISTER = new HashMap<>();

    public static void register(String interfaceName,Class interfaceImpl) {
        REGISTER.put(interfaceName,interfaceImpl);
    }
    public static Class get(String interfaceName){
        return REGISTER.get(interfaceName);
    }
}

