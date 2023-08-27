package org.scraper;

import org.scraper.models.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.scraper.models.Component.Component;
import org.scraper.models.Component.ComponentFactory;

import java.util.ArrayList;
import java.util.HashMap;


public class DataScraper implements ComponentFactory {
    private static final String URL = "https://www.pc-kombo.com/us/components";

    public ArrayList<Component> getComponent(String endpoint) {
        ArrayList<Component> CPULinkList = new ArrayList<>();
        try {
            Document domDocument = Jsoup.connect(URL + endpoint).get();
            Elements hardwareList = domDocument.select("#hardware a[href]");

            for (Element hardware: hardwareList) {
                String hardwareReference = hardware.attr("href");

                System.out.println("Fetching from URL: " +  hardwareReference);

                if (hardwareReference.contains("pc-kombo")) {
                    Elements productHtml = Jsoup.connect(hardwareReference)
                            .get()
                            .select("#product");

                    CPULinkList.add(componentCheck(productHtml, mapSpecificationsWithKeys(productHtml), endpoint));

                    return CPULinkList;
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

    //https://www.baeldung.com/java-serialization-approaches
}