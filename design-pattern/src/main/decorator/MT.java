package decorator;

public class MT {
    public static void main(String[] args) {

        Component component = new ConcreteComponent();// 被装饰者
        Component a = new DecoratorActionA(component);
        Component b = new DecoratorActionB(a);
        Component c = new DecoratorActionC(b);



        c.sampleOpra();


    }
}
