package org.scraper;

import org.scraper.models.CPU;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataScraperTest {
    private DataScraper dataScraper;

    @Before
    public void setUp() throws Exception {
         try {
             dataScraper = new DataScraper();
         }catch (Exception e) {
             System.out.println("Initiating dataScraper failed with error :" + e);
         }
    }

    @Test
    public void testCpuSpecificationTitle() {
        CPU testCpu = (CPU) dataScraper.getComponent(CPU.endpoint).get(0);
        assertEquals(testCpu.getTitle(), "AMD Ryzen 5 5600X");
    }

}
