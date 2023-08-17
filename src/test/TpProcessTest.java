import com.secdavid.tpmonitoring.model.entsoe.DefaultMarketDocument;
import com.secdavid.tpmonitoring.parsers.BalancingParser;
import com.secdavid.tpmonitoring.parsers.TransmissionParser;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TpProcessTest {

    @Test
    public void testBalancing() throws Exception {
        InputStream balancingResource = TpProcessTest.class.getResourceAsStream("test-balancing.xml");
        DefaultMarketDocument out = BalancingParser.parseDocument(balancingResource);

        assertNotNull(out);
    }

    @Test
    public void testTransmission() throws Exception {
        InputStream transmissionResource = TpProcessTest.class.getResourceAsStream("test-transmission.xml");

        DefaultMarketDocument out = TransmissionParser.parseDocument(transmissionResource);
        assertNotNull(out);
    }

    @Test
    public void testGeneration() throws Exception {
        InputStream generationResource = TpProcessTest.class.getResourceAsStream("test-generation.xml");

        DefaultMarketDocument out = TransmissionParser.parseDocument(generationResource);
        assertNotNull(out);
    }

    @Test
    public void testBalancingA24() throws Exception {
        InputStream balancingResource = TpProcessTest.class.getResourceAsStream("test_balancing_A24.xml");

        DefaultMarketDocument out = BalancingParser.parseDocument(balancingResource);
        assertNotNull(out);
    }
}