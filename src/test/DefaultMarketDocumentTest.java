import com.secdavid.tpmonitoring.model.entsoe.DefaultMarketDocument;
import com.secdavid.tpmonitoring.model.entsoe.ParsedMasterData;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;
import com.secdavid.tpmonitoring.parsers.BalancingParser;
import com.secdavid.tpmonitoring.parsers.MasterdataParser;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DefaultMarketDocumentTest {

    @Test
    public void test() throws Exception {
        InputStream oneStream = DefaultMarketDocumentTest.class.getResourceAsStream("12.1.G_1.xml");
        InputStream twoStream = DefaultMarketDocumentTest.class.getResourceAsStream("12.1.G_2.xml");
        List<TimeSeries> outOne = BalancingParser.parseDocument(oneStream).getTimeSeries();
        List<TimeSeries> outTwo = BalancingParser.parseDocument(twoStream).getTimeSeries();

        System.out.println(outOne.get(0).getPeriod().getTimeInterval());
        System.out.println(outTwo.get(0).getPeriod().getTimeInterval());
        System.out.println(outOne.get(0).compare(outTwo.get(0)));




    }

}