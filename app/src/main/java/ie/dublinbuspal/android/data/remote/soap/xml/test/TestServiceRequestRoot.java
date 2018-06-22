package ie.dublinbuspal.android.data.remote.soap.xml.test;

import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "TestService", strict = false)
@Namespace(reference = "http://dublinbus.ie/")
public class TestServiceRequestRoot {

    public TestServiceRequestRoot() {
        super();
    }

}
