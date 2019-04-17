package ie.dublinbuspal.service.download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import ie.dublinbuspal.util.DownloadProgressListener;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class DownloadProgressResponseBody extends ResponseBody {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadProgressResponseBody.class);

    private final ResponseBody body;
    private final int requestId;
    private final DownloadProgressListener listener;
    private BufferedSource bufferedSource;

    public DownloadProgressResponseBody(ResponseBody body, int requestId, DownloadProgressListener listener) {
        this.body = body;
        this.requestId = requestId;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return body.contentType();
    }

    @Override
    public long contentLength() {
        return body.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(body.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;

                float percent = (((float)totalBytesRead / (float) body.contentLength()) * 100);

                if (percent < 0) {
                    percent = 0;
                }

                if (percent > 100) {
                    percent = 100;
                }

                if (listener != null) {
                    listener.update(requestId, (int) percent);
                }
                return bytesRead;
            }
        };
    }

}
