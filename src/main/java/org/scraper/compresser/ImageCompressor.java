package org.scraper.compresser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class ImageCompressor {
    private final String imagePath = System.getProperty("user.home")  + "/image.jpeg";
    public String encodeImageBase64() throws IOException {
        return Base64.getEncoder().encodeToString(getImageByteData());
    }
    public String encodeImageBase64(byte[] byteData) throws IOException {
        return Base64.getEncoder().encodeToString(byteData);
    }
    public byte[] getImageByteData() throws IOException {
        return Files.readAllBytes(Path.of(imagePath));
    }
     public ByteArrayInputStream getTemporaryByteArrayImage(URL url) throws IOException {
        BufferedImage image = ImageIO.read(url);
        ByteArrayOutputStream arrayInputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jepg", arrayInputStream);
        return new ByteArrayInputStream(arrayInputStream.toByteArray());
    }
    public void deleteCurrentWrittenImage() {
        File file = new File(imagePath);
        file.delete();
    }

    public void writeBufferedImageToPath(BufferedImage bufferedImage) throws IOException {
        File file = new File(getImagePath());
        ImageIO.write(bufferedImage, "jpeg", file);
    }
    public void writeBufferedImageToPathWebp(BufferedImage bufferedImage, String path) throws IOException {
        File file = new File(path);
        ImageIO.write(bufferedImage, "webp", file);
    }

    public String getImagePath() {
        return imagePath;
    }

}
