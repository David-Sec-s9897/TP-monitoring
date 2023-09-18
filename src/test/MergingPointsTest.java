import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;
import com.secdavid.tpmonitoring.parsers.BalancingParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static com.secdavid.tpmonitoring.util.TimeSeriesUtils.deleteDownloaded;
import static com.secdavid.tpmonitoring.util.TimeSeriesUtils.mergeTimeSeries;

public class MergingPointsTest {

    private ZonedDateTime end = LocalDateTime.now().withHour(22).withMinute(0).withSecond(0).atZone(ZoneId.of("UTC"));

    @Test
    public void test() throws Exception {

        InputStream oneStream = MergingPointsTest.class.getResourceAsStream("11.1_1.xml");
        InputStream updatedStream = MergingPointsTest.class.getResourceAsStream("11.1_2.xml");
        List<TimeSeries> outOne = BalancingParser.parseDocument(oneStream).getTimeSeries();
        List<TimeSeries> updateList = BalancingParser.parseDocument(updatedStream).getTimeSeries();

        outOne = deleteDownloaded(outOne, end);
        int currentLengthAfterDownload = outOne.get(0).getPeriod().getPoint().size();
        updateList = mergeTimeSeries(updateList);
        outOne = mergeTimeSeries(outOne);
        updateList.get(0).getPeriod().getPoint().remove(0);
        int updateListLength = updateList.get(0).getPeriod().getPoint().size();
        outOne.get(0).getPeriod().getPoint().addAll(updateList.get(0).getPeriod().getPoint());

        Assert.assertEquals(currentLengthAfterDownload+updateListLength, outOne.get(0).getPeriod().getPoint().size());
    }
}