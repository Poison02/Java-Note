package cdu.zch.rpc.core.common.config;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class ServerConfig {

    private Integer serverPort;

    private String registerAddr;

    private String applicationName;

    public String getRegisterAddr() {
        return registerAddr;
    }

    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
