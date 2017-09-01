package demo.ypc.okhttpdemo;

import java.util.ArrayList;

/**
 * Created by yinpengcheng on 2017/9/1.
 */

public class DoctorList {
    ArrayList<doctor> doctors;
    int code;
    String reason;
    String stackTrace;
    String redirect;
    User user;

    public ArrayList<doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<doctor> doctors) {
        this.doctors = doctors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
