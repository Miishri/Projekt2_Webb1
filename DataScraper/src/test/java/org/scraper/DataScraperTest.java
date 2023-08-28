package org.scraper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.scraper.models.CPU;
import org.junit.Before;
import org.junit.Test;
import org.scraper.models.Component.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataScraperTest {

    private final String URL = "https://www.pc-kombo.com/us/components";
    private final String rootDir = "src/main/resources/DatabaseJSON/";
    private ObjectMapper mapper;
    private DataScraper dataScraper;
    @Before
    public void setUp() {
         try {
             dataScraper = new DataScraper();
             mapper = new ObjectMapper();
         }catch (Exception e) {
             System.out.println("Initiating dataScraper failed with error :" + e);
         }
    }
    @Test
    public void testCpuSpecificationTitle() {
        CPU testCpu = (CPU) dataScraper.getComponent(CPU.endpoint).get(0);
        assertEquals(testCpu.getTitle(), "AMD Ryzen 5 5600X");
    }
    @Test
    public void testCpuSpecificationImage() {
        CPU testCpu = (CPU) dataScraper.getComponent(CPU.endpoint).get(0);
        assertEquals(testCpu.getImage(), "https://www.alternate.de/p/o/8/8/AMD_Ryzen__5_5600X__Prozessor@@1685588.jpg");
    }
    @Test
    public void testCpuSpecificationDescription() {
        CPU testCpu = (CPU) dataScraper.getComponent(CPU.endpoint).get(0);
        assertEquals(testCpu.getDescription(), "The AMD Ryzen 5 5600X has 6 cores and 12 threads. Its base clock is at 3.7 GHz, its turbo clock goes up to 4.6 GHz. Its multiplier is unlocked, making it possible to overclock the processor easily.");
    }
    @Test
    public void testCpuSpecificationProducer() {
        CPU testCpu = (CPU) dataScraper.getComponent(CPU.endpoint).get(0);
        assertEquals(testCpu.getProducer(), "AMD");
    }

    @Test
    public void testWriteToJsonDatabase() throws IOException {
        ArrayList<Component> componentArrayList = dataScraper.getComponent(CPU.endpoint);

        File file = new File(rootDir + CPU.endpoint.substring(1) + "_database.json");
        List<CPU> componentsReadList = mapper.readValue(file, new TypeReference<>(){});
        assertEquals(componentArrayList.get(0).getTitle(), componentsReadList.get(0).getTitle());
    }

}
