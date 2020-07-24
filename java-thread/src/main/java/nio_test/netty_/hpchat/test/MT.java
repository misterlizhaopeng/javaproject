package nio_test.netty_.hpchat.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MT {
    private static final Logger LOGGER= LoggerFactory.getLogger(MT.class);
    public static void main(String[] args) throws Exception{


        Thread.sleep(5000);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            LOGGER.warn("jvm 即将关闭");
        }));

    }
}
