package com.zch.some_cool_media_library;

import java.util.HashMap;

/**
 * 远程服务接口
 * @author Zch
 * @date 2023/8/4
 **/
public interface ThirdPartyYouTubeLib {

    HashMap<String, Video> popularVideos();

    Video getVideo(String videoId);

}
