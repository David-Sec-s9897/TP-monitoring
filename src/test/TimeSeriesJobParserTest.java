import com.secdavid.tpmonitoring.model.entsoe.TimeSeriesJob;
import com.secdavid.tpmonitoring.parsers.TimeSeriesJobParser;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TimeSeriesJobParserTest {

    @Test
    public void testJobParser() throws Exception {
        InputStream resource = TimeSeriesJobParserTest.class.getResourceAsStream("jobs.xml");
        List<TimeSeriesJob> list = TimeSeriesJobParser.parseDocument(resource);
        for(TimeSeriesJob job: list){
            System.out.println(job.toString());
        }
        assertNotNull(list);
    }
}
