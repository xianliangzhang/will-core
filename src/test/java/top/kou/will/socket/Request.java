package top.kou.will.socket;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/19.
 */
public class Request implements Serializable {
    public Constant.Status status;
    public String sessionId;
    public String requestId;
    public Object data;

    public Request(String sessionId, String requestId, Object data) {
        this.data = data;
        this.status = Constant.Status.START;
        this.sessionId = sessionId;
        this.requestId = requestId;
    }

    public Request newRequest(String requestId, Object data) {
        this.requestId = requestId;
        this.data = data;
        return this;
    }
}
