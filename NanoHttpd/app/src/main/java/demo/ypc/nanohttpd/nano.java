package demo.ypc.nanohttpd;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import android.util.Log;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by yinpengcheng on 2017/8/14.
 */

public class nano extends NanoHTTPD {
    public static final int DEFAULT_SERVER_PORT = 8080;
    private static final String REQUEST_ROOT = "/";

    private String mVideoFilePath;
    private int mVideoWidth  = 0;
    private int mVideoHeight = 0;
    private FileInputStream fis = null;
    byte[] buffer = null;
    public nano(int port) throws IOException {
        super(port);
        LogUtils.e("run");
    }

//    @Override
//    public Response serve(IHTTPSession session) {
//////        return super.serve(session);
////        String msg = "<html><body><h1>Hello server</h1>\n";
////        Map<String, String> parms = session.getParms();
////        if (parms.get("username") == null) {
////            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
////        } else {
////            msg += "<p>Hello, " + parms.get("username") + "!</p>";
////        }
////        return newFixedLengthResponse(msg + "</body></html>\n");
////        return newFI
//        int len = 0;
//        try {
//            fis = new FileInputStream("/mnt/internal_sd/webscreen/mp4/c4.mp4");
//            buffer = new byte[1024*1024*1024];
//            int temp = 0;
//            while ((temp = fis.read()) != -1){
//                buffer[len] = (byte)temp;
//                len++;
//            }
////            fis.close();
//        }catch (Exception e){
//            LogUtils.e(e);
//        }
//
//        return new
//
//    }

    @Override
    public Response serve(String uri, Method method, Map<String, String> headers, Map<String, String> parms, Map<String, String> files) {
//        return super.serve(uri, method, headers, parms, files);
        int len = 0;
        byte[] buffer = null;
        Log.d("jltxgcy", headers.get("remote-addr"));

        // 默认传入的url是以“/”开头的，需要删除掉，否则就变成了绝对路径
        String file_name = uri.substring(1);

        // 默认的页面名称设定为index.html
        if(file_name.equalsIgnoreCase("")){
            file_name = "index.html";
        }

        try {

            //通过AssetManager直接打开文件进行读取操作
            InputStream in = new FileInputStream("/mnt/internal_sd/webscreen/mp4/c4.mp4");

            //假设单个网页文件大小的上限是1MB
            buffer = new byte[1024*1024*1024];

            int temp=0;
            while((temp=in.read())!=-1){
                buffer[len]=(byte)temp;
                len++;
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 将读取到的文件内容返回给浏览器
        return new NanoHTTPD.Response(new String(buffer,0,len));
    }

    @Override
    public Response serve(IHTTPSession session) {
        int len = 0;
        byte[] buffer = null;

        // 默认传入的url是以“/”开头的，需要删除掉，否则就变成了绝对路径

        // 默认的页面名称设定为index.html

        try {

            //通过AssetManager直接打开文件进行读取操作
            InputStream in = new FileInputStream("/mnt/internal_sd/webscreen/mp4/c4.mp4");

            //假设单个网页文件大小的上限是1MB
            buffer = new byte[1024*1024*1024];

            int temp=0;
            while((temp=in.read())!=-1){
                buffer[len]=(byte)temp;
                len++;
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 将读取到的文件内容返回给浏览器
        return new NanoHTTPD.Response(new String(buffer,0,len));
    }


    //    @Override
//    public Response serve(IHTTPSession session) {
////        return super.serve(session);
//        if (REQUEST_ROOT.equals(session.getUri())){
//            return responseRootPage(session);
//        }else if (mVideoFilePath.equals(session.getUri())){
//            return responseVideoStream(session);
//        }
//        return response404(session, session.getUri());
//    }
//
//    public Response responseRootPage(IHTTPSession session) {
//        File file = new File(mVideoFilePath);
//        if(!file.exists()) {
//            return response404(session,mVideoFilePath);
//        }
//        StringBuilder builder = new StringBuilder();
//        builder.append("<!DOCTYPE html><html><body>");
//        builder.append("<video ");
//        builder.append("width="+getQuotaStr(String.valueOf(mVideoWidth))+" ");
//        builder.append("height="+getQuotaStr(String.valueOf(mVideoHeight))+" ");
//        builder.append("controls>");
//        builder.append("<source src="+getQuotaStr(mVideoFilePath)+" ");
//        builder.append("type="+getQuotaStr("video/mp4")+">");
//        builder.append("Your browser doestn't support HTML5");
//        builder.append("</video>");
//        builder.append("</body></html>\n");
//        return new Response(builder.toString());
//    }
//
//    public Response responseVideoStream(IHTTPSession session) {
//        try {
//            FileInputStream fis = new FileInputStream(mVideoFilePath);
//            return new Response(Response.Status.OK, "video/mp4", fis);
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return response404(session,mVideoFilePath);
//        }
//    }
//
//    public Response response404(IHTTPSession session,String url) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("<!DOCTYPE html><html><body>");
//        builder.append("Sorry, Can't Found "+url + " !");
//        builder.append("</body></html>\n");
//        return new Response(builder.toString());
//    }
//
//
//    protected String getQuotaStr(String text) {
//        return "\"" + text + "\"";
//    }


}
