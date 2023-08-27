package org.example;

import org.example.models.CPU;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataScraperTest {
    private DataScraper dataScraper = new DataScraper();

    @Before
    public void setUp() throws Exception {
         try {
             dataScraper = new DataScraper();
         }catch (Exception e) {
             System.out.println("Initiating dataScraper failed with error :" + e);
         }
    }

    @Test
    public void testCpuSpecificationValues() {
        dataScraper.getComponent(CPU.endpoint);
        System.out.println();
    }

}
