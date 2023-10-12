package org.scraper.compresser;

import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class ImageCompressor {
    private final String path = System.getProperty("user.home") + "/Desktop/image.png";
    public static String compressImage(ImageCompressor imageCompressor, URL url) throws IOException {
        imageCompressor.writeOptimizedImage(url);

        String encodedBase64 = imageCompressor.encodeImageBase64();

        imageCompressor.deleteImage();

        return encodedBase64;
    }

    public void writeOptimizedImage(URL url) throws IOException {
        PngImage inputImage = new PngImage(getTempImage(url));
        PngImage pngImage = optimizeImage(inputImage);

        System.out.println(inputImage.getImageData().length + " shrank to " + pngImage.getImageData().length);

        pngImage.writeDataOutputStream(Files.newOutputStream(Paths.get(path)));
    }

    public void optimizeImageWithCooBird() throws IOException {
        URL url = new URL("https://images2.productserve.com/?w=200&h=200&bg=white&trim=5&t=letterbox&url=ssl%3Acdn-reichelt.de%2Fbilder%2Fweb%2Fxxl_ws%2FE200%2FAMD_R5-5600X_01.png&feedId=34189&k=88484424d74235f15c4e85174c4c26372188390e");
        BufferedImage image = ImageIO.read(url);

        File output = new File(path);

        Thumbnails.of(image).scale(1).outputQuality(0.5).outputFormat("png").toFile(output);
    }

    public PngImage optimizeImage(PngImage inputImage) throws IOException {
        PngOptimizer optimizer = new PngOptimizer();
        return optimizer.optimize(inputImage);
    }

    public String encodeImageBase64() throws IOException {
        return Base64.getEncoder().encodeToString(getImageDate());
    }

    public byte[] getImageDate() throws IOException {
        return Files.readAllBytes(Path.of(path));
    }

    public ByteArrayInputStream getTempImage(URL url) throws IOException {
        BufferedImage image = ImageIO.read(url);
        ByteArrayOutputStream arrayInputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", arrayInputStream);
        return new ByteArrayInputStream(arrayInputStream.toByteArray());
    }

    public void deleteImage() {
        File file = new File(path);
        file.delete();
    }
    public void writeUrlImage(BufferedImage image) throws IOException {
        File file = new File(path);
        ImageIO.write(image, "png", file);
    }
}
