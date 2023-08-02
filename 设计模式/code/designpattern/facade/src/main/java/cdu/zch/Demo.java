package cdu.zch;

import cdu.zch.facade.VideoConversionFacade;

import java.io.File;

/**
 * @author Zch
 * @date 2023/8/2
 **/
public class Demo {

    public static void main(String[] args) {
        VideoConversionFacade converter = new VideoConversionFacade();
        File mp4Video = converter.convertVideo("youtubevideo.ogg", "mp4");
        // ...
    }

}
