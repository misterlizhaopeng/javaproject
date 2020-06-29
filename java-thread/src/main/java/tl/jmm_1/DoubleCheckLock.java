package tl.jmm_1;

public class DoubleCheckLock {
    private DoubleCheckLock(){}

    public static DoubleCheckLock instance;
    public static DoubleCheckLock getInstance(){
        //
        if (instance==null){
            synchronized (DoubleCheckLock.class){
                if (instance==null)
                {
                    instance=new DoubleCheckLock();
                }
            }
        }
        return instance;


    }
    /*
    述代码一个经典的单例的双重检测的代码，这段代码在单线程环境下并没有什么问题，但如果在多线程环境下就可以出现线程安全问题。
    原因在于某一个线程执行到第一次检测，读取到的instance不为null时，instance的引用对象可能没有完成初始化。
    因为instance = new DoubleCheckLock();可以分为以下3步完成(伪代码):

    memory = allocate();//1.分配对象内存空间
    instance(memory);//2.初始化对象
    instance = memory;//3.设置instance指向刚分配的内存地址，此时instance！=null

    由于步骤1和步骤2间可能会重排序，如下：
    memory=allocate();//1.分配对象内存空间
    instance=memory;//3.设置instance指向刚分配的内存地址，此时instance！=null，但是对象还没有初始化完成！
    instance(memory);//2.初始化对象

    由于步骤2和步骤3不存在数据依赖关系，而且无论重排前还是重排后程序的执行结果在单
    线程中并没有改变，因此这种重排优化是允许的。但是指令重排只会保证串行语义的执行的一
    致性(单线程)，但并不会关心多线程间的语义一致性。所以当一条线程访问instance不为null
    时，由于instance实例未必已初始化完成，也就造成了线程安全问题。那么该如何解决呢，很
    简单，我们使用volatile禁止instance变量被执行指令重排优化即可。

   //禁止指令重排优化
                    private   volatile   static   DoubleCheckLock instance;
     */
}
