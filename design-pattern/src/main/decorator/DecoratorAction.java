package decorator;

//装饰模式-装饰者
public class DecoratorAction implements  Component {

    private  Component component;
    public DecoratorAction (Component component){
        this.component=component;
    }

    @Override
    public void sampleOpra() {
        // 如果组件不为空，执行之
        if (component != null)
        {
            component.sampleOpra();
            System.out.println("装饰者");
        }
    }
}
