package com.lp.cn;

import com.lp.interface_.Car;

import java.util.ServiceLoader;

/**
 * @ClassName com.lp.cn.JavaSPIPort
 * @Deacription TODO
 * @Author LP
 * @Date 2021/3/28 14:55
 * @Version 1.0
 **/
public class JavaSPIPort {
    public static void main(String[] args) {
        ServiceLoader<Car> cars = ServiceLoader.load(Car.class);
        for (Car car:cars){
            System.out.println(car.toString());
        }
    }
}

