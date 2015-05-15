import junit.framework.*;
import java.util.regex.*;
import org.findata.blpwrapper.*;

public class ReferenceDataResultTest extends TestCase {
  private Connection conn;

  public void setUp() throws Exception{
    conn = new Connection();
  }

  public void tearDown() throws Exception{
    conn.close();
  }



  public void testValidRequest() throws Exception {
    String screenName = "Quality Screen";
    String screenType = "GLOBAL";
    BeqsDataResult result = (BeqsDataResult)conn.beqs(screenName, screenType);

  }

  public void testValidRequestWithAsOfDate() throws Exception {
    String screenName = {"Quality Screen"};
    String screenType = {"GLOBAL"};
    StringAsOfDate = "20100201";

    BeqsDataResult result = (BeqsDataResult)conn.beqs(screenName, screenType, AsOfDate);

  }


}
