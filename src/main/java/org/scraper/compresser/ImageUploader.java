package org.scraper.compresser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.scraper.DataScraper;
import org.scraper.models.*;
import org.scraper.models.Component.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ImageUploader {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final String postUri = "https://api.imgbb.com/1/upload?key=" + getApiKey();
    private final ImageCompressor imageCompressor = new ImageCompressor();
    private final DataScraper dataScraper = new DataScraper();
    private int count = 1;

    public static void main(String[] args) throws IOException {
        ImageUploader imageUploader = new ImageUploader();
        imageUploader.uploadImages(Monitor.endpoint);
        imageUploader.uploadImages(CPU.endpoint);
        imageUploader.uploadImages(GPU.endpoint);
        imageUploader.uploadImages(Motherboard.endpoint);
        imageUploader.uploadImages(SSD.endpoint);
        imageUploader.uploadImages(Ram.endpoint);

    }

    public void uploadImages(String endpoint) throws IOException {
        for (URL url: getImageUrls(endpoint)) {
            if (url != null) {
                System.out.println("----------------------"+ count +"-------------------");
                System.out.println("Upload started for URL: " + url);
                URL uploadedUrl = compressAndUpload(ImageCompressor.compressImage(imageCompressor, url));
                setHostedUrl(uploadedUrl, url, endpoint);
                System.out.println("Upload successful for URL: " + url);
                System.out.println("------------------------------------------");
                count++;
            }
        }
        count = 0;
    }

    public URL compressAndUpload(String base64) throws IOException {

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(postUri)).newBuilder();
        urlBuilder.addQueryParameter("key", getApiKey());

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                        .addFormDataPart("image", base64)
                                .build();

        System.out.println("Created request Body: " + requestBody);

        Request request = new Request.Builder()
                .url(postUri)
                .addHeader("Content-Type", "multipart/form-data")
                .post(requestBody)
                .build();

        System.out.println("Created request: " + request);

        Response response = okHttpClient.newCall(request).execute();
        String jsonResponse = Objects.requireNonNull(response.body()).string();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        System.out.println("JSON node created & JSON response fetched: " + jsonNode.path("data").toPrettyString());

        return new URL(jsonNode.path("data").path("url").asText());

    }
    public List<URL> getImageUrls(String endpoint) {
        List<Component> components = new DataScraper().readJsonDatabaseURL(endpoint);
        return components.stream().map((e) -> {
            String imageUrl = e.getImage();
            if (!imageUrl.contains("https://i.ibb.co")) {
                try {
                    return new URL(imageUrl);
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return null;
        }).toList();
    }

    public void setHostedUrl(URL hostedUrl, URL url, String endpoint) {
        List<Component> databaseList = dataScraper.readJsonDatabaseURL(endpoint);
        System.out.println("Database list fetched with : " + databaseList.size() + " components");

        for (Component component: databaseList) {
            replaceImageSrc(component, hostedUrl, url);
        }

        dataScraper.writeToJsonDatabase(databaseList, endpoint);
        System.out.println("Written to database.");
    }

    public void replaceImageSrc(Component component,URL hostedUrl ,URL url) {
        if (!component.getImage().equals("https://i.ibb.co") && component.getImage().equals(url.toString())) {
            component.setImage(hostedUrl.toString());
            System.out.println("Replaced image src component with ID: " + component.getId() + "\nwith URL: " + hostedUrl);
        }
    }

    private String getApiKey() {
        return Dotenv.configure().filename("src/main/resources/ImgBB.env").load().get("IMGBB_KEY");
    }
}
