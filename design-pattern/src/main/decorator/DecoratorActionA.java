package decorator;

//装饰功能A
public class DecoratorActionA extends DecoratorAction {
    private Component component;

    public DecoratorActionA(Component component) {
        super(component);
        this.component = component;
    }

    @Override
    public void sampleOpra() {
        this.component.sampleOpra();
        System.out.println("装饰功能A");
    }
}
