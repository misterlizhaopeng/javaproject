package decorator;

//装饰功能B
public class DecoratorActionB extends DecoratorAction {
    private Component component;

    public DecoratorActionB(Component component) {
        super(component);
        this.component = component;
    }

    @Override
    public void sampleOpra() {
        this.component.sampleOpra();
        System.out.println("装饰功能B");
    }
}
