package cdu.zch.encoding;

import java.util.Arrays;

/**
 * base64编码解码
 * @author Zch
 * @data 2023/6/15
 **/
public class Base64 {

    public static String encodeBase64(String input) {
        return java.util.Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decodeBase64(String input) {
//        return Arrays.toString(java.util.Base64.getDecoder().decode(input.getBytes()));
        return new String(java.util.Base64.getDecoder().decode(input.getBytes()));
    }

}
