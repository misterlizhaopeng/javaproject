package bean;

public class User {
    private Integer id;
    private String name;


    public User(){}
    public User(Integer id,String name){
        this.id=id;
        this.name=name;
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


    public void sout(){
        System.out.println("-------------自定义加载器执行的方法！");
    }


    //User类需要重写finalize方法
    @Override
    protected void finalize() throws Throwable {
        //OOMTest.list.add(this);
        System.out.println("关闭资源，userid=" + id + "即将被回收");
    }
}
