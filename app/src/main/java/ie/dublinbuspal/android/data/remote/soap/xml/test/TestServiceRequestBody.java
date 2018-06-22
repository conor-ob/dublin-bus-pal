package ie.dublinbuspal.android.data.remote.soap.xml.test;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "soap12:Body", strict = false)
public class TestServiceRequestBody {

    @Element(name = "TestService",required = false)
    private TestServiceRequestRoot testServiceRequestRoot;

    public TestServiceRequestRoot getTestServiceRequestRoot() {
        return testServiceRequestRoot;
    }

    public void setTestServiceRequestRoot(TestServiceRequestRoot testServiceRequestRoot) {
        this.testServiceRequestRoot = testServiceRequestRoot;
    }

}
