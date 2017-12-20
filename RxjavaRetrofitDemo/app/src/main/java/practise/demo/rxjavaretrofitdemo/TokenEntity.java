package practise.demo.rxjavaretrofitdemo;

/**
 * Created by yinpengcheng on 2017/12/19.
 */

public class TokenEntity {
    int expire;
    String token;
    String user;

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
