package demo.ypc.nanohttpd;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Pattern;

import android.text.TextUtils;
import android.util.Log;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by yinpengcheng on 2017/8/14.
 */

public class nano extends NanoHTTPD {

    public nano(int port){
        super(port);
    }

    @Override
    public void start() throws IOException {
        LogUtils.e("");
        super.start();
    }

    //当接受到连接时会调用此方法
    public Response serve(IHTTPSession session){
        LogUtils.e(session.getUri());
        if (!TextUtils.isEmpty(session.getUri()) && session.getUri().equals("/favicon.ico")){
            LogUtils.e("");
            return null;
        }
        return responseFile(session);
    }
    //对于请求文件的，返回下载的文件
    public Response responseFile(IHTTPSession session){
            String uri = session.getUri();
            LogUtils.e(session);
        Response response = null;
        try {
            FileInputStream fis = new FileInputStream("/mnt/internal_sd/webscreen/mp4/c1.mp4");
            response = newFixedLengthResponse(Status.OK, "video/mp4", fis, fis.available());
        }catch (Exception e){
            LogUtils.e(e);
        }
        return response;

    }

}
