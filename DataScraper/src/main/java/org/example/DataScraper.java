package org.example;

import org.example.models.CPU;
import org.example.models.Component;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class DataScraper {
    private static final String URL = "https://www.pc-kombo.com/us/components";
    private static final StringBuilder builder = new StringBuilder();
    public static void main(String[] args) {
        getComponent(CPU.endpoint);

    }

    private static void builderLog(String[] buildingData) {
        for (String dataNode: buildingData) {
            builder.append(dataNode);
        }
        System.out.println(builder);
        builder.delete(0, builder.length());
    }

    private static ArrayList<?> getComponent(String endpoint) {
        builderLog(new String[]{"Fetching from URL ", URL + endpoint});
        ArrayList<Component> CPULinkList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL + endpoint).get();
            Elements hardwareLinks = doc.select("#hardware a[href]");
            for (Element link: hardwareLinks) {
                String href = link.attr("href");
                if (href.contains("pc-kombo")) {

                    Document hrefDoc = Jsoup.connect(href).get();
                    Elements elements = hrefDoc.select("#product .card-body dd");
                    ArrayList<String> elementList = new ArrayList<>();
                    elements.forEach(element -> {
                        if (element.hasText() && !element.text().contains(">")) {
                            elementList.add(element.text());
                        }
                    });
                    System.out.println(elementList);
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred while getting CPUs:" + e);
        }

        System.out.println(CPULinkList);
        return CPULinkList;
    }
}