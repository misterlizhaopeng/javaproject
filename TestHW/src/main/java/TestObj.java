import javax.print.DocFlavor;
import java.util.List;

/**
 * @ClassName PACKAGE_NAME.TestObj
 * @Deacription TODO
 * @Author LP
 * @Date 2021/1/27 15:37
 * @Version 1.0
 **/

public class TestObj {
    private Integer id;
    private String  name;
    private List<TestObj> to;

    public List<TestObj> getTo() {
        return to;
    }

    public void setTo(List<TestObj> to) {
        this.to = to;
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

    @Override
    public String toString() {
        return "TestObj{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", to=" + to +
                '}';
    }
}

