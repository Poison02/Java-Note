package cdu.zch.rpc.core.client;

import cdu.zch.rpc.core.common.RpcInvocation;
import cdu.zch.rpc.core.common.RpcProtocol;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import static cdu.zch.rpc.core.common.cache.CommonClientCache.*;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 客户端和服务端之间的数据都是以RpcProtocol对象作为基本协议进行的交互
        RpcProtocol rpcProtocol = (RpcProtocol) msg;
        // 这里是传输参数更为详细的RpcInvocation对象字节数组。
        byte[] reqContent = rpcProtocol.getContent();
        String json = new String(reqContent, 0, reqContent.length);
        RpcInvocation rpcInvocation = JSON.parseObject(json, RpcInvocation.class);
        // 通过之前发送的uuid来注入匹配的响应数值
        if (!RESP_MAP.containsKey(rpcInvocation.getUuid())) {
            throw new IllegalArgumentException("Server response is error");
        }
        // 将请求的响应结构放入一个Map集合中，集合的key就是uuid，这个uuid在发送请求之前就已经初始化好了，所以只需要起一个线程在后台遍历这个map，查看对应的key是否有相应即可。
        // uuid何时放入到map？其实放入的操作我将它封装到了代理类中进行实现，
        RESP_MAP.put(rpcInvocation.getUuid(), rpcInvocation);
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }
}
