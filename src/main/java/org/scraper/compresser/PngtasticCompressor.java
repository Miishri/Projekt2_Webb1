package org.scraper.compresser;

import com.googlecode.pngtastic.core.PngImage;
import com.googlecode.pngtastic.core.PngOptimizer;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PngtasticCompressor extends ImageCompressor {

    public static String compressImage(PngtasticCompressor pngtasticCompressor, URL url) throws IOException {
        pngtasticCompressor.writeOptimizedPngImage(url);

        String encodedBase64 = pngtasticCompressor.encodeImageBase64();

        pngtasticCompressor.deleteCurrentWrittenImage();

        return encodedBase64;
    }


    public PngImage optimizePngImage(PngImage inputImage) throws IOException {
        PngOptimizer optimizer = new PngOptimizer();
        return optimizer.optimize(inputImage);
    }

    public void writeOptimizedPngImage(URL url) throws IOException {
        PngImage inputImage = new PngImage(getTemporaryByteArrayImage(url));
        PngImage pngImage = optimizePngImage(inputImage);

        System.out.println(inputImage.getImageData().length + " shrank to " + pngImage.getImageData().length);

        pngImage.writeDataOutputStream(Files.newOutputStream(Paths.get(getImagePath())));
    }
}
