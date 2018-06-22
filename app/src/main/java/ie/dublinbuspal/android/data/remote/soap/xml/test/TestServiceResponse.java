package ie.dublinbuspal.android.data.remote.soap.xml.test;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class TestServiceResponse {

    @Path("soap:Body/TestServiceResponse")
    @Element(name = "TestServiceResult", required = false)
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
