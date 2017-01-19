package top.kou.will.socket;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/19.
 */
public class Response implements Serializable {
    public Constant.Status status;
    public Request request;
    public Object data;

    public Response(Request request, Object data) {
        this.data = data;
        this.status = Constant.Status.GOON;
        this.request = request;
    }
}
