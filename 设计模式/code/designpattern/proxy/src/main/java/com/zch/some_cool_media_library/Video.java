package com.zch.some_cool_media_library;

/**
 * 视频文件
 * @author Zch
 * @date 2023/8/4
 **/
public class Video {

    public String id;
    public String title;
    public String data;

    Video(String id, String title) {
        this.id = id;
        this.title = title;
        this.data = "Random video.";
    }

}
