package list_;

import org.junit.Before;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName list_.SelfListFilter
 * @Deacription TODO
 * @Author LP
 * @Date 2021/3/25 14:12
 * @Version 1.0
 **/
public class SelfListFilter {


    final static List<Obj_1> l1 = new ArrayList<>();
    final static List<Obj_2> l2 = new ArrayList<>();

    static {

        Obj_1 obj_1 = new Obj_1();
        obj_1.setId(1);
        obj_1.setName("z");
        l1.add(obj_1);
        Obj_1 obj_2 = new Obj_1();
        obj_2.setId(2);
        obj_2.setName("h");
        l1.add(obj_2);
        Obj_1 obj_3 = new Obj_1();
        obj_3.setId(3);
        obj_3.setName("z");
        l1.add(obj_3);
        Obj_1 obj_4 = new Obj_1();
        obj_4.setId(4);
        obj_4.setName("y");
        l1.add(obj_4);


        Obj_2 obj1 = new Obj_2();
        obj1.setUid(1);
        ;
        obj1.setuName("u");
        l2.add(obj1);
        Obj_2 obj2 = new Obj_2();
        obj2.setUid(2);
        obj2.setuName("p");
        Obj_2 obj3 = new Obj_2();
        obj3.setUid(3);
        obj3.setuName("y");
        l2.add(obj2);

    }

    @Before
    public void before() {

    }

    @org.junit.Test
    public void test_01() {
        Iterator<Obj_1> iterator = l1.iterator();
        while (iterator.hasNext()) {
            Obj_1 obj_1 = iterator.next();
            System.out.println(obj_1.getId() + ";" + obj_1.getName());
        }
        ListIterator<Obj_1> obj_1ListIterator = l1.listIterator();

    }

    public static void main(String[] args) {


        List<Obj_2> obj2 = l1.stream().map(l1 -> l2.stream()
                .filter(l2 -> Objects.equals(l1.getId(), l2.getUid()))
                .findFirst().map(ll -> {
                    Obj_2 obj_2 = new Obj_2();
                    return obj_2;
                }).orElse(null)
        ).collect(Collectors.toList());

        List<Obj_1> collect = l1.stream().map(l1 -> l2.stream()
                .filter(l2 -> Objects.equals(l1.getId(), l2.getUid()))
                .findFirst().map(ll -> {

                    return new Obj_1();
                }).orElse(null)
        ).collect(Collectors.toList());

        List<Obj_2> collect1 = l1.stream().map(l1 -> l2.stream()
                .filter(l2 -> Objects.equals(l1.getId(), l2.getUid()))
                .findFirst().orElse(null)
        ).collect(Collectors.toList());


        System.out.println(1);
    }
}


class Obj_1 {

    private Integer id;

    private String name;

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

class Obj_2 {
    private Integer uid;
    private String uName;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
