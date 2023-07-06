package cdu.zch.rpc.core.common.event;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public class IRpcUpdateEvent implements IRpcEvent{
    private Object data;

    public IRpcUpdateEvent(Object data) {
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public IRpcEvent setData(Object data) {
        this.data = data;
        return this;
    }
}
