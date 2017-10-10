package demo.ypc.observerparamdemo;

/**
 * Created by yinpengcheng on 2017/10/10.
 */

public interface Subject {
    void add(Observer observer);
    void del(Observer observer);
    void notifyObservers();
    void operation();
}
