package org.scraper.uploader;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.*;
import org.scraper.compresser.ImageCompressor;
import org.scraper.compresser.TinifyCompressor;
import org.scraper.models.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.scraper.compresser.PngtasticCompressor.compressImage;

public class ImgurUploader extends ImageUploaderInterface{

    private final TinifyCompressor tinifyCompressor = new TinifyCompressor();
    private int count = 1;

    public static void main(String[] args) throws IOException {
        ImgurUploader imgurUploader = new ImgurUploader();
        imgurUploader.uploadAllImages();
    }

    public void uploadAllImages() throws IOException {
        uploadImages(GPU.endpoint);
        uploadImages(Monitor.endpoint);
        uploadImages(CPU.endpoint);
        uploadImages(Motherboard.endpoint);
        uploadImages(SSD.endpoint);
        uploadImages(Ram.endpoint);
    }

    public URL uploadImage(String base64) throws IOException {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", base64)
                .build();

        System.out.println("Created request Body: " + requestBody);

        Request request = new Request.Builder()
                .url(imgurPostUri)
                .addHeader("Content-Type", "multipart/form-data")
                .addHeader("Authorization", "Client-ID " + getImgurKey())
                .post(requestBody)
                .build();

        System.out.println("Created request: " + request);

        Response response = okHttpClient.newCall(request).execute();
        String jsonResponse = Objects.requireNonNull(response.body()).string();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        System.out.println(jsonResponse);

        System.out.println("JSON response fetched: " + jsonResponse);
        System.out.println("JSON node created: " + jsonNode.toPrettyString());
        return new URL(jsonNode.path("data").path("link").asText());
    }

    void uploadImages(String endpoint) throws IOException {
        for (ArrayList<String> url: getStringImageUrls(endpoint)) {
            if (url.size() == 1) {
                for (String stringImageUrl: url) {
                    System.out.println("----------------------"+ count +"-------------------");
                    System.out.println("Upload started for URL: " + url);
                    setHostedUrls(getSourceList(stringImageUrl), new URL(stringImageUrl), endpoint);
                    System.out.println("Upload successful for URL: " + url);
                    System.out.println("------------------------------------------");
                    count++;
                }
            }
        }
        count = 0;
    }

    private ArrayList<String> getSourceList(String url) throws IOException {
        byte[] compressedOriginal = tinifyCompressor.getCompressedImageBufferedData(url);
        byte[] compressedResized = tinifyCompressor.getCompressedImageResizedBufferedData(compressedOriginal);

        String originalUrl = urlToString(uploadImage(tinifyCompressor.encodeImageBase64(compressedOriginal)));
        String resizedUrl = urlToString(uploadImage(tinifyCompressor.encodeImageBase64(compressedOriginal)));
        return new ArrayList<>(List.of(originalUrl, resizedUrl));
    }
}
