package nio_test.netty_.codec;

import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.Serializable;

public class C implements Serializable {//
    @Override
    public String toString() {
        return "C{" +
                "messageToByteEncoder=" + messageToByteEncoder +
                ", byteToMessageDecoder=" + byteToMessageDecoder +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public C(){}
    public C(Integer id,String name){this.id=id;this.name=name;}

    MessageToByteEncoder messageToByteEncoder;
    ByteToMessageDecoder byteToMessageDecoder;

    private Integer id;
    private String name;

    public MessageToByteEncoder getMessageToByteEncoder() {
        return messageToByteEncoder;
    }

    public void setMessageToByteEncoder(MessageToByteEncoder messageToByteEncoder) {
        this.messageToByteEncoder = messageToByteEncoder;
    }

    public ByteToMessageDecoder getByteToMessageDecoder() {
        return byteToMessageDecoder;
    }

    public void setByteToMessageDecoder(ByteToMessageDecoder byteToMessageDecoder) {
        this.byteToMessageDecoder = byteToMessageDecoder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
