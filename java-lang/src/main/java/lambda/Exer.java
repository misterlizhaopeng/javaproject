package lambda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntBinaryOperator;
import java.util.function.IntSupplier;

public class Exer {


//    Consumer<String> stringConsumer = (String s) -> System.out.println(s);


    public static void main(String[] args) {
//        IntSupplier doubleSupplier = () -> 5;
//        int asInt = doubleSupplier.getAsInt();
//        //System.out.println(asInt);
//
//
//        IntBinaryOperator intBinaryOperator = (int x, int y) -> x + y;
//        int i = intBinaryOperator.applyAsInt(4, 5);
//        System.out.println(i);






        /*start-> go：
         *  Lambda 表达式，也可称为闭包，Lambda 允许把函数作为一个方法的参数传递到方法中；
         *
         * lambda的核心语法：
         * (parameters) -> expression
         * (parameters) -> {statements;}
         *
         *
         * lambda表达式的重要特征(在我印象中，下面的四个特征和c#语言里面 lambda 完全一致):
         *      可选类型声明：不需要声明参数类型，编译器可以统一识别参数值。
         *      可选的参数圆括号：一个参数无需定义圆括号，但多个参数需要定义圆括号。
         *      可选的大括号：如果主体包含了一个语句，就不需要使用大括号。
         *      可选的返回关键字：如果主体只有一个表达式返回值则编译器会自动返回值，大括号需要指定明表达式返回了一个数值。
         *
         *
         * */


        //1. List 的lambda应用
        String[] strarr = {"aa", "bb", "cc", "dd"};
        System.out.println("--------------------list-旧方式");
        for (String v : strarr) {
            System.out.println(v);
        }
        System.out.println("--------------------list-旧方式");
        List<String> list = Arrays.asList(strarr);
        System.out.println("--------------------list-lambda-start");
        list.forEach(a -> {
            System.out.println(a);
        });
        System.out.println("--------------------list-lambda-end");


        //2.java 8
        System.out.println("--------------------list-java8-start");
        list.forEach(System.out::println);
        System.out.println("--------------------list-java8-end");


        //3.接口或者说面对匿名类lambda的应用
        //个人理解：接口方法规定输入输出参数，具体实现由lambda实现;
        System.out.println("--------------------list-interface-start");
        Calc c1 = (int a, int b) -> a + b;//对接口实现了加法
        Calc c2 = (a, b) -> a - b;//对接口实现了减法，传入参数的类型可以不用定义，因为编译器可以推断出来
        Calc c3 = (a, b) -> a * b;
        System.out.println(oper(1, 2, c1));
        System.out.println(oper(1, 2, c2));
        System.out.println(c1.oper(1, 3));//这种写法好理解一些
        System.out.println(c3.oper(4, 5));//这种写法好理解一些
        SayHello sayHello = (String msg) -> "hello:" + msg;
        String m="一个自定义变量";
        SayHello sy2 = mm -> "null:" + m;//一个输入参数，括号可以省略，注意，lambda中的变量不能喝上下文中的变量重名，这个应该很简单就能注意到；
        System.out.println(sayHello.toSay("李朋"));
        System.out.println(sy2.toSay("go"));
        System.out.println("--------------------list-interface-start");


        //4.Lambda 允许把函数作为一个方法的参数传递到方法中;
        // 使用lambda规则：当方法的参数是一个函数式接口时，可以使用Lambda表达式进行简化
        //      函数式接口的概念：函数式接口(Functional Interface)就是一个有且仅有一个【抽象方法】的接口，abstract可以不写，因为编译器会自动加上；
        //
        //      @FunctionalInterface注解：
        //      一旦使用该注解来定义接口，编译器将会强制检查该接口是否确实有且仅有一个抽象方法，否则将会报错。需要注意的是，即使不使用该注解，
        //      只要满足函数式接口的定义，这仍然是一个函数式接口，使用起来都一样。

        System.out.println("--------------------thread-runnable-start");
        System.out.println("主线程：name=" + Thread.currentThread().getName() + ",thread id=" + Thread.currentThread().getId());
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println("子线程：name=" + Thread.currentThread().getName() + ",thread id=" + Thread.currentThread().getId());
            }).start();
        }
        System.out.println("--------------------thread-runnable-end");

    }

    private static int oper(int a, int b, Calc calc) {
        return calc.oper(a, b);
    }
}
