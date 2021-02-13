import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName PACKAGE_NAME.DeadCycle
 * @Deacription TODO
 * @Author LP
 * @Date 2021/2/2 18:20
 * @Version 1.0
 **/
public class DeadCycle {


    public static void main(String[] args) throws Exception {
        List<ObjSelf> list = new LinkedList<>();
        int cou = 0;
        for (; ; ) {
            Thread.sleep(10);
            System.out.println("going..........");
            ObjSelf objSelf = new ObjSelf();
            objSelf.setId(cou);
            list.add(objSelf);
        }


    }
}


class ObjSelf {
    private Integer id;
    private String name;
    private Date birthDay;

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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
}

