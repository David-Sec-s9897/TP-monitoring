import com.secdavid.tpmonitoring.entsoe.EntsoeRestClient;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class JobsRestTest {
    @InjectMocks
    private EntsoeRestClient entsoeRestClient;

    @Mock
    EntsoeRestClient.EntsoeRestService service;

    @Before
    public void setup() throws Exception {
    }

    @Test
    public void testLoginWithCorrectUserNameAndPassword() throws Exception {
        //entsoeRestClient.getService().getDocumentBalancing("", "","","","","","");
    }

}
