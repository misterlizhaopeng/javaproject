package nio_test.netty_.codec;

import java.io.Serializable;
import java.util.Date;

public class MsgContent<T> implements Serializable {
    private Integer id;
    private T content;
    private Date sendDate;
    private Date createDate;

    @Override
    public String toString() {
        return "MsgContent{" +
                "id=" + id +
                ", content=" + content +
                ", sendDate=" + sendDate +
                ", createDate=" + createDate +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
