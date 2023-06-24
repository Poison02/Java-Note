package cdu.zch.nio.c1;

import java.nio.ByteBuffer;

/**
 * @author Zch
 * @data 2023/6/21
 **/
public class TestByteBufferAllocate {

    public static void main(String[] args) {
        // 这里的allocate不能动态调整
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
        /*
        class java.nio.HeapByteBuffer  Java堆内存 读写效率较低 会受到垃圾回收的影响
        class java.nio.DirectByteBuffer 直接内存 效率较高，少一个拷贝 使用系统内存不会受到垃圾回收的影响 分配效率较低 必须要释放
         */

    }

}
