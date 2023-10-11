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

    private final String postUri = "https://api.imgbb.com/1/upload?key=" + getApiKey();
    private final ImageCompressor imageCompressor = new ImageCompressor();

    public static void main(String[] args) throws IOException {
        ImageUploader imageUploader = new ImageUploader();
        imageUploader.uploadImages(CPU.endpoint);
    }

    public void uploadImages(String endpoint) throws IOException {
        for (URL url: getImageUrls(endpoint)) {
            if (url != null) {
                URL uploadedUrl = compressAndUpload(ImageCompressor.compressImage(imageCompressor, url));
                setHostedUrl(uploadedUrl, url, endpoint);
            }
        }
    }

    public URL compressAndUpload(String base64) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();

        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(postUri)).newBuilder();
        urlBuilder.addQueryParameter("key", getApiKey());

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                        .addFormDataPart("image", base64)
                                .build();



        Request request = new Request.Builder()
                .url(postUri)
                .addHeader("Content-Type", "multipart/form-data")
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String jsonResponse = Objects.requireNonNull(response.body()).string();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        return new URL(jsonNode.path("data").path("url").asText());

    }
    public List<URL> getImageUrls(String endpoint) {
        return new DataScraper().readJsonDatabaseURL(endpoint).stream().map((e) -> {
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
        DataScraper dataScraper = new DataScraper();
        List<Component> components = dataScraper.readJsonDatabaseURL(endpoint).stream().map((e) -> e.getImage().equals(url.toString())
                ? Component.builder().image(hostedUrl.toString()).build()
                : e).toList();

        dataScraper.writeToJsonDatabase(components, endpoint);
    }

    private String getApiKey() {
        return Dotenv.configure().filename("src/main/resources/ImgBB.env").load().get("IMGBB_KEY");
    }
}
