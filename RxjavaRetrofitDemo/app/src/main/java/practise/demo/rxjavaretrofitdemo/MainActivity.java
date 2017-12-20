package practise.demo.rxjavaretrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apkfuns.logutils.LogUtils;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.observers.Observers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("hello");
//                subscriber.onNext("hi");
//                subscriber.onNext("alohia");
//                subscriber.onCompleted();
//            }
//        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .build();

        GithubInterface service = retrofit.create(GithubInterface.class);
        Call<List<GitHubRepo>> repos = service.listRepos("octocat");
        LogUtils.e(repos);
    }
}
