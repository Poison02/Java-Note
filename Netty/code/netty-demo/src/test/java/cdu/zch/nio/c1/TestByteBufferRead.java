package cdu.zch.nio.c1;

import java.nio.ByteBuffer;

import static cdu.zch.nio.c1.ByteBufferUtil.debugAll;

/**
 * @author Zch
 * @data 2023/6/21
 **/
public class TestByteBufferRead {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[] {'a', 'b', 'c', 'd'});
        buffer.flip();

        // rewind 从头开始读
        buffer.get(new byte[3]);
        debugAll(buffer);
        buffer.rewind();
        System.out.println((char) buffer.get());

        // mark & reset
        // mark 做一个而标记，记录position位置，reset将position重置到mark位置
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        buffer.mark(); // 加标记 索引2
        System.out.println((char) buffer.get());
        buffer.reset(); // 将position重置到索引2
        System.out.println((char) buffer.get());

        // get(i) 不会改变读索引的位置
        System.out.println((char) buffer.get(3));
        debugAll(buffer);
    }

}
