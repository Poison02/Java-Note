package cdu.zch.demo04.protocol.command;

/**
 * @author Zch
 * @date 2023/7/22
 **/
public interface Command {

    Byte LOGIN_REQUEST = 1;

    Byte LOGIN_RESPONSE = 2;

    Byte MESSAGE_REQUEST = 3;

    Byte MESSAGE_RESPONSE = 4;
}
