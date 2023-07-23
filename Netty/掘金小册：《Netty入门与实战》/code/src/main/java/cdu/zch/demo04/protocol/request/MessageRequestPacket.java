package cdu.zch.demo04.protocol.request;

import cdu.zch.demo04.protocol.Packet;
import lombok.Data;

import static cdu.zch.demo04.protocol.command.Command.MESSAGE_REQUEST;

/**
 * @author Zch
 * @date 2023/7/22
 **/
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }

}
