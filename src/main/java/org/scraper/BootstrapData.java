package org.scraper;

import org.scraper.uploader.AmazonStorageUploader;

import java.io.IOException;

public class BootstrapData {

    public static void main(String[] args) throws IOException {
        bootstrap();
    }

    public static void bootstrap() throws IOException {
        DataScraper dataScraper = new DataScraper();

        AmazonStorageUploader amazonStorageUploader = new AmazonStorageUploader();
        amazonStorageUploader.uploadAllImages();
    }
}
