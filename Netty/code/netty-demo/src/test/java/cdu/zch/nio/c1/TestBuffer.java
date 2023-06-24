package cdu.zch.nio.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Zch
 * @data 2023/6/21
 **/
@Slf4j
// 测试Buffer
public class TestBuffer {

    public static void main(String[] args) {
        // FileChannel
        // 1. 输入输出流
        // 2. RandomAccessFile
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            // 准备好一个缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                // 从Channel中读取数据，向缓冲区写入即向buffer写入
                int len = channel.read(buffer);
                log.debug("读取到的字节数{}", len);
                if (len == -1) { // 没有内容了
                    break;
                }
                // 打印buffer内容
                buffer.flip(); // 切换至读模式
                while (buffer.hasRemaining()) { // 检查是否还有未读的数据
                    byte b = buffer.get();
                    log.debug("实际字节{}", (char)b);
                }
                // 切换为写模式
                buffer.clear();
            }

        } catch (IOException e) {
        }
    }

}
