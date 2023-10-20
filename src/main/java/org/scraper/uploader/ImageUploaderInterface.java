package org.scraper.uploader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.scraper.DataScraper;
import org.scraper.compresser.ImageCompressor;
import org.scraper.models.Component.Component;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ImageUploaderInterface {
    final ObjectMapper objectMapper = new ObjectMapper();
    final DataScraper dataScraper = new DataScraper();
    final OkHttpClient okHttpClient = new OkHttpClient()
            .newBuilder()
            .build();

    final ImageCompressor imageCompressor = new ImageCompressor();

    final String imgBbPostUri = "https://api.imgbb.com/1/upload?key=" + getImgBbApiKey();
    final String imgurPostUri = "https://api.imgur.com/3/image";
    private final String envFilePath = "src/main/resources/key.env";

    public void setHostedUrl(URL hostedUrl, URL url, String endpoint) {
        List<Component> databaseList = dataScraper.readJsonDatabaseURL(endpoint);
        System.out.println("Database list fetched with : " + databaseList.size() + " components");

        for (Component component: databaseList) {
            replaceImageSource(component, hostedUrl, url);
        }

        dataScraper.writeToJsonDatabase(databaseList, endpoint);
        System.out.println("Written to database.");
    }
    public void replaceImageSource(Component component, URL hostedImageUrl , URL url) {
         String imageUrl = urlToString(url);
        if (imageNotHosted(imageUrl) && component.getImage().get(0).equals(imageUrl)) {
            component.setImage(new ArrayList<>(List.of(urlToString(hostedImageUrl))));
            System.out.println("Replaced image src component with ID: " + component.getId() + "\nwith URL: " + hostedImageUrl);
        }
    }

    public void setHostedUrls(ArrayList<String> hostedUrls, URL url, String endpoint) {
        List<Component> databaseList = dataScraper.readJsonDatabaseURL(endpoint);
        System.out.println("Database list fetched with : " + databaseList.size() + " components");

        for (Component component: databaseList) {
            replaceImageSources(component, hostedUrls, url);
        }

        dataScraper.writeToJsonDatabase(databaseList, endpoint);
        System.out.println("Written to database.");
    }

    public void replaceImageSources(Component component, ArrayList<String> hostedImageSourceSet, URL url) {
        String imageUrl = urlToString(url);
        if (imageNotHosted(imageUrl) && component.getImage().get(0).equals(imageUrl)) {
            component.setImage(hostedImageSourceSet);
            System.out.println("Replaced image src component with ID: " + component.getId() + "\nwith URL: " + hostedImageSourceSet);
        }
    }
    public List<URL> getImageUrls(String endpoint) {
        return dataScraper
                .readJsonDatabaseURL(endpoint)
                .stream()
                .map(this::nonHostedUrls)
                .toList();
    }

    public List<ArrayList<String>> getStringImageUrls(String endpoint) {
        return dataScraper
                .readJsonDatabaseURL(endpoint)
                .stream()
                .map(Component::getImage)
                .toList();
    }

    public UUID getUUID(String url, String endpoint) {
        return dataScraper
                .readJsonDatabaseURL(endpoint)
                .stream()
                .map((component -> {
                    if (component.getImage().get(0).equals(url)) {
                        return component.getId();
                    }
                    return null;
                }))
                .filter(Objects::isNull)
                .findFirst().get();
    }

    public boolean imageNotHosted(String imageUrl){
        return !(imageUrl.contains("https://i.ibb.co") || imageUrl.contains("imgur"));
    }
    public String getImgBbApiKey() {
        return Dotenv.configure().filename(envFilePath).load().get("IMGBB_KEY");
    }
    public String getTinifyKey() {
        return Dotenv.configure().filename(envFilePath).load().get("TINIFY_KEY");
    }
    public String getImgurKey(String version) {
        return Dotenv.configure().filename(envFilePath).load().get("CLIENT_ID_IMGUR" + version);
    }
    public String getAccessKey() {
        return Dotenv.configure().filename(envFilePath).load().get("AMAZON_SECRET_KEY");
    }
    public String getSecretAccessKey() {
        return Dotenv.configure().filename(envFilePath).load().get("AMAZON_SECRET_ACCESS_KEY");
    }

    String urlToString(URL url) {
         return url.toString();
    }
    private URL nonHostedUrls(Component component) {
        String imageUrl = component.getImage().get(0);
        if (imageNotHosted(imageUrl)) {
            try {
                return new URL(imageUrl);
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    public String getImagePath() {
        return imageCompressor.getImagePath();
    }

    public JsonNode executeHttpClientCall(Request request) throws IOException {
        Response response = okHttpClient.newCall(request).execute();
        String jsonResponse = Objects.requireNonNull(response.body()).string();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        return jsonNode;
    }

    public JsonNode getJsonNodeData(JsonNode jsonNode) {
        return jsonNode.path("data");
    }
}
