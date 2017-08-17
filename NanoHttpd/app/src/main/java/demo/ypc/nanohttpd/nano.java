package demo.ypc.nanohttpd;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import android.text.TextUtils;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

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
//        if (!TextUtils.isEmpty(session.getUri()) && !session.getUri().endsWith(".mp4")){
//            return responseHtml(session);
//        }
        return responseFile(session);

    }
    //对于请求文件的，返回下载的文件
    public Response responseFile(IHTTPSession session){
            String uri = session.getUri();
            LogUtils.e(session);
        Response response = null;
        try {
            File file = new File("/mnt/internal_sd/webscreen/mp4/c4.mp4");

            FileInputStream fis = new FileInputStream(file);
            response = newFixedLengthResponse(Status.OK, "video/mp4", fis, fis.available());
//            return newChunkedResponse(Status.OK, "video/mp4", fis);
//            response.addHeader("Accept-Ranges","bytes");
//            response.addHeader("Access-Control-Allow-Origin","*");
            return response;
        }catch (Exception e){
            LogUtils.e(e);
        }
        return response;

    }
    public Response responseHtml(IHTTPSession session){
        StringBuilder builder = new StringBuilder();
        builder.append("<html>\n" +
                "<body>\n" +
                "\n" +
                "<video width=\"320\" height=\"240\" controls=\"controls\">\n" +
                "<source src=\"/mnt/internal_sd/webscreen/mp4/c4.mp4\" type=\"video/mp4\">\n" +
                "    Your browser doestn't support HTML5\n" +
                "</video>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
        return newFixedLengthResponse(builder.toString());
    }

}
