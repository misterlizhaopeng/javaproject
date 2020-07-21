package nio_test.netty_.base;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyByteBuf {
    public static void main(String[] args) {
        // 创建byteBuf对象，该对象内部包含一个字节数组byte[10]
        // 通过readerindex和writerIndex和capacity，将buffer分成三个区域
        // 已经读取的区域：[0,readerindex)
        // 可读取的区域：[readerindex,writerIndex)
        // 可写的区域: [writerIndex,capacity)
        ByteBuf byteBuf = Unpooled.buffer(10);
        System.out.println("byteBuf:" + byteBuf);

        for (int i = 0; i < 5; i++) {
            System.out.println(byteBuf.getByte(i));
        }
        System.out.println("byteBuf:" + byteBuf);
        //byteBuf:UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 8, cap: 10)
        // 表示：writerIndex为写到了第八位
        for (int i = 0; i < 8; i++) {
            byteBuf.writeByte(i);
        }
        System.out.println("byteBuf:" + byteBuf);

        // 表示：从0位遍历到第八位这样的一个数组，通过方法：getByte(i)，不会改变读索引 readerindex 的位置
        for (int i = 0; i < 8; i++) {
            System.out.println(byteBuf.getByte(i));
        }
        System.out.println("byteBuf=" + byteBuf);

        // 表示：从0位遍历到第八位这样的一个数组，通过方法：readByte()，会改变读索引 readerindex 的位置
        for (int i = 0; i < 8; i++) {
            System.out.println(byteBuf.readByte());
        }
        System.out.println("byteBuf=" + byteBuf);

        System.out.println("----------------------------------------->");
        //用Unpooled工具类创建ByteBuf
        //utf汉字编码下，每个汉字占用 3 个字节；所以字符串：zhangsan 的字节个数为24个，放到ByteBuf中，capacity的值为24；
        ByteBuf byteBuf2 = Unpooled.copiedBuffer("zhangsan1", CharsetUtil.UTF_8);
        //ByteBuf byteBuf2 = Unpooled.copiedBuffer("zhangsan", CharsetUtil.US_ASCII);
        if (byteBuf2.hasArray()) {
            byte[] content = byteBuf2.array();
            //将 content 转成字符串
            System.out.println(new String(content, CharsetUtil.UTF_8));
            // byteBuf2 的容量和CharsetUtil.UTF_8存在关系
            System.out.println("byteBuf=" + byteBuf2);

            //byteBuf的可读位置；
            System.out.println(byteBuf2.readerIndex()); // 0
            //byteBuf的可写位置；
            System.out.println(byteBuf2.writerIndex()); // 8
            //byteBuf的容量；
            System.out.println(byteBuf2.capacity()); // 24

            System.out.println(byteBuf2.getByte(0));// 获取数组0这个位置的字符z的ascii码，h=122

            int len = byteBuf2.readableBytes(); //可读的字节数  8
            System.out.println("len=" + len);


            //使用for取出各个字节
            for (int i = 0; i < len; i++) {
                System.out.println((char) byteBuf2.getByte(i));
            }
            //范围读取
            System.out.println(byteBuf2.getCharSequence(0, 6, CharsetUtil.UTF_8));
            System.out.println(byteBuf2.getCharSequence(6, 2, CharsetUtil.UTF_8));

        }


    }
}
