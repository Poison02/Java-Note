package cdu.zch.protocol.command;

import cdu.zch.demo02.protocol.command.LoginRequestPacket;
import cdu.zch.demo02.protocol.command.Packet;
import cdu.zch.demo02.protocol.command.PacketCodeC;
import cdu.zch.demo02.serialize.Serializer;
import cdu.zch.demo02.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Zch
 * @date 2023/7/22
 **/
public class PacketCodeCTest {

    @Test
    public void test() {
        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");
        System.out.println(loginRequestPacket);

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        System.out.println(byteBuf);
        Packet decodedPacket = packetCodeC.decode(byteBuf);
        System.out.println(decodedPacket);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));
    }

}
