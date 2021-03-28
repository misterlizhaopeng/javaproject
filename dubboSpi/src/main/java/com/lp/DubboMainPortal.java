package com.lp;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.lp.inter.IA;

/**
 * @ClassName com.lp.DubboMainPortal
 * @Deacription TODO
 * @Author LP
 * @Date 2021/3/28 20:25
 * @Version 1.0
 **/
public class DubboMainPortal {
    public static void main(String[] args) {

        ExtensionLoader<IA> extensionLoader = ExtensionLoader.getExtensionLoader(IA.class);
        IA ia = extensionLoader.getExtension("ib1");
        System.out.println(ia.getClass().getName());

    }
}

