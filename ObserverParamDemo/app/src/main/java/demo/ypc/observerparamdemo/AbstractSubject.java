package demo.ypc.observerparamdemo;

import java.util.Enumeration;
import java.util.Vector;

/**
 * Created by yinpengcheng on 2017/10/10.
 */

public class AbstractSubject implements Subject {
    private Vector<Observer> vector = new Vector<>();

    @Override
    public void add(Observer observer) {
        vector.add(observer);
    }

    @Override
    public void del(Observer observer) {
        vector.remove(observer);
    }

    @Override
    public void notifyObservers() {
        Enumeration<Observer> enumeration = vector.elements();
        while (enumeration.hasMoreElements()){
            enumeration.nextElement().update();
        }
    }

    @Override
    public void operation() {

    }

}
