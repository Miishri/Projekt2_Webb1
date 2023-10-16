package org.scraper.uploader;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.*;
import org.scraper.compresser.ImageCompressor;
import org.scraper.compresser.PngtasticCompressor;
import org.scraper.compresser.TinifyCompressor;
import org.scraper.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static org.scraper.compresser.PngtasticCompressor.compressImage;

public class ImgBbUploader extends ImageUploaderInterface{

    private final PngtasticCompressor pngtasticCompressor = new PngtasticCompressor();
    private int count = 1;

    public static void main(String[] args) throws IOException {
        ImgBbUploader imgBbUploader = new ImgBbUploader();
        imgBbUploader.uploadAllImages();
    }

    public void uploadAllImages() throws IOException {
        uploadImages(GPU.endpoint);
        uploadImages(Monitor.endpoint);
        uploadImages(CPU.endpoint);
        uploadImages(Motherboard.endpoint);
        uploadImages(SSD.endpoint);
        uploadImages(Ram.endpoint);
    }

    public URL compressAndUpload(String base64) throws IOException {

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", base64)
                .build();


        System.out.println("Created request Body: " + requestBody);

        Request request = new Request.Builder()
                .url(imgBbPostUri)
                .addHeader("Content-Type", "multipart/form-data")
                .post(requestBody)
                .build();

        System.out.println("Created request: " + request);

        JsonNode jsonNode = getJsonNodeData(executeHttpClientCall(request));

        System.out.println("JSON node created & JSON response fetched: " + jsonNode.path("data").toPrettyString());

        return new URL(jsonNode.path("url").asText());
    }

    void uploadImages(String endpoint) throws IOException {
        for (URL url: getImageUrls(endpoint)) {
            if (url != null) {
                System.out.println("----------------------"+ count +"-------------------");
                System.out.println("Upload started for URL: " + url);
                URL uploadedUrl = compressAndUpload(PngtasticCompressor.compressImage(pngtasticCompressor, url));                setHostedUrl(uploadedUrl, url, endpoint);
                setHostedUrl(uploadedUrl, url, endpoint);
                System.out.println("Upload successful for URL: " + url);
                System.out.println("------------------------------------------");
                count++;
            }
        }
        count = 0;
    }

}
