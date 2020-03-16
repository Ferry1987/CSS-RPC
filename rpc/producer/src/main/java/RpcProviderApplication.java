import com.css.com.css.core.ServiceRegisterComponent;
import com.css.com.css.rpc.ProducerStandardServer;
import com.css.com.css.rpc.ServerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * DubboProviderApplication
 * 服务提供启动类
 *
 * @author xiaoze
 * @date 2018/6/7
 */
public class RpcProviderApplication {

    public static void main(String[] args) {
        ProducerStandardServer server = ServerFactory.getStandardServer();
        server.export(1000);
    }
}
