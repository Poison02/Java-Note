package cdu.zch.rpc.core.common.event;

/**
 * @author Zch
 * @date 2023/7/6
 **/
public interface IRpcListener<T> {

    void callBack(Object t);

}
