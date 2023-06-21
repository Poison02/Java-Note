package cdu.zch.netty.c1;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static cdu.zch.netty.c1.ByteBufferUtil.debugAll;

/**
 * @author Zch
 * @data 2023/6/21
 **/
public class TestByteBufferString {

    public static void main(String[] args) {
        // 1. 字符串转为ByteBuffer 但是没有自动转为读模式，也就是没有buffer.flip()
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("hello".getBytes());
        debugAll(buffer);

        // 2. Charset
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer1);

        // 3. wrap
        ByteBuffer buffer2 = ByteBuffer.wrap("hello".getBytes());
        debugAll(buffer2);

        // ByteBuffer转为String
        CharBuffer decode = StandardCharsets.UTF_8.decode(buffer1);
        System.out.println(decode);
    }

}
