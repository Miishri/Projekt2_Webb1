package org.scraper.uploader;

import com.tinify.Options;
import com.tinify.Source;
import com.tinify.Tinify;
import org.scraper.compresser.ImageCompressor;
import org.scraper.compresser.TinifyCompressor;
import org.scraper.models.*;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.core.Response;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AmazonStorageUploader extends ImageUploaderInterface {

    private final TinifyCompressor tinifyCompressor = new TinifyCompressor();

    private String region = "eu-north-1";
    private String bucketName = "mydatastoragebucket";
    private int count = 1;

    public static void main(String[] args) throws IOException {
        AmazonStorageUploader amazonStorageUploader = new AmazonStorageUploader();
        amazonStorageUploader.uploadAllImages();
    }
    public void uploadAllImages() throws IOException {
        uploadImages(GPU.endpoint);
        uploadImages(Monitor.endpoint);
        uploadImages(CPU.endpoint);
        uploadImages(Motherboard.endpoint);
        uploadImages(SSD.endpoint);
        uploadImages(Ram.endpoint);
    }

    URL uploadImage(String compressedType) throws MalformedURLException {
        AwsCredentials credentials = AwsBasicCredentials.create(getAccessKey(), getSecretAccessKey());
        AwsCredentialsProvider provider = StaticCredentialsProvider.create(credentials);
        S3Client s3Client = S3Client.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(provider)
                .build();

        String uuidImageKey = "component-" + compressedType + UUID.randomUUID() + ".jpeg";

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .contentType("image/jpeg")
                .bucket(bucketName)
                .key(uuidImageKey)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(new File(getImagePath())));

        return new URL("https://" + bucketName + ".s3.amazonaws.com/" + uuidImageKey);
    }

    /*  Future fix for bugs */
    public void uploadImageTinify(String compressedType) throws IOException {
        Source source = Tinify.fromFile(getImagePath());
        Options amazonS3Uploader = new Options()
                .with("service", "s3")
                .with("aws_access_key_id", getAccessKey())
                .with("aws_secret_access_key", getSecretAccessKey())
                .with("region", "eu-north-1")
                .with("path", bucketName + "/ComponentProductImages/"+ "component-" + compressedType + UUID.randomUUID() + ".jpeg");

        source.store(amazonS3Uploader);
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
        imageCompressor.writeBufferedImageToPath(ImageIO.read(new ByteArrayInputStream(compressedOriginal)));
        String originalUrl = urlToString(uploadImage("original"));
        imageCompressor.deleteCurrentWrittenImage();

        BufferedImage compressedResized = ImageIO.read(new ByteArrayInputStream(tinifyCompressor.getCompressedImageResizedBufferedData(compressedOriginal)));
        imageCompressor.writeBufferedImageToPath(compressedResized);
        String resizedUrl = urlToString(uploadImage("resized"));
        imageCompressor.deleteCurrentWrittenImage();

        return new ArrayList<>(List.of(originalUrl, resizedUrl));
    }
}
