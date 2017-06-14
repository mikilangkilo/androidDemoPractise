package android.com.java.sampleforgithub;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/14.
 */
public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String > {
    private TextView textView;
    private ProgressBar progressBar;
    public ProgressBarAsyncTask(TextView textView, ProgressBar progressBar){
        this.textView = textView;
        this.progressBar = progressBar;
    }

    @Override
    protected String doInBackground(Integer... params) {//在子线程进行耗时操作
        NetOperator netOperator = new NetOperator();
        int i = 0;
        for (i = 10; i <= 100;i +=10){
            netOperator.operator();
            publishProgress(i);//该方法回调onProgressUpdate
        }
        return i+params[0].intValue()+"";
    }

    @Override
    protected void onPreExecute() {
        textView.setText("开始执行异步线程");//此处更新UI，走的是主线程，是最开始的时候执行的
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int value = values[0];
        progressBar.setProgress(value);//根据耗时操作的结果进行主线程UI的更新
    }
}
