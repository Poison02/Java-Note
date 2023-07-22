package cdu.zch.demo03.protocol.response;

import cdu.zch.demo03.protocol.Packet;
import lombok.Data;

import static cdu.zch.demo03.protocol.command.Command.LOGIN_RESPONSE;

/**
 * @author Zch
 * @date 2023/7/22
 **/
@Data
public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
