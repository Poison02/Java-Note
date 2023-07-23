package cdu.zch.demo04.protocol.response;

import cdu.zch.demo04.protocol.Packet;
import lombok.Data;

import static cdu.zch.demo04.protocol.command.Command.MESSAGE_RESPONSE;

/**
 * @author Zch
 * @date 2023/7/22
 **/
@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {

        return MESSAGE_RESPONSE;
    }

}
