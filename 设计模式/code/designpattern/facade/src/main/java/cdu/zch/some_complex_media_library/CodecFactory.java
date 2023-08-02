package cdu.zch.some_complex_media_library;

/**
 * @author Zch
 * @date 2023/8/2
 **/
public class CodecFactory {

    public static Codec extract(VideoFile file) {
        String type = file.getCodecType();
        if (type.equals("mp4")) {
            System.out.println("CodecFactory: extracting mpeg audio...");
            return new MPEG4CompressionCodec();
        } else {
            System.out.println("CodecFactory: extracting ogg video...");
            return new OggCompressionCodec();
        }
    }

}
