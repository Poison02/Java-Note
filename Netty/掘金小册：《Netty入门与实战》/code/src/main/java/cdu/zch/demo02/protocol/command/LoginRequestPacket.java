package cdu.zch.demo02.protocol.command;

import lombok.Data;

import static cdu.zch.demo03.protocol.command.Command.LOGIN_REQUEST;

/**
 * @author Zch
 * @date 2023/7/22
 **/
@Data
public class LoginRequestPacket extends Packet {
    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
