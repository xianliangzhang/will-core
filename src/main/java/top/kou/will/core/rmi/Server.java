package top.kou.core.rmi;

import org.apache.log4j.Logger;
import top.kou.core.rmi.inter.message.impl.MessageService;
import top.kou.core.rmi.inter.transfer.impl.TransferService;
import top.kou.core.rmi.skeleton.ServerRepertory;

/**
 * Created by Hack on 2016/11/23.
 */
public class Server {
    private static final Logger RUN_LOG = Logger.getLogger(Server.class);

    public static void main(String[] args) throws Exception {
        ServerRepertory.register("MessageService", new MessageService());
        RUN_LOG.info("MessageService Deployed!");

        ServerRepertory.register("TransferService", new TransferService());
        RUN_LOG.info("TransferService Deployed!");
    }
}
