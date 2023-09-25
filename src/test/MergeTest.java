import com.secdavid.tpmonitoring.model.entsoe.TimeInterval;
import com.secdavid.tpmonitoring.model.entsoe.TimeSeries;
import com.secdavid.tpmonitoring.parsers.BalancingParser;
import com.secdavid.tpmonitoring.util.TimeSeriesUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MergeTest {

    /**
     *         0 = {TimeInterval@1428} "TimeInterval{start=2023-09-20T22:00Z, end=2023-09-21T22:00Z}"
     *         1 = {TimeInterval@1429} "TimeInterval{start=2023-09-21T22:00Z, end=2023-09-22T22:00Z}"
     *         2 = {TimeInterval@1430} "TimeInterval{start=2023-09-22T22:00Z, end=2023-09-23T22:00Z}"
     *         3 = {TimeInterval@1431} "TimeInterval{start=2023-09-23T22:00Z, end=2023-09-24T22:00Z}"
     *         4 = {TimeInterval@1432} "TimeInterval{start=2023-09-24T22:00Z, end=2023-09-25T22:00Z}"
     *
     *         |--INTERVAL 0-(1 day)-|--INTERVAL 1-(1 day)-|--INTERVAL 2-(1 day)-|--INTERVAL 3-(1 day)-|--INTERVAL 4-(1 day)-|
     */
    @Test
    public void mergeTimeIntervalsHDSTest(){
        String[] intervals = new String[]{"2023-09-20T22:00Z","2023-09-21T22:00Z","2023-09-21T22:00Z", "2023-09-22T22:00Z", "2023-09-22T22:00Z", "2023-09-23T22:00Z", "2023-09-23T22:00Z", "2023-09-24T22:00Z", "2023-09-24T22:00Z", "2023-09-25T22:00Z"};
        List<TimeInterval> timeIntervals = generateTimeIntervals(intervals);
        List<TimeInterval> mergedTI = TimeSeriesUtils.mergeTimeIntervals(timeIntervals, 0);

        Assert.assertEquals(1, mergedTI.size());
        Assert.assertEquals("2023-09-20T22:00Z", mergedTI.get(0).start.toString());
        Assert.assertEquals("2023-09-25T22:00Z", mergedTI.get(0).end.toString());
    }

    /**
     * 0 = {TimeInterval@983} "TimeInterval{start=2023-09-20T22:00Z, end=2023-09-25T22:00Z}"
     * 1 = {TimeInterval@984} "TimeInterval{start=2023-09-24T22:00Z, end=2023-09-25T22:00Z}"
     * 2 = {TimeInterval@985} "TimeInterval{start=2023-09-24T22:00Z, end=2023-09-26T22:00Z}"
     *
     *  |------------------------INTERVAL 1 (5 days)-------------------------------------|
     *                                                             |--INTERVAL 2-(1 day)-|
     *                                                             |-------------INTERVAL 3-(2 days)------------|
     */
    @Test
    public void mergeTimeIntervalsHDSTestOverlappingIntervals(){
        String[] intervals = new String[]{"2023-09-20T22:00Z","2023-09-25T22:00Z", "2023-09-24T22:00Z", "2023-09-25T22:00Z", "2023-09-24T22:00Z", "2023-09-26T22:00Z"};
        List<TimeInterval> timeIntervals = generateTimeIntervals(intervals);
        List<TimeInterval> mergedTI = TimeSeriesUtils.mergeTimeIntervals(timeIntervals, 0);

        Assert.assertEquals(1, mergedTI.size());
        Assert.assertEquals("2023-09-20T22:00Z", mergedTI.get(0).start.toString());
        Assert.assertEquals("2023-09-26T22:00Z", mergedTI.get(0).end.toString());

    }

    /**
     * 0 = {TimeInterval@987} "TimeInterval{start=2023-09-20T22:00Z, end=2023-09-21T22:00Z}"
     * 1 = {TimeInterval@988} "TimeInterval{start=2023-09-21T22:00Z, end=2023-09-22T22:00Z}"
     * 2 = {TimeInterval@989} "TimeInterval{start=2023-09-22T22:00Z, end=2023-09-23T22:00Z}"
     * 3 = {TimeInterval@990} "TimeInterval{start=2023-09-23T22:00Z, end=2023-09-24T22:00Z}"
     * 4 = {TimeInterval@991} "TimeInterval{start=2023-09-24T22:00Z, end=2023-09-25T22:00Z}"
     *     --> GAP in the data <--
     * 5 = {TimeInterval@992} "TimeInterval{start=2023-09-25T23:00Z, end=2023-09-26T22:00Z}"
     */
    @Test
    public void mergeTimeIntervalsTest2Intervals(){
        String[] intervals = new String[]{"2023-09-20T22:00Z","2023-09-21T22:00Z","2023-09-21T22:00Z", "2023-09-22T22:00Z", "2023-09-22T22:00Z", "2023-09-23T22:00Z", "2023-09-23T22:00Z", "2023-09-24T22:00Z", "2023-09-24T22:00Z", "2023-09-25T22:00Z", "2023-09-25T23:00Z", "2023-09-26T22:00Z"};
        List<TimeInterval> timeIntervals = generateTimeIntervals(intervals);
        List<TimeInterval> mergedTI = TimeSeriesUtils.mergeTimeIntervals(timeIntervals, 0);

        Assert.assertEquals(2, mergedTI.size());
        Assert.assertEquals("2023-09-20T22:00Z", mergedTI.get(0).start.toString());
        Assert.assertEquals("2023-09-25T22:00Z", mergedTI.get(0).end.toString());

        Assert.assertEquals("2023-09-25T23:00Z", mergedTI.get(1).start.toString());
        Assert.assertEquals("2023-09-26T22:00Z", mergedTI.get(1).end.toString());

        List<TimeInterval> missingIntervals = TimeSeriesUtils.getMissingIntervals(mergedTI);
        Assert.assertEquals(1, missingIntervals.size());
        Assert.assertEquals("2023-09-25T22:00Z", missingIntervals.get(0).start.toString());
        Assert.assertEquals("2023-09-25T23:00Z", missingIntervals.get(0).end.toString());
    }

    /**
     * 0 = {TimeInterval@988} "TimeInterval{start=2023-09-20T22:00Z, end=2023-09-21T22:00Z}"
     *     --> GAP in the data <--
     * 1 = {TimeInterval@989} "TimeInterval{start=2023-09-21T23:00Z, end=2023-09-22T22:00Z}"
     * 2 = {TimeInterval@990} "TimeInterval{start=2023-09-22T22:00Z, end=2023-09-23T22:00Z}"
     * 3 = {TimeInterval@991} "TimeInterval{start=2023-09-23T22:00Z, end=2023-09-24T22:00Z}"
     * 4 = {TimeInterval@992} "TimeInterval{start=2023-09-24T22:00Z, end=2023-09-25T22:00Z}"
     *     --> 2nd GAP in the data <--
     * 5 = {TimeInterval@993} "TimeInterval{start=2023-09-25T23:00Z, end=2023-09-26T22:00Z}"
     */
    @Test
    public void mergeTimeIntervalsTest3IntervalsMiddle(){
        String[] intervals = new String[]{"2023-09-20T22:00Z","2023-09-21T22:00Z","2023-09-21T23:00Z", "2023-09-22T22:00Z", "2023-09-22T22:00Z", "2023-09-23T22:00Z", "2023-09-23T22:00Z", "2023-09-24T22:00Z", "2023-09-24T22:00Z", "2023-09-25T22:00Z", "2023-09-25T23:00Z", "2023-09-26T22:00Z"};
        List<TimeInterval> timeIntervals = generateTimeIntervals(intervals);
        List<TimeInterval> mergedTI = TimeSeriesUtils.mergeTimeIntervals(timeIntervals, 0);

        Assert.assertEquals(3, mergedTI.size());

        List<TimeInterval> missingIntervals = TimeSeriesUtils.getMissingIntervals(mergedTI);
        Assert.assertEquals(2, missingIntervals.size());
        Assert.assertEquals("2023-09-21T22:00Z", missingIntervals.get(0).start.toString());
        Assert.assertEquals("2023-09-21T23:00Z", missingIntervals.get(0).end.toString());

        Assert.assertEquals("2023-09-25T22:00Z", missingIntervals.get(1).start.toString());
        Assert.assertEquals("2023-09-25T23:00Z", missingIntervals.get(1).end.toString());
    }

    /**
     * 0 = {TimeInterval@989} "TimeInterval{start=2023-09-20T22:00Z, end=2023-09-21T22:00Z}"
     *     --> GAP in the data <--
     * 1 = {TimeInterval@990} "TimeInterval{start=2023-09-21T23:00Z, end=2023-09-22T22:00Z}"
     *     --> GAP in the data <--
     * 2 = {TimeInterval@991} "TimeInterval{start=2023-09-22T23:00Z, end=2023-09-23T22:00Z}"
      *     --> GAP in the data <--
     * 3 = {TimeInterval@992} "TimeInterval{start=2023-09-23T23:00Z, end=2023-09-24T22:00Z}"
     *     --> GAP in the data <--
     * 4 = {TimeInterval@993} "TimeInterval{start=2023-09-24T22:00Z, end=2023-09-25T23:00Z}"
     *     --> GAP in the data <--
     * 5 = {TimeInterval@994} "TimeInterval{start=2023-09-25T23:00Z, end=2023-09-26T22:00Z}"
     */
    @Test
    public void mergeTimeIntervalAllSubsequentIntervals(){
        String[] intervals = new String[]{"2023-09-20T22:00Z","2023-09-21T22:00Z","2023-09-21T23:00Z", "2023-09-22T22:00Z", "2023-09-22T23:00Z", "2023-09-23T22:00Z", "2023-09-23T23:00Z", "2023-09-24T22:00Z", "2023-09-24T23:00Z", "2023-09-25T22:00Z", "2023-09-25T23:00Z", "2023-09-26T22:00Z"};
        List<TimeInterval> timeIntervals = generateTimeIntervals(intervals);
        List<TimeInterval> mergedTI = TimeSeriesUtils.mergeTimeIntervals(timeIntervals, 0);

        Assert.assertEquals(6, mergedTI.size());

        List<TimeInterval> missingIntervals = TimeSeriesUtils.getMissingIntervals(mergedTI);
        Assert.assertEquals(5, missingIntervals.size());
        Assert.assertEquals("2023-09-21T22:00Z", missingIntervals.get(0).start.toString());
        Assert.assertEquals("2023-09-21T23:00Z", missingIntervals.get(0).end.toString());

        Assert.assertEquals("2023-09-22T22:00Z", missingIntervals.get(1).start.toString());
        Assert.assertEquals("2023-09-22T23:00Z", missingIntervals.get(1).end.toString());

        Assert.assertEquals("2023-09-23T22:00Z", missingIntervals.get(2).start.toString());
        Assert.assertEquals("2023-09-23T23:00Z", missingIntervals.get(2).end.toString());

        Assert.assertEquals("2023-09-24T22:00Z", missingIntervals.get(3).start.toString());
        Assert.assertEquals("2023-09-24T23:00Z", missingIntervals.get(3).end.toString());

        Assert.assertEquals("2023-09-25T22:00Z", missingIntervals.get(4).start.toString());
        Assert.assertEquals("2023-09-25T23:00Z", missingIntervals.get(4).end.toString());
    }

    @Test
    public void mergeMultipleIntervals() throws Exception{
        List<TimeInterval> one = loadTimeIntervals("12.1.F_1.xml");
        List<TimeInterval> two = loadTimeIntervals("12.1.F_2.xml");
        List<TimeInterval> three = loadTimeIntervals("12.1.F_3.xml");
        List<TimeInterval> four = loadTimeIntervals("12.1.F_4.xml");
        List<TimeInterval> five = loadTimeIntervals("12.1.F_5.xml");
        List<TimeInterval> mergedTI = TimeSeriesUtils.mergeTimeIntervals(one);
        mergedTI = TimeSeriesUtils.mergeTimeIntervals(mergedTI, two,three,four, five);
        Assert.assertEquals(2, mergedTI.size());
    }


    @Test
    public void mergeMultipleIntervalsUnsorted() throws Exception{
        List<TimeInterval> one = loadTimeIntervals("12.1.F_5.xml");
        List<TimeInterval> two = loadTimeIntervals("12.1.F_2.xml");
        List<TimeInterval> three = loadTimeIntervals("12.1.F_1.xml");
        List<TimeInterval> four = loadTimeIntervals("12.1.F_3.xml");
        List<TimeInterval> five = loadTimeIntervals("12.1.F_4.xml");
        List<TimeInterval> mergedTI = TimeSeriesUtils.mergeTimeIntervals(one);
        mergedTI = TimeSeriesUtils.mergeTimeIntervals(mergedTI, two,three,four, five);
        Assert.assertEquals(2, mergedTI.size());
    }

    private List<TimeInterval> loadTimeIntervals(String s) throws Exception {
        return BalancingParser.parseDocument(MergeTest.class.getResourceAsStream(s))
                .getTimeSeries().stream().map(ts -> ts.getPeriod().getTimeInterval()).collect(Collectors.toList());
    }


    private List<TimeInterval> generateTimeIntervals(String[] intervals) {
        List<TimeInterval> timeIntervals = new ArrayList<>();
        for (int i = 0; i < intervals.length; i+=2) {
            TimeInterval timeInterval = new TimeInterval();
            timeInterval.setStart(ZonedDateTime.parse(intervals[i]));
            timeInterval.setEnd(ZonedDateTime.parse(intervals[i+1]));
            timeIntervals.add(timeInterval);
        }
        return timeIntervals;
    }

}