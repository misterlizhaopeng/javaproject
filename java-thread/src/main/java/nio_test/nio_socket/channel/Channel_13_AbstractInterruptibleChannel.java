package nio_test.nio_socket.channel;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @ClassName nio_test.nio_socket.channel.Channel_13_Ab
 * @Deacription : 这里（书中是本章）主要介绍FileChannel类，但是FileChannel类的父类是 java.nio.channels.spi.AbstractInterruptibleChannel ;
 *                  该类的主要作用：给通道提供了一个可以中断功能的基本实现；此类封装了能使通道实现异步关闭和中断所需的最低级别的机制；在调用有可能无限期限阻塞的 IO 操作的之前和之后，通道类必须分别调用begin和end方法，为了确保end 始终被调用，应该把end 方法放到finally中；
 *                  end方法的参数为是否 io 操作 完成；
 *
 *                  FileChannel类没有打开现有文件或者创建新文件的方法，对于操作文件，一般用 FileInputStream ，FileOutputStream ， RandomAccessFile；
 *                  分别进行读通道，写通道，可读可写通道；
 *
 *
 *
 *
 *
 * @Author LP
 * @Date 2021/6/23
 * @Version 1.0
 **/
public class Channel_13_AbstractInterruptibleChannel {
    @Test
    public void test_01() throws IOException {
        System.out.println(159);
        FileChannel fileChannel ;
//        RandomAccessFile randomAccessFile = new RandomAccessFile(null,null);
//        FileChannel channel = randomAccessFile.getChannel();
//
//        FileOutputStream fileOutputStream= new FileOutputStream(null);
//        FileChannel channel1 = fileOutputStream.getChannel();
//
//        FileInputStream fileInputStream = new FileInputStream(null);
//        FileChannel channel2 = fileInputStream.getChannel();



    }
}

