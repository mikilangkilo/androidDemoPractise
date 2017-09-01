package demo.ypc.okhttpdemo;

/**
 * Created by yinpengcheng on 2017/9/1.
 */

public class User {
    String AuthenticationType;
    boolean IsAuthenticated;
    String Name;

    public String getAuthenticationType() {
        return AuthenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        AuthenticationType = authenticationType;
    }

    public boolean isAuthenticated() {
        return IsAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        IsAuthenticated = authenticated;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
