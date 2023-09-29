package org.scraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.scraper.models.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.scraper.models.Component.Component;
import org.scraper.models.Component.ComponentFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;


public class DataScraper implements ComponentFactory {
    private static final String URL = "https://www.pc-kombo.com/us/components";
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        DataScraper dataScraper = new DataScraper();
        dataScraper.bootstrapData();
    }

    public void bootstrapData() {
        writeToJsonDatabase(getComponent(GPU.endpoint), GPU.endpoint);
        /*writeToJsonDatabase(getComponent(CPU.endpoint), CPU.endpoint);
        writeToJsonDatabase(getComponent(CpuCooler.endpoint), CpuCooler.endpoint);
        writeToJsonDatabase(getComponent(Cases.endpoint), Cases.endpoint);
        writeToJsonDatabase(getComponent(Motherboard.endpoint), Motherboard.endpoint);
        writeToJsonDatabase(getComponent(Monitor.endpoint), Monitor.endpoint);
        writeToJsonDatabase(getComponent(PSU.endpoint), PSU.endpoint);
        writeToJsonDatabase(getComponent(Ram.endpoint), Ram.endpoint);
        writeToJsonDatabase(getComponent(SSD.endpoint), SSD.endpoint);*/
    }

    public ArrayList<Component> getComponent(String endpoint) {
        ArrayList<Component> componentArrayList = new ArrayList<>();
        try {
            Document domDocument = Jsoup.connect(URL + endpoint).get();
            Elements hardwareList = domDocument.select( "#hardware a[href]");
            for (Element hardware: hardwareList) {
                String hardwareReference = hardware.attr("href");

                if (hardwareReference.contains("pc-kombo")) {
                    Elements productHtml = Jsoup.connect(hardwareReference)
                            .get()
                            .select("#product");

                    if (!getPrice(productHtml).equals("0") && !getPrice(productHtml).isBlank()) {
                        System.out.println("Fetching from URL: " +  hardwareReference);

                        Component component = componentCheck(productHtml, mapSpecificationsWithKeys(productHtml), endpoint);
                        componentArrayList.add(component);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error occurred while fetching:" + e);
        }

         writeToJsonDatabase(componentArrayList, endpoint);
         return componentArrayList;
    }
    private HashMap<String, String> mapSpecificationsWithKeys(Elements product) {
        HashMap<String, String> specifications = new HashMap<>();
        ArrayList<String> descriptionTermsList = new ArrayList<>();
        ArrayList<String> descriptionDetailsList = new ArrayList<>();
        product = product.select(".card-body dl");
        product.select("dt").forEach(listTerm -> descriptionTermsList.add(listTerm.text()));
        product.select("dd").forEach(listDetails -> descriptionDetailsList.add(listDetails.text()));

        for (int i = 0; i < descriptionTermsList.size(); i++) {
            specifications.put(descriptionTermsList.get(i), descriptionDetailsList.get(i));
        }
        return specifications;
    }
    private Component componentCheck(Elements productHtml, HashMap<String, String> productSpecifications, String endpoint) {
        return switch (endpoint) {
            case "/cpus" -> createCpu(productHtml, productSpecifications);
            case "/cases" -> createCases(productHtml, productSpecifications);
            case "/gpus" -> createGpu(productHtml, productSpecifications);
            case "/cpucoolers" -> createCpuCooler(productHtml, productSpecifications);
            case "/displays" -> createMonitor(productHtml, productSpecifications);
            case "/motherboards" -> createMotherboard(productHtml, productSpecifications);
            case "/psus" -> createPsu(productHtml, productSpecifications);
            case "/rams" -> createRam(productHtml, productSpecifications);
            case "/ssds" -> createSSD(productHtml, productSpecifications);
            default -> new Component();
        };
    }
    private void writeToJsonDatabase(ArrayList<Component> componentArrayList, String endpoint) {
        try {
            File file = new File("src/main/resources/DatabaseJSON/" + endpoint.substring(1) + "_database.json");
            mapper.writeValue(file, componentArrayList);
        }catch (Exception e) {
            System.out.println("Error occurred while writing json: " + e);
        }
    }
    private String getPrice(Elements productHtml) {
        try {
            return productHtml.select("[itemprop=price]").text().split(" ")[0];
        }catch (Exception e) {
            System.out.println("Error occurred inside getPrice: " + e);
            return "0";
        }
    }

    @Override
    public Cases createCases(Elements productHtml, HashMap<String, String> productSpecifications) {
        return Cases.builder()
                .id(UUID.randomUUID())
                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())
                .price(getPrice(productHtml))

                .width(productSpecifications.get("Width"))
                .depth(productSpecifications.get("Depth"))
                .height(productSpecifications.get("Height"))
                .motherboard(productSpecifications.get("Motherboard"))
                .powerSupply(productSpecifications.get("Power Supply"))
                .supportedGpuLength(productSpecifications.get("Supported GPU length"))
                .fanSupport(true)
                .window(true)
                .dustFilter(true)
                .cableManagement(true)
                .build();
    }

    @Override
    public CPU createCpu(Elements productHtml, HashMap<String, String> productSpecifications) {
        return CPU.builder()
                .id(UUID.randomUUID())
                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())
                .price(getPrice(productHtml))

                .cores(Integer.valueOf(productSpecifications.get("Cores")))
                .threads(Integer.valueOf(productSpecifications.get("Threads")))
                .baseClock(productSpecifications.get("Base Clock"))
                .turboClick(productSpecifications.get("Turbo Clock"))
                .socket(productSpecifications.get("Socket"))
                .TDP(productSpecifications.get("TDP"))
                .build();
    }

    @Override
    public CpuCooler createCpuCooler(Elements productHtml, HashMap<String, String> productSpecifications) {
        return CpuCooler.builder()
                .id(UUID.randomUUID())
                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())
                .price(getPrice(productHtml))

                .socketSupports(Arrays.stream(productSpecifications.get("Supported Sockets").split(",")).toList())
                .fanSupport(true)
                .extraFanSupport(true)
                .TDP(productSpecifications.get("TDP"))
                .height(productSpecifications.get("Height"))
                .build();
    }

    @Override
    public GPU createGpu(Elements productHtml, HashMap<String, String> productSpecifications) {
        return GPU.builder()
                .id(UUID.randomUUID())
                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())
                .price(getPrice(productHtml))

                .length(productSpecifications.get("Length"))
                .slots(Double.valueOf(productSpecifications.get("Slots")))
                .eightPinConnectors(Integer.valueOf(productSpecifications.get("6-pin connectors")))
                .sixPinConnectors(Integer.valueOf(productSpecifications.get("8-pin connectors")))
                .HDMI(Integer.valueOf(productSpecifications.get("HDMI")))
                .DP(Integer.valueOf(productSpecifications.get("DisplayPort")))
                .DVI(Integer.valueOf(productSpecifications.get("DVI")))
                .VGA(Integer.valueOf(productSpecifications.get("VGA")))
                .MHZ(productSpecifications.get("Memory Clock"))
                .VRAM(productSpecifications.get("Vram"))
                .TDP(productSpecifications.get("TDP"))
                .build();
    }

    @Override
    public Monitor createMonitor(Elements productHtml, HashMap<String, String> productSpecifications) {
        return Monitor.builder()
                .id(UUID.randomUUID())
                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())
                .price(getPrice(productHtml))

                .resolution(productSpecifications.get("Resolution"))
                .refreshRate(productSpecifications.get("Refresh Rate"))
                .size(productSpecifications.get("Size"))
                .panelType(productSpecifications.get("Panel"))
                .responseMS(productSpecifications.get("Response Time") + " ms")
                .HDMI(Integer.valueOf(productSpecifications.get("HDMI")))
                .DP(Integer.valueOf(productSpecifications.get("DisplayPort")))
                .DVI(Integer.valueOf(productSpecifications.get("DVI")))
                .VGA(Integer.valueOf(productSpecifications.get("VGA")))
                .speakers(productSpecifications.get("Speakers").isBlank())
                .curved(productSpecifications.get("Curved").isBlank())
                .adjustableHeight(productSpecifications.get("Adjustable Height").isBlank())
                .sync(productSpecifications.get("Sync"))
                .build();
    }

    @Override
    public Motherboard createMotherboard(Elements productHtml, HashMap<String, String> productSpecifications) {
        return Motherboard.builder()
                .id(UUID.randomUUID())
                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())
                .price(getPrice(productHtml))

                .socket(productSpecifications.get("Socket"))
                .chipset(productSpecifications.get("Chipset"))
                .memoryType(productSpecifications.get("Memory Type"))
                .memoryCapacity(productSpecifications.get("Memory Capacity"))
                .ramSlots(productSpecifications.get("Ramslots"))
                .sata(productSpecifications.get("SATA"))
                .pcie(true)
                .usbSlots(productSpecifications.get("USB 3 Slots"))
                .VGA(productSpecifications.get("VGA"))
                .DVI(productSpecifications.get("DVI"))
                .DP(productSpecifications.get("Display Port"))
                .HDMI(productSpecifications.get("HDMI"))

                .build();
    }

    @Override
    public PSU createPsu(Elements productHtml, HashMap<String, String> productSpecifications) {
        return PSU.builder()
                .id(UUID.randomUUID())
                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())
                .price(getPrice(productHtml))

                .watt(productSpecifications.get("Watt"))
                .size(productSpecifications.get("Size"))
                .efficiencyRating(productSpecifications.get("Efficiency Rating"))
                .pcie6(productSpecifications.get("PCI-E cables 6-pin"))
                .pcie8(productSpecifications.get("PCI-E cables 8-pin"))
                .build();
    }

    @Override
    public Ram createRam(Elements productHtml, HashMap<String, String> productSpecifications) {
        return Ram.builder()
                .id(UUID.randomUUID())
                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())
                .price(getPrice(productHtml))

                .ramType(productSpecifications.get("Ram Type"))
                .size(productSpecifications.get("Size"))
                .clock(Integer.parseInt(productSpecifications.get("Clock")))
                .timings(productSpecifications.get("Timings"))
                .sticks(Integer.valueOf(productSpecifications.get("Sticks")))
                .build();
    }

    @Override
    public SSD createSSD(Elements productHtml, HashMap<String, String> productSpecifications) {
        return SSD.builder()
                .id(UUID.randomUUID())
                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())
                .price(getPrice(productHtml))

                .formFactor(productSpecifications.get("Form Factor"))
                .protocol(productSpecifications.get("Protocol"))
                .size(productSpecifications.get("Size"))
                .NAND(productSpecifications.get("NAND"))
                .controller(productSpecifications.get("Controller"))
                .build();
    }

}