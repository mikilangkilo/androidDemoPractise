package practise.demo.rxjavaretrofitdemo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yinpengcheng on 2017/12/19.
 */

public interface GithubInterface {
    @GET("users/{user}/repos")
    Call<List<GitHubRepo>> listRepos(@Path("user") String user);
}
