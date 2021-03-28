package com.lp.inter;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * @InterfaceName com.lp.inter.IA
 * @Deacription TODO
 * @Author LP
 * @Date 2021/3/28 20:28
 * @Version 1.0
 **/
@SPI
public interface IA {
    int go();
}
