package cdu.zch.rpc.core.common;

import java.util.Arrays;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class RpcInvocation {

    // 请求的目标方法，如findUser
    private String targetMethod;

    // 请求的目标服务名称，如com.zch.user.UserService
    private String targetServiceName;

    // 请求参数
    private Object[] args;

    /*
    用于匹配请求和相应的一个关键之。当请求从客户端发出的时候，会有一个uuid用于记录发出的请求，
    待数据返回的时候通过uuid来匹配对应的请求线程，并且返回给调用线程
     */
    private String uuid;

    // 接口相应的数据放入这个字段中，若异步调用或者void，则为null
    private Object response;

    public String getTargetMethod() {
        return targetMethod;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public String getTargetServiceName() {
        return targetServiceName;
    }

    public void setTargetServiceName(String targetServiceName) {
        this.targetServiceName = targetServiceName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "RpcInvocation{" +
                "targetMethod='" + targetMethod + '\'' +
                ", targetServiceName='" + targetServiceName + '\'' +
                ", args=" + Arrays.toString(args) +
                ", uuid='" + uuid + '\'' +
                ", response=" + response +
                '}';
    }
}
