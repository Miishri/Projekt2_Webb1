package org.scraper.compresser;

import com.tinify.Tinify;

import java.awt.*;
import java.awt.image.*;

public class ImageTransparency extends ImageCompressor {

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
