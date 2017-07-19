package demo.ypc.customlistviewdemo;

/**
 * Created by yinpengcheng on 2017/7/18.
 */

public class stuc {
    private String content;
    private int count;
    public stuc(String content, int count){
        this.content = content;
        this.count = count;
    }
    public void setContent(String content){
        this.content = content;
    }
    public void setCount(int count){
        this.count = count;
    }
    public String getContent(){
        return content;
    }
    public int getCount(){
        return count;
    }
}
