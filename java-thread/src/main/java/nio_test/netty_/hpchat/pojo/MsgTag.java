package nio_test.netty_.hpchat.pojo;

import java.io.Serializable;

public class MsgTag extends User implements Serializable {

    /* 1 表示认证（认证的时候把当前的channel添加到缓存中）；2 表示发送*/
    private Integer tk;

    /* 昵称*/
    private String nick;
    /* to昵称*/
    private String toNick;

    public String getToNick() {
        return toNick;
    }

    public void setToNick(String toNick) {
        this.toNick = toNick;
    }

    /* 消息内容*/
    private String chatMessage;
    //总人数
    private Long counts;

    public Long getCounts() {
        return counts;
    }

    public void setCounts(Long counts) {
        this.counts = counts;
    }

    public Integer getTk() {
        return tk;
    }

    public void setTk(Integer tk) {
        this.tk = tk;
    }
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }
}
