package nio_test.netty_.hpchat.lpprotocol;

import java.io.Serializable;

public class UserTag implements Serializable {
    private Integer id;
    //0表示认证（认证的时候把当前的channel添加到缓存中）；1表示发送
    private short tk;
    //objectNumber 发送对象，就是给谁发送
    private String obj;
    //发送内容
    private MsgContent content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getTk() {
        return tk;
    }

    public void setTk(short tk) {
        this.tk = tk;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public MsgContent getContent() {
        return content;
    }

    public void setContent(MsgContent content) {
        this.content = content;
    }
}
