package org.scraper.models.Component;

import org.jsoup.select.Elements;
import org.scraper.models.*;

import java.util.HashMap;

public interface ComponentFactory {
    CPU createCpu(Elements productHtml, HashMap<String, String> productSpecifications);
    GPU createGpu(Elements productHtml, HashMap<String, String> productSpecifications);
    Cases createCases(Elements productHtml, HashMap<String, String> productSpecifications);
    CpuCooler createCpuCooler(Elements productHtml, HashMap<String, String> productSpecifications);
    Monitor createMonitor(Elements productHtml, HashMap<String, String> productSpecifications);
    Motherboard createMotherboard(Elements productHtml, HashMap<String, String> productSpecifications);
    PSU createPsu(Elements productHtml, HashMap<String, String> productSpecifications);
    Ram createRam(Elements productHtml, HashMap<String, String> productSpecifications);
    SSD createSSD(Elements productHtml, HashMap<String, String> productSpecifications);
}
