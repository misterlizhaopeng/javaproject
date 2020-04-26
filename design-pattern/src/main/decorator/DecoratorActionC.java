package decorator;

//装饰功能B
public class DecoratorActionC extends DecoratorAction {
    private Component component;

    public DecoratorActionC(Component component) {
        super(component);
        this.component = component;
    }

    @Override
    public void sampleOpra() {
        this.component.sampleOpra();
        System.out.println("装饰功能C");
    }
}
