package cdu.zch.rpc.core.common.config;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class ClientConfig {

    private Integer port;

    private String serverAddr;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }
}
