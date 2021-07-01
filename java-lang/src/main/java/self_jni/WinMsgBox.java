package self_jni;

import org.junit.Test;
/**
 * @ClassName self_jni.WinMsgBox
 * @Deacription :
 * @Author LP
 * @Date 2021/6/30
 * @Version 1.0
 **/

/**
 * Step 1：写一个Java类，在这个类中包含了需要调用的本地方法的描述。
 */
public class WinMsgBox {
    static {
        /**
         * (1)中WinMsgDll是动态链接文件的文件名，不用加扩展名，因为在不同的平台下动态链接文件扩展名是不同的，由JVM自动识别，
         * 比如在 Solaris下，会被转换为WinMsgDll.so；而Win32环境下会转换为WinMsgDll.dll。这个文件名必须和Step 4中生成的文件名一致。
         * 这个文件的存放位置也很重要，它只能被放在JVM属性值java.library.path中指定的文件夹中。这个属性值可以使用 System.getProperty("java.library.path");来查看。
         * 一般情况下，至少放在这几个位置是确定可靠的，windows安装目录下的system32下面，JDK安装目录下的bin下面，以及调用主类文件的当前目录。
         */
        System.loadLibrary("WinMsgDll");// (1)
    }

    /**
     * (2)中指明了你必须用本地代码实现的方法。
     * @param str
     */
    public native void showMsgBox(String str);  // (2)

    @Test
    public void test01(){
        new  WinMsgBox().showMsgBox("123");
    }
}

