package cdu.zch.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @author Zch
 * @data 2023/6/25
 **/
public class TestSlice {

    public static void main(String[] args) {
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        byteBuf.writeBytes(new byte[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'});
        TestByteBuf.log(byteBuf);

        // 在切片过程中，没有发生数据复制
        ByteBuf f1 = byteBuf.slice(0, 5);
        TestByteBuf.log(f1);
        f1.setByte(0, 'b');
        TestByteBuf.log(f1);
        TestByteBuf.log(byteBuf);

        System.out.println("释放内存");
        byteBuf.release();
        TestByteBuf.log(f1);
    }

}
