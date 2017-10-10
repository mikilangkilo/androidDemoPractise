package demo.ypc.observerparamdemo;

/**
 * Created by yinpengcheng on 2017/10/10.
 */

public class MySubject extends AbstractSubject {
    @Override
    public void add(Observer observer) {
        super.add(observer);
    }

    @Override
    public void del(Observer observer) {
        super.del(observer);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public void operation() {
        super.operation();
        System.out.println("update self!");
        notifyObservers();
    }
}
