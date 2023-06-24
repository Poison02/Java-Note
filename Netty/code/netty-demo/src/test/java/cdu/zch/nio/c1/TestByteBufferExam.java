package cdu.zch.nio.c1;

import java.nio.ByteBuffer;

/**
 * @author Zch
 * @data 2023/6/21
 **/
public class TestByteBufferExam {

    public static void main(String[] args) {
         /*
         网络上有多条数据发送给服务端，数据之间使用 \n 进行分隔
         但由于某种原因这些数据在接收时，被进行了重新组合，例如原始数据有3条为
             Hello,world\n
             I'm zhangsan\n
             How are you?\n
         变成了下面的两个 byteBuffer (黏包，半包)
             Hello,world\nI'm zhangsan\nHo
             w are you?\n
         现在要求你编写程序，将错乱的数据恢复成原始的按 \n 分隔的数据
         */
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        // 先切换到读模式
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 通过 \n 分割
            if (source.get(i) == '\n') {
                int length = i - source.position() + 1;
                // 把这条完整消息存入新的buffer
                ByteBuffer buffer = ByteBuffer.allocate(length);
                // 从source读，向target写
                for (int j = 0; j < length; j++) {
                    buffer.put(source.get());
                }
                ByteBufferUtil.debugAll(buffer);
            }
        }
        // 切换回写模式，但是不能丢掉内容
        source.compact();
    }

}
