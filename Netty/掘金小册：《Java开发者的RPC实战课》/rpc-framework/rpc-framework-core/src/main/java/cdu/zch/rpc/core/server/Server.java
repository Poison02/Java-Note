package cdu.zch.rpc.core.server;

import cdu.zch.rpc.core.common.RpcDecoder;
import cdu.zch.rpc.core.common.RpcEncoder;
import cdu.zch.rpc.core.common.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cdu.zch.rpc.core.common.cache.CommonServerCache.PROVIDER_CLASS_MAP;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static EventLoopGroup bossGroup = null;
    private static EventLoopGroup workerGroup = null;

    private ServerConfig serverConfig;

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void startApplication() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_SNDBUF, 16 * 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        LOGGER.debug("----初始化provider过程----");
                        ch.pipeline()
                                .addLast(new RpcEncoder())
                                .addLast(new RpcDecoder())
                                .addLast(new ServerHandler());
                    }
                })
                .bind(serverConfig.getPort()).sync();
    }

    public void registryService(Object serviceBean) {
        if (serviceBean.getClass().getInterfaces().length == 0) {
            throw new RuntimeException("service 必须要又interfaces");
        }
        Class[] clazz = serviceBean.getClass().getInterfaces();
        if (clazz.length > 1) {
            throw new RuntimeException("service 只能有一个interface");
        }
        Class interfaceClass = clazz[0];
        // 需要注册的对象统一放在一个MAP中进行管理
        PROVIDER_CLASS_MAP.put(interfaceClass.getName(), serviceBean);
    }

    public static void main(String[] args) throws InterruptedException {
        Server server = new Server();
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setPort(9090);
        server.setServerConfig(serverConfig);
        server.registryService(new DataServiceImpl());
        server.startApplication();
    }
}
