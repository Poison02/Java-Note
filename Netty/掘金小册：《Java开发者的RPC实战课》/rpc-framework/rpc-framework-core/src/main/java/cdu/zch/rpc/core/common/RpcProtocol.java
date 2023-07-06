package cdu.zch.rpc.core.common;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class RpcProtocol implements Serializable {

    private static final long serialVersionUID = 5359096060555795690L;

    // 魔法数，主要是在做通讯服务的时候定义的一个安全检测，确认当前请求的协议是否合法
    private short magicNumber = MAGIC_NUMBER;

    // 协议传输核心数据的长度
    private int contentLength;

    /*
    核心传输数据，在这里面又重新封装，在RpcInvocation中，
    主要是请求的服务名称，请求服务的方法名称，请求参数内容
     */
    private byte[] content;

    public RpcProtocol(byte[] content) {
        this.contentLength = content.length;
        this.content = content;
    }

    public short getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(short magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RpcProtocol{" +
                "magicNumber=" + magicNumber +
                ", contentLength=" + contentLength +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
