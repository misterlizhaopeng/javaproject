package del;

import java.io.FileInputStream;
import java.lang.reflect.Method;

public class MyClassLoaderTest {

    static class MyClassLoader extends ClassLoader {
        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }

        private byte[] loadByte(String name) throws Exception {
            //name = name.replaceAll("\\.", "/");
            FileInputStream fis = new FileInputStream(classPath + "/" + name
                    + ".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }


        // 重写loadClass，打破双亲委派机制 - 重写类加载方法，实现自己的加载逻辑，不委派给双亲加载；
        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);

            if (c==null){

                // If still not found, then invoke findClass in order
                // to find the class.
                long t1 = System.nanoTime();
                c = findClass(name);

                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }

            if (resolve) {
                resolveClass(c);
            }
            return c;//super.loadClass(name, resolve);
        }

        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name); //defineClass将一个字节数组转为Class对象，这个字节数组是class文件读取后最终的字节数组。
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

    }

    public static void main(String args[]) throws Exception {

        System.out.println("-------------------->yyy");


        MyClassLoader classLoader = new MyClassLoader("D:/del");
        // D:\del\com\tuling\jvm
        Class clazz = classLoader.loadClass("bean.User");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout", null);
        method.invoke(obj, null);
        System.out.println(clazz.getClassLoader().getClass().getName());
    }
}