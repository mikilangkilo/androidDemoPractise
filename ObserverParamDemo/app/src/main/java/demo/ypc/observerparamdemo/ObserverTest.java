package demo.ypc.observerparamdemo;

/**
 * Created by yinpengcheng on 2017/10/10.
 */

public class ObserverTest {
    public static void main(String[] args){
        Subject subject = new MySubject();
        subject.add(new Observer1());
        subject.add(new Observer2());
        subject.operation();
    }
}
