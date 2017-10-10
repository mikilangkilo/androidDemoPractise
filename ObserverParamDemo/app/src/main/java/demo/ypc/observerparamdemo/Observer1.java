package demo.ypc.observerparamdemo;

/**
 * Created by yinpengcheng on 2017/10/10.
 */

public class Observer1 implements Observer {
    @Override
    public void update() {
        System.out.println("observer1 update!");
    }
}
