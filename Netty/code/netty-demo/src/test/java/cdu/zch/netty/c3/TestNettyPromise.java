package cdu.zch.netty.c3;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @author Zch
 * @data 2023/6/25
 **/
@Slf4j
public class TestNettyPromise {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 准备EventLoop对象
        EventLoop eventLoop = new NioEventLoopGroup().next();
        // 主动创建promise
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() -> {
            System.out.println("开始计算");
            try {
                // int i = 1 / 0;
                Thread.sleep(1000);
                promise.setSuccess(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
                promise.setFailure(e);
            }
        }).start();
        // 接受结果的线程
        log.debug("等待结果");
        log.debug("结果是:{}", promise.get());
    }

}
