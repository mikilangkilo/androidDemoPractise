package demo.ypc.okhttpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.apkfuns.logutils.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    OkHttpClient client;
    MediaType JSON1;
    String message = null;
    String Path = "http://www.glhospital.com:82/webapinew/api/UnifiedData/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new OkHttpClient();
        JSON1 = MediaType.parse("application/json; charset=utf-8");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    message = get("http://10.10.20.250/rest/v1/drug?s=ELIXIR");
//                    LogUtils.e(message);
//                    String jsonString = "{'DoctorId':'','DeptCode':''}";
//                    String jsonString = "{'DoctorId':'00000000000249','DeptCode':'11'}";
                    String jsonString = "{\"DoctorId\":\"\",\"DeptCode\":\"\"}";
                    message = post("http://www.glhospital.com:82/webapinew/api/UnifiedData/GetExpertInfo",jsonString);//获取医生数据
//                    LogUtils.e(message);
                    String jsonString1 = "{\"DeptCode\":\"\"}";
//                    message = post(Path+"GetDeptL2Info",jsonString1);//门诊信息
                    String jsonString2 = "{\"NurseId\":\"\",\"DeptCode\":\"\"}";
//                    message = post(Path+"GetNurseInfo",jsonString2);//护士信息有误
//                    message = post(Path+"GetHospitalInfo","");//医院的信息
//                    DoctorList doctorList = JSON.parseObject(message, demo.ypc.okhttpdemo.DoctorList.class);
//                    LogUtils.e(doctorList);
//                    List<doctor> list = new ArrayList<doctor>(JSON.parseArray(message, doctor.class));
//                    LogUtils.e(list);
                    JSONObject jsonObject = JSON.parseObject(message);
//                    LogUtils.e("result: "+jsonObject.get("result"));
                    JSONArray array = (JSONArray)JSON.parse(jsonObject.get("result").toString());
//                    LogUtils.e(message);
                    List<doctor> doctorList = array.toJavaList(doctor.class);
                    LogUtils.e(doctorList);
                }catch (Exception e){
                    LogUtils.e(e);
                }
            }
        }).start();
        LogUtils.e(message);
    }
    private String get(String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    private String post(String url, String json) throws IOException{
        RequestBody body = RequestBody.create(JSON1, json);
//        LogUtils.e(body);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    private String post(String url)throws IOException{
        RequestBody body = RequestBody.create(JSON1, "");
        LogUtils.e(body);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().toString();
    }
}
