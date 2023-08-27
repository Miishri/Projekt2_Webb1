package org.example;

import org.example.models.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;


public class DataScraper {
    private static final String URL = "https://www.pc-kombo.com/us/components";

    public ArrayList<?> getComponent(String endpoint) {
        System.out.println("Fetching from URL " +  URL + endpoint);
        ArrayList<Component> CPULinkList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL + endpoint).get();
            Elements hardwareLinks = doc.select("#hardware a[href]");
            for (Element link: hardwareLinks) {
                String href = link.attr("href");
                if (href.contains("pc-kombo")) {
                    Document hrefDoc = Jsoup.connect(href).get();
                    Elements elements = hrefDoc.select("#product");

                    HashMap<String, String> elementList = new HashMap<>();
                    ArrayList<String> dtList = new ArrayList<>();
                    ArrayList<String> ddList = new ArrayList<>();
                    elements.select(".card-body dl").forEach(element -> {
                        element.select("dt").forEach(title -> {
                            dtList.add(title.text());
                        });
                        element.select("dd").forEach(value -> {
                            ddList.add(value.text());
                        });
                    });

                    for (int i = 0; i < dtList.size(); i++) {
                        elementList.put(dtList.get(i), ddList.get(i));
                    }

                    System.out.println(elementList);

                    String title = elements.select("[itemprop=name]").text();
                    System.out.println(title);

                    String imageHref = elements.select("[itemprop=image]").attr("src");
                    System.out.println(imageHref);

                    String description = elements.select("[itemprop=description]").text();
                    System.out.println(description);

                    if (elements.select("[itemprop=aggregateRating]").hasText()) {
                        String aggregateRating = elements.select("[itemprop=aggregateRating]").text();
                        System.out.println(aggregateRating);
                    }


                    System.out.println(elementList);

                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred while fetching CPUs:" + e);
        }

         return CPULinkList;
    }
}