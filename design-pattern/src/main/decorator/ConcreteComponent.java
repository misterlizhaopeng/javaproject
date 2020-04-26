package decorator;

//具体的组件
public class ConcreteComponent implements  Component {
    @Override
    public void sampleOpra() {
        System.out.println("具体的组件");
    }
}
