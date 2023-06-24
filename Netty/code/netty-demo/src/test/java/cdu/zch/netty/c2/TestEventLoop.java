package cdu.zch.netty.c2;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Zch
 * @data 2023/6/24
 **/
@Slf4j
public class TestEventLoop {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(2);

        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        // 普通任务
        group.execute(() -> {
            log.debug("OK");
        });

        // 定时任务 参数2的0表示立刻立刻执行，参数3表示间隔1秒，参数4表示单位为秒
        group.scheduleAtFixedRate(() -> {
            log.debug("Schedule OK");
        }, 0, 1, TimeUnit.SECONDS);
        log.debug("main");
    }

}
