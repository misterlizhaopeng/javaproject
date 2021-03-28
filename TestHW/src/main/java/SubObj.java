/**
 * @ClassName PACKAGE_NAME.SumObj
 * @Deacription TODO
 * @Author LP
 * @Date 2021/3/2 21:51
 * @Version 1.0
 **/
public class SubObj extends ParentObj {

    public SubObj(){
        System.out.println(obj());
        System.out.println(this.obj());
        System.out.println(super.obj());
    }


    public String obj(){
        return "SumObj";
    }

}

