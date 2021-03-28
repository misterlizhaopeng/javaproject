package T;

import java.lang.annotation.Annotation;

/**
 * @ClassName T.Tst
 * @Deacription TODO
 * @Author LP
 * @Date 2021/3/10 13:36
 * @Version 1.0
 **/
public class Tst {

    public static void main(String[] args) throws Exception{
        Baseclass baseclass1=new  Extendclass();
        Baseclass baseclass2=new  Baseclass();
        System.out.println(baseclass1.getClass().getSuperclass().getName());
        System.out.println(baseclass2.getClass().getSuperclass().getSimpleName());


        System.out.println(Baseclass.class.getName());
        System.out.println(Baseclass.class.getClassLoader());
        System.out.println(Extendclass.class.getSimpleName());




        System.out.println("------------------------------------------------------>");
        Class<?> clazz= Class.forName(Extendclass.class.getName());
        Annotation[] annotations = clazz.getAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            System.out.println(annotations);
        }


        //url:https://blog.csdn.net/tanga842428/article/details/76473440
        //--> second url:https://www.jianshu.com/p/9be58ee20dee
    }
}

