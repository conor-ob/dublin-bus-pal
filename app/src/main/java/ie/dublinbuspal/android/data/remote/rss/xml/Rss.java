package ie.dublinbuspal.android.data.remote.rss.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss")
public class Rss {

    @Attribute(name = "version", required = false)
    private String version;

    @Element(name = "channel", required = false)
    private Channel channel;

    public Rss() {
        super();
    }

    public String getVersion() {
        return version;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
