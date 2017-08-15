package demo.ypc.androidasyncdemo;

import android.util.Log;

import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import java.io.File;

/**
 * Created by yinpengcheng on 2017/8/15.
 */

public class NIOHttpServer implements HttpServerRequestCallback {
    private static final String TAG = "NIOHttpServer";

    private static NIOHttpServer mInstance;

    public static int PORT_LISTEN_DEFALT = 5000;

    AsyncHttpServer server = new AsyncHttpServer();

    public static NIOHttpServer getInstance() {
        if (mInstance == null) {
            // 增加类锁,保证只初始化一次
            synchronized (NIOHttpServer.class) {
                if (mInstance == null) {
                    mInstance = new NIOHttpServer();
                }
            }
        }
        return mInstance;
    }

    //仿照nanohttpd的写法
    public static enum Status {
        REQUEST_OK(200, "请求成功"),
        REQUEST_ERROR(500, "请求失败"),
        REQUEST_ERROR_API(501, "无效的请求接口"),
        REQUEST_ERROR_CMD(502, "无效命令"),
        REQUEST_ERROR_DEVICEID(503, "不匹配的设备ID"),
        REQUEST_ERROR_ENV(504, "不匹配的服务环境");

        private final int requestStatus;
        private final String description;

        Status(int requestStatus, String description) {
            this.requestStatus = requestStatus;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public int getRequestStatus() {
            return requestStatus;
        }
    }

    /**
     * 开启本地服务
     */
    public void startServer() {
//如果有其他的请求方式，例如下面一行代码的写法
        server.addAction("OPTIONS", "[\\d\\D]*", this);
        server.get("[\\d\\D]*", this);
        server.post("[\\d\\D]*", this);
        server.listen(PORT_LISTEN_DEFALT);
    }

    @Override
    public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
        Log.d(TAG, "进来了，哈哈");
        String uri = request.getPath();
//这个是获取header参数的地方，一定要谨记哦
        Multimap headers = request.getHeaders().getMultiMap();

        if (checkUri(uri)) {// 针对的是接口的处理
            //注意：这个地方是获取post请求的参数的地方，一定要谨记哦
            Multimap parms = ((AsyncHttpRequestBody<Multimap>)request.getBody()).get();
            if (headers != null) {
                LogUtil.d(TAG, headers.toString());
            }
            if (parms != null) {
                LogUtil.d(TAG, "parms = " + parms.toString());
            }

            if (StringUtil.isEmpty(uri)) {
                throw new RuntimeException("无法获取请求地址");
            }

            if ("OPTIONS".toLowerCase().equals(request.getMethod().toLowerCase())) {
                LogUtil.d(TAG, "OPTIONS探测性请求");
                addCORSHeaders(Status.REQUEST_OK, response);
                return;
            }

            switch (uri) {
                case "/test": {//接口2
                    //此方法包括了封装返回的接口请求数据和处理异常以及跨域
                    return getXXX(parms);
                }
                default: {
                    return addHeaderResponse(Status.REQUEST_ERROR_API);
                }
            }
        } else {
            // 针对的是静态资源的处理
            String filePath = getFilePath(uri); // 根据url获取文件路径

            if (filePath == null) {
                response.send("sd卡没有找到");
                return;
            }
            File file = new File(filePath);

            if (file != null && file.exists()) {
                Log.d(TAG, "file path = " + file.getAbsolutePath());

                response.sendFile(file);//和nanohttpd不一样的地方

            } else {
                Log.d(TAG, "file path = " + file.getAbsolutePath() + "的资源不存在");
            }
        }
    }

}
