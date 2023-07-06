package cdu.zch.rpc.core.client;

import cdu.zch.rpc.core.common.RpcDecoder;
import cdu.zch.rpc.core.common.RpcEncoder;
import cdu.zch.rpc.core.common.RpcInvocation;
import cdu.zch.rpc.core.common.RpcProtocol;
import cdu.zch.rpc.core.common.config.ClientConfig;
import cdu.zch.rpc.core.proxy.jdk.JDKProxyFactory;
import cdu.zch.rpc.interfaces.DataService;
import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static cdu.zch.rpc.core.common.cache.CommonClientCache.*;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static EventLoopGroup clientGroup = new NioEventLoopGroup();

    private ClientConfig clientConfig;

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    public RpcReference startClientApplication() throws InterruptedException {
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(clientGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 管道中初始化一些逻辑，这里包含了上边所说的编解码器和客户端响应类
                        ch.pipeline()
                                .addLast(new RpcEncoder())
                                .addLast(new RpcDecoder())
                                .addLast(new ClientHandler());
                    }
                });

        // 常规的链接netty服务端
        ChannelFuture channelFuture = bootstrap.connect(clientConfig.getServerAddr(), clientConfig.getPort()).sync();
        LOGGER.debug("====客户端启动====");
        this.startClient(channelFuture);
        // 这里注入了一个代理工厂
        /*
        客户端首先需要通过一个代理工厂获取被调用对象的代理对象，
        然后通过代理对象将数据放入发送队列，
        最后会有一个异步线程将发送队列内部的数据一个个地发送给到服务端，
        并且等待服务端响应对应的数据结果。
         */
        RpcReference reference = new RpcReference(new JDKProxyFactory());
        return reference;
    }

    /**
     * 开启发送线程，专门从事将数据包发送给服务端，起到一个解耦的效果
     * @param channelFuture
     */
    private void startClient(ChannelFuture channelFuture) {
        Thread asyncSendJob = new Thread(new AsyncSendJob(channelFuture));
        asyncSendJob.start();
    }

    /**
     * 异步发送信息
     */
    class AsyncSendJob implements Runnable {

        private ChannelFuture channelFuture;

        public AsyncSendJob(ChannelFuture channelFuture) {
            this.channelFuture = channelFuture;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 阻塞模式
                    RpcInvocation data = SEND_QUEUE.take();
                    String json = JSON.toJSONString(data);
                    // 将RpcInvocation封装到RpcProtocol对象中，然后发送给服务端，这里正好对应了上文中的ServerHandler
                    RpcProtocol rpcProtocol = new RpcProtocol(json.getBytes());
                    // netty的通道负责发送数据给服务端
                    channelFuture.channel().writeAndFlush(rpcProtocol);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        Client client = new Client();
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setPort(9090);
        clientConfig.setServerAddr("localhost");
        client.setClientConfig(clientConfig);
        RpcReference reference = client.startClientApplication();
        DataService dataService = reference.get(DataService.class);
        for (int i = 0; i < 100; i++) {
            String result = dataService.sendData("test");
            System.out.println(result);

        }
    }
}
