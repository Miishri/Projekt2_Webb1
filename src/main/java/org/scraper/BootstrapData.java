package org.scraper;

import org.scraper.uploader.AmazonStorageUploader;

import java.io.IOException;

public class BootstrapData {

    public static void main(String[] args) throws IOException {
        DataScraper dataScraper = new DataScraper();
        dataScraper.bootstrapData();

        AmazonStorageUploader amazonStorageUploader = new AmazonStorageUploader();
        amazonStorageUploader.uploadAllImages();
    }
}
