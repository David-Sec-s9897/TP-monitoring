import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.tasks.MissingIntervalsService;
import org.junit.Assert;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class MissingIntervalsServiceTest {

    @Test
    public void identifyMissingIntervalsTest() {
        MissingIntervalsService missingIntervalsService = new MissingIntervalsService();
        List < TimeInterval> timeIntervals = new ArrayList<>();
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start = ZonedDateTime.now().minusHours(2);
        timeInterval.end = ZonedDateTime.now();
        timeIntervals.add(timeInterval);

        missingIntervalsService.addToMissingIntervals("someTestProcess", timeIntervals);
        missingIntervalsService.addToMissingIntervals("someTestProcess2", timeIntervals);

        boolean areNewIntervals = missingIntervalsService.areSomeNewIntervals();
        Assert.assertTrue(areNewIntervals);

        missingIntervalsService.updateLastMissingIntervals();
        timeIntervals = new ArrayList<>();
        timeInterval = new TimeInterval();
        timeIntervals.add(timeInterval);

        missingIntervalsService.addToMissingIntervals("someTestProcess3", timeIntervals);
        areNewIntervals = missingIntervalsService.areSomeNewIntervals();
        Assert.assertTrue(areNewIntervals);
    }

}