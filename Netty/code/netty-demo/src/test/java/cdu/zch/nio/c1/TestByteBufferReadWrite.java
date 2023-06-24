package cdu.zch.nio.c1;

import java.nio.ByteBuffer;

import static cdu.zch.nio.c1.ByteBufferUtil.debugAll;

/**
 * @author Zch
 * @data 2023/6/21
 **/
public class TestByteBufferReadWrite {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        buffer.put((byte) 0x61); // 'a'
        debugAll(buffer);
        buffer.put(new byte[]{0x62, 0x63, 0x64});
        debugAll(buffer);
        // System.out.println(buffer.get()); // 此时调用get只能读取0
        // 转为读模式
        buffer.flip();
        System.out.println(buffer.get());
        debugAll(buffer);
        buffer.compact();
        debugAll(buffer);

        buffer.put(new byte[]{0x65, 0x66});
        debugAll(buffer);
    }

}
