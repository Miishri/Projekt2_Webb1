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
import java.util.HashMap;


public class DataScraper implements ComponentFactory {
    private static final String URL = "https://www.pc-kombo.com/us/components";
    private final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
       DataScraper dataScraper = new DataScraper();
       ArrayList<Component> comp = dataScraper.getComponent(CPU.endpoint);
    }

    public ArrayList<Component> getComponent(String endpoint) {
        ArrayList<Component> CPULinkList = new ArrayList<>();


        try {
            Document domDocument = Jsoup.connect(URL + endpoint).get();
            Elements hardwareList = domDocument.select("#hardware a[href]");

            for (Element hardware: hardwareList) {
                String hardwareReference = hardware.attr("href");

                if (hardwareReference.contains("pc-kombo")) {
                    System.out.println("Fetching from URL: " +  hardwareReference);

                    Elements productHtml = Jsoup.connect(hardwareReference)
                            .get()
                            .select("#product");

                    Component component = componentCheck(productHtml, mapSpecificationsWithKeys(productHtml), endpoint);
                    CPULinkList.add(component);

                    if (CPULinkList.size() > 10) {
                        writeToJsonDatabase(CPULinkList, endpoint);
                        return CPULinkList;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error occurred while fetching CPUs:" + e);
        }


         return CPULinkList;
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
        return null;
    }

    @Override
    public Cases createCases(Elements productHtml, HashMap<String, String> productSpecifications) {
        return null;
    }

    @Override
    public CpuCooler createCpuCooler(Elements productHtml, HashMap<String, String> productSpecifications) {
        return null;
    }

    @Override
    public Monitor createMonitor(Elements productHtml, HashMap<String, String> productSpecifications) {
        return null;
    }

    @Override
    public Motherboard createMotherboard(Elements productHtml, HashMap<String, String> productSpecifications) {
        return null;
    }

    @Override
    public PSU createPsu(Elements productHtml, HashMap<String, String> productSpecifications) {
        return null;
    }

    @Override
    public Ram createRam(Elements productHtml, HashMap<String, String> productSpecifications) {
        return null;
    }

    @Override
    public SSD createSsd(Elements productHtml, HashMap<String, String> productSpecifications) {
        return null;
    }

}