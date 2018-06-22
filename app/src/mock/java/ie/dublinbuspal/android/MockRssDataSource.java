package ie.dublinbuspal.android;

import android.content.Context;

import ie.dublinbuspal.android.R;
import ie.dublinbuspal.android.data.remote.rss.RssDataSource;
import ie.dublinbuspal.android.data.remote.rss.xml.Rss;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MockRssDataSource implements RssDataSource {

    private final Context context;
    private Rss rss;

    public MockRssDataSource(Context context) {
        this.context = context;
    }

    @Override
    public Rss getRss() throws Exception {
        if (rss == null) {
            final InputStream inputStream = context.getResources().openRawResource(R.raw.rss);
            final Reader reader = new InputStreamReader(inputStream);
            Serializer ser = new Persister();
            rss = ser.read(Rss.class, reader);
        }
        return rss;
    }

}
