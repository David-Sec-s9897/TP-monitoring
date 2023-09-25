import com.secdavid.tpmonitoring.model.entsoe.ParsedMasterData;
import com.secdavid.tpmonitoring.parsers.MasterdataParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class MasterdataParserTest {

    @Test
    public void testMasterdataParser() throws Exception {
        InputStream masterdataStream = MasterdataParserTest.class.getResourceAsStream("masterdata-transmission.xml");
        List<ParsedMasterData> out = MasterdataParser.parseMasterdata(masterdataStream);
        assertNotNull(out);
    }

}
