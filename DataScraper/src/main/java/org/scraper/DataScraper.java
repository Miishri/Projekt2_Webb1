package org.scraper;

import com.fasterxml.jackson.core.type.TypeReference;
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


public class DataScraper implements ComponentFactory {
    private static final String URL = "https://www.pc-kombo.com/us/components";
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        DataScraper dataScraper = new DataScraper();
        System.out.println(dataScraper.getComponent(Ram.endpoint));
    }


    public ArrayList<Component> getComponent(String endpoint) {
        ArrayList<Component> componentArrayList = new ArrayList<>();


        try {
            Document domDocument = Jsoup.connect(URL + endpoint).get();
            Elements hardwareList = domDocument.select( "#hardware a[href]");

            for (Element hardware: hardwareList) {
                String hardwareReference = hardware.attr("href");

                if (hardwareReference.contains("pc-kombo")) {
                    System.out.println("Fetching from URL: " +  hardwareReference);

                    Elements productHtml = Jsoup.connect(hardwareReference)
                            .get()
                            .select("#product");


                    Component component = componentCheck(productHtml, mapSpecificationsWithKeys(productHtml), endpoint);
                    componentArrayList.add(component);

                    return componentArrayList;
                }
            }

        } catch (Exception e) {
            System.out.println("Error occurred while fetching CPUs:" + e);
        }


        writeToJsonDatabase(componentArrayList, endpoint);
         return componentArrayList;
    }

    private HashMap<String, String> mapSpecificationsWithKeys(Elements product) {
        HashMap<String, String> specifications = new HashMap<>();
        ArrayList<String> descriptionTermsList = new ArrayList<>();
        ArrayList<String> descriptionDetailsList = new ArrayList<>();
        product = product.select(".card-body dl");
        product.select("dt").forEach(listTerm -> {
            descriptionTermsList.add(listTerm.text());
        });
        product.select("dd").forEach(listDetails -> {
            descriptionDetailsList.add(listDetails.text());
        });

        for (int i = 0; i < descriptionTermsList.size(); i++) {
            specifications.put(descriptionTermsList.get(i), descriptionDetailsList.get(i));
        }

        System.out.println(specifications);

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
            case "/ssds" -> createSsd(productHtml, productSpecifications);
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
    @Override
    public CPU createCpu(Elements productHtml, HashMap<String, String> productSpecifications) {
        return CPU.builder()

                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())

                .cores(Integer.valueOf(productSpecifications.get("Cores")))
                .threads(Integer.valueOf(productSpecifications.get("Threads")))
                .baseClock(productSpecifications.get("Base Clock"))
                .turboClick(productSpecifications.get("Turbo Clock"))
                .socket(productSpecifications.get("Socket"))
                .TDP(productSpecifications.get("TDP"))
                .build();
    }

    @Override
    public GPU createGpu(Elements productHtml, HashMap<String, String> productSpecifications) {
        return GPU.builder()

                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())

                .length(productSpecifications.get("Length"))
                .slots(Double.valueOf(productSpecifications.get("Slots")))
                .eightPinConnectors(Integer.valueOf(productSpecifications.get("6-pin connectors")))
                .sixPinConnectors(Integer.valueOf(productSpecifications.get("8-pin connectors")))
                .HDMI(Integer.valueOf(productSpecifications.get("HDMI")))
                .DP(Integer.valueOf(productSpecifications.get("DisplayPort")))
                .DVI(Integer.valueOf(productSpecifications.get("DVI")))
                .VGA(Integer.valueOf(productSpecifications.get("VGA")))
                .MHZ(productSpecifications.get("Memory_Clock"))
                .VRAM(productSpecifications.get("Vram"))
                .TDP(productSpecifications.get("TDP"))
                .build();
    }
        @Override
    public Cases createCases(Elements productHtml, HashMap<String, String> productSpecifications) {
        return Cases.builder()
                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())

                .width(productSpecifications.get("Width"))
                .depth(productSpecifications.get("Depth"))
                .height(productSpecifications.get("Height"))
                .motherboard(productSpecifications.get("Motherboard"))
                .powerSupply(productSpecifications.get("Power Supply"))
                .supportedGpuLength(productSpecifications.get("Supported GPU length"))
                .supportedCpuCooler(productSpecifications.get("Supported CPU cooler"))
                .fanSupport(true)
                .radiatorSupport(true)
                .window(true)
                .dustFilter(true)
                .cableManagement(true)
                .noiseIsolation(true)
                .build();
    }

    @Override
    public CpuCooler createCpuCooler(Elements productHtml, HashMap<String, String> productSpecifications) {
        return CpuCooler.builder()

                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())

                .socketSupports(Arrays.stream(productSpecifications.get("Supported Sockets").split(",")).toList())
                .fanSupport(true)
                .extraFanSupport(true)
                .TDP(productSpecifications.get("TDP"))
                .height(productSpecifications.get("Height"))
                .build();
    }

    @Override
    public Monitor createMonitor(Elements productHtml, HashMap<String, String> productSpecifications) {
        return Monitor.builder()

                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())

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

                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())

                .socket(productSpecifications.get("Socket"))
                .chipset(productSpecifications.get("Chipset"))
                .memoryType(productSpecifications.get("Memory Type"))
                .memoryCapacity(Integer.valueOf(productSpecifications.get("Memory Capacity")))
                .ramSlots(Integer.valueOf(productSpecifications.get("Ramslots")))
                .sata(Integer.valueOf(productSpecifications.get("SATA")))
                .pcie(true)
                .usbSlots(Integer.valueOf(productSpecifications.get("USB 3 Slots")))
                .VGA(Integer.valueOf(productSpecifications.get("VGA")))
                .DVI(Integer.valueOf(productSpecifications.get("DVI")))
                .DP(Integer.valueOf(productSpecifications.get("Display Port")))
                .HDMI(Integer.valueOf(productSpecifications.get("HDMI")))

                .build();
    }

    @Override
    public PSU createPsu(Elements productHtml, HashMap<String, String> productSpecifications) {
        return PSU.builder()

                .title(productHtml.select("[itemprop=name]").text())
                .rating(productHtml.select("[itemprop=aggregateRating]").text())
                .producer(productSpecifications.get("Producer"))
                .image(productHtml.select("[itemprop=image]").attr("src"))
                .description(productHtml.select("[itemprop=description]").text())

                .watt(Integer.valueOf(productSpecifications.get("Watt")))
                .size(productSpecifications.get("Size"))
                .efficiencyRating(productSpecifications.get("Efficiency Rating"))
                .pcie6(Integer.valueOf(productSpecifications.get("PCI-E cables 6-pin")))
                .pcie8(Integer.valueOf(productSpecifications.get("PCI-E cables 8-pin")))
                .build();
    }

    /*{Timings=16-18-18-36, EAN=0843591070478, Producer=Corsair, Year=, Ram Type=DDR4-3200,
    Sticks=2, Size=16 GB, UPC=, MPN=CMK16GX4M2B3200C16R, Clock=3200, Product Page=}
     */
    @Override
    public Ram createRam(Elements productHtml, HashMap<String, String> productSpecifications) {
        return null;
    }

    /*{Timings=16-18-18-36, EAN=0843591070478, Producer=Corsair, Year=, Ram Type=DDR4-3200, Sticks=2,
    Size=16 GB, UPC=, MPN=CMK16GX4M2B3200C16R, Clock=3200, Product Page=}
     */
    @Override
    public SSD createSsd(Elements productHtml, HashMap<String, String> productSpecifications) {
        return null;
    }

}