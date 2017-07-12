package demo.ypc.databindingdemo;

/**
 * Created by yinpengcheng on 2017/7/12.
 */

public class User {
    public final String firstName;
    public final String lastName;
    public final boolean isAdult;
    public User(String firstName, String lastName, boolean isAdult){
        this.firstName = firstName;
        this.lastName = lastName;
        this.isAdult = isAdult;
    }
}
