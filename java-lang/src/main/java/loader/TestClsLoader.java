package loader;

import java.util.Arrays;
import java.util.List;

public class TestClsLoader {
    public static void main(String[] args) {
        System.out.println("".getClass().getClassLoader());


        System.out.println(User.class.getClassLoader());
        System.out.println(User.class.getClassLoader().getParent());
        System.out.println(User.class.getClassLoader().getParent().getParent());

        System.out.println("---------------------------------------------------------");
        User user = new User();
        UserImpl user1 = new UserImpl();
        System.out.println(user1.getClass().getName() + ";" + user1.getClass().isInterface());
        printStrByArr(user1.getClass().getInterfaces());

//        System.out.println(user.getClass().getName() + ";" + getStrByArr(user.getClass().getInterfaces()) + ";" + user.getClass().isInterface());
//        System.out.println(IUser.class.getName() + ";" + getStrByArr(IUser.class.getInterfaces()) + ";" + IUser.class.isInterface());

    }


    private static void printStrByArr(Class<?>[] arr) {
        List<Class<?>> classes = Arrays.asList(arr);
        classes.forEach(a -> {
            System.out.println(a);
        });
    }
}
