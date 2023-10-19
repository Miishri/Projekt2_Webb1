package org.scraper;

import org.scraper.compresser.ImageCompressor;
import org.scraper.compresser.TinifyCompressor;
import org.scraper.uploader.AmazonStorageUploader;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

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
