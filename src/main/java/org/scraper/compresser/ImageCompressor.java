package org.scraper.compresser;

import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.io.File;
import java.io.IOException;

public class ImageCompressor {
    private final String path = System.getProperty("user.home")  + "/image.png";
    public static String compressImage(ImageCompressor imageCompressor, URL url) throws IOException {
        imageCompressor.writeOptimizedImage(url);
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

    public void optimizeImageWithCooBird(String inputUrl) throws IOException {
        URL url = new URL(inputUrl);
        BufferedImage image = ImageIO.read(url);

        File output = new File(path);

        Thumbnails.of(image).scale(1).outputQuality(0.5).outputFormat("png").toFile(output);
    }

    public PngImage optimizeImage(PngImage inputImage) throws IOException {
        PngOptimizer optimizer = new PngOptimizer();
        return optimizer.optimize(inputImage);
    }

    public String encodeImageBase64() throws IOException {
        return Base64.getEncoder().encodeToString(getImageData());
    }

    public byte[] getImageData() throws IOException {
        return Files.readAllBytes(Path.of(path));
    }

    public ByteArrayInputStream getTempImage(URL url) throws IOException {
        BufferedImage image = ImageIO.read(url);
        ByteArrayOutputStream arrayInputStream = new ByteArrayOutputStream();
        ImageIO.write(transparency(image), "png", arrayInputStream);
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


    public BufferedImage transparency(BufferedImage bufferedImage) {
        Image imageWithTransparency = makeColorTransparent(bufferedImage, new Color(bufferedImage.getRGB(0, 0)));

        return imageToBufferedImage(imageWithTransparency);
    }
    private static BufferedImage imageToBufferedImage(final Image image) {
        final BufferedImage bufferedImage =
                new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return bufferedImage;
    }
    public static Image makeColorTransparent(final BufferedImage im, final Color color) {
        final ImageFilter filter = new RGBImageFilter() {
            public final int markerRGB = color.getRGB() | 0xFFFFFFFF;

            public int filterRGB(final int x, final int y, final int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) return 0x00FFFFFF & rgb;
                else return rgb;
            }
        };

        final ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
}