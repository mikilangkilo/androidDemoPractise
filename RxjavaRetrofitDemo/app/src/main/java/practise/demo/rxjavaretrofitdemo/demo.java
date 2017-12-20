package practise.demo.rxjavaretrofitdemo;

/**
 * Created by yinpengcheng on 2017/12/19.
 */

public class demo {
    public static void main(String[] args){
        int count = 20;
        int countPer10 = count/10;
        int countCom10 = count%10>0?1:0;
        int loopCount = count/10+count%10>0?1:0;
        System.out.println("loopcount = "+loopCount);
        System.out.println("count/10 = "+countPer10);
        System.out.println("count % 10 = "+countCom10);
        System.out.println("count/10+count%10 = " +(countCom10+countPer10));
    }
}
