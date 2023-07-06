package cdu.zch.rpc.core.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol msg, ByteBuf out) throws Exception {
        // 魔法数
        out.writeShort(msg.getMagicNumber());
        // 传输数据长度
        out.writeInt(msg.getContentLength());
        // 传输数据
        out.writeBytes(msg.getContent());
    }
}
