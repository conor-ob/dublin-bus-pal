package ie.dublinbuspal.service.interceptor;

import java.io.IOException;

import ie.dublinbuspal.service.download.DownloadProgressResponseBody;
import ie.dublinbuspal.util.DownloadProgressListener;
import okhttp3.Interceptor;
import okhttp3.Response;

public class DownloadProgressInterceptor implements Interceptor {

    private final DownloadProgressListener listener;

    public DownloadProgressInterceptor(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (listener == null) {
            return chain.proceed(chain.request());
        }
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .body(new DownloadProgressResponseBody(originalResponse.body(), chain.request().hashCode(), listener))
                .build();
    }

}
