package cdu.zch.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println("服务端接收收数据：" + byteBuf.toString(StandardCharsets.UTF_8));

        // 写数据到客户端
        System.out.println(new Date() + "服务端写出数据");
        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "Hello！！！".getBytes(StandardCharsets.UTF_8);

        ByteBuf buf = ctx.alloc().buffer();

        buf.writeBytes(bytes);

        return buf;
    }
}
