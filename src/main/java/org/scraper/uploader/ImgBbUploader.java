package org.scraper.uploader;

import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.scraper.compresser.PngtasticCompressor;
import org.scraper.models.*;

import java.io.IOException;
import java.net.URL;

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


        System.out.println("Created Request Body");

        Request request = new Request.Builder()
                .url(expirableImgPostUri)
                .addHeader("Content-Type", "multipart/form-data")
                .post(requestBody)
                .build();

        System.out.println("---------------------------------");
        System.out.println("IMAGE SET TO EXPIRE IN 90 SECONDS");
        System.out.println("---------------------------------");

        System.out.println("Created request with body: " + request.body());

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
