import com.secdavid.tpmonitoring.entsoe.TransparencyPortalRestClient;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeriesJob;
import com.secdavid.tpmonitoring.parsers.TimeSeriesJobParser;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.core.MediaType;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.Buffer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetJobsTest {



}
