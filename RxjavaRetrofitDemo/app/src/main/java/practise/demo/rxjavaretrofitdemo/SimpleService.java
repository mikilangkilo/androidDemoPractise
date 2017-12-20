package practise.demo.rxjavaretrofitdemo;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by yinpengcheng on 2017/12/19.
 */

public class SimpleService {
    public static final String API_URL = "https://api.github.com";
    public static final String TOKEN_URL = "http://zebra.test.highwho.com";
    public static class Contributor {
        public final String login;
        public final int contributions;

        public Contributor(String login, int contributions) {
            this.login = login;
            this.contributions = contributions;
        }
    }
    public static class Token{
        public final String token;
        public Token(String token){
            this.token = token;
        }
    }
    public static class Password{
        public final String username;
        public final String password;
        public Password(String username, String password){
            this.username = username;
            this.password = password;
        }
    }
    public interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);
    }
    public interface TokenInterface{
        @POST("/wise/rest/v1/token")
        Call<List<Token>> createTokens(@Body Password password);
        @POST("/wise/rest/v1/token")
        Call<Token> createToken(@Body Password password);
    }
    public static void main(String... args) throws IOException {
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Retrofit retrofit1 = new Retrofit.Builder()
                .baseUrl(TOKEN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);
        TokenInterface tokenInterface = retrofit1.create(TokenInterface.class);
        // Create a call instance for looking up Retrofit contributors.
        Call<List<Contributor>> call = github.contributors("square", "retrofit");
        Call<Token> mToken = tokenInterface.createToken(new Password("xuyaozuo","123"));
        // Fetch and print a list of the contributors to the library.
        List<Contributor> contributors = call.execute().body();
        Token oneToken = mToken.execute().body();
        System.out.print(oneToken.token+"");
//        for (Contributor contributor : contributors) {
//            System.out.println(contributor.login + " (" + contributor.contributions + ")");
//        }
    }

}
