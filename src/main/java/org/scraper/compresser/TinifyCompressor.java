package org.scraper.compresser;

import com.tinify.Options;
import com.tinify.Source;
import com.tinify.Tinify;
import org.scraper.uploader.ImageUploaderInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TinifyCompressor extends ImageCompressor {

    private static final ImageUploaderInterface imageUploaderInterface = new ImageUploaderInterface();

    public byte[] getCompressedImageBufferedData(String url) throws IOException {
        Tinify.setKey(imageUploaderInterface.getTinifyKey());

        Source source = Tinify.fromUrl(url);
        source.toFile(getImagePath());

        byte[] sourceData = Files.readAllBytes(Paths.get(getImagePath()));
        byte[] resultData = Tinify.fromBuffer(sourceData).toBuffer();

        deleteCurrentWrittenImage();

        return resultData;
    }

    public byte[] getCompressedImageResizedBufferedData(byte[] compressedImage) throws IOException {
        Source source = Tinify.fromBuffer(compressedImage);
        Options options = new Options()
                .with("method", "scale")
                .with("height", 150);

        Source resizedCompressedImage = source.resize(options);
        resizedCompressedImage.toFile(getImagePath());

        byte[] sourceData = Files.readAllBytes(Paths.get(getImagePath()));
        byte[] resultData = Tinify.fromBuffer(sourceData).toBuffer();

        deleteCurrentWrittenImage();

        return resultData;
    }
}