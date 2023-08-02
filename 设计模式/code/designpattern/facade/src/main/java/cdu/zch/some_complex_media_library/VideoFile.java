package cdu.zch.some_complex_media_library;

/**
 * @author Zch
 * @date 2023/8/2
 **/
public class VideoFile {

    private String name;
    private String codecType;

    public VideoFile(String name) {
        this.name = name;
        this.codecType = name.substring(name.indexOf(".") + 1);
    }

    public String getCodecType() {
        return codecType;
    }

    public String getName() {
        return name;
    }


}
