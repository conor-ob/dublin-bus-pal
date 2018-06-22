package ie.dublinbuspal.android.data.remote.download;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class DownloadProgressResponseBody extends ResponseBody {

    private final ResponseBody body;
    private final DownloadProgressListener listener;
    private BufferedSource bufferedSource;

    DownloadProgressResponseBody(ResponseBody body, DownloadProgressListener listener) {
        this.body = body;
        this.listener = listener;
    }

    @Nullable
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
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;

                float percent = bytesRead == -1 ? 100f : (((float)totalBytesRead / (float) body.contentLength()) * 100);

                if (listener != null) {
                    listener.update((int) percent, bytesRead == -1);
                }
                return bytesRead;
            }
        };
    }

}
