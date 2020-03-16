
import com.css.com.css.rpc.BizServiceUtils;
import com.css.com.css.rpc.RemoteCallProxy;
import com.css.com.css.rpc.RemoteCallProxyHandler;
import com.css.com.css.rpc.proxy.protocol.HttpInvocationHandler;
import com.css.com.css.service.IBookService;
import com.css.com.css.vo.Book;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Logger;
/**
 * DubboProviderApplication
 * 服务提供启动类
 *
 * @author xiaoze
 * @date 2018/6/7
 */
public class RpcConsumerApplication {

    private static Logger logger = Logger.getLogger(RpcConsumerApplication.class.getName());
    public static void main(String[] args) {
        final IBookService service = (IBookService) BizServiceUtils.callRomote(IBookService.class);
        final Book book = service.getBookById("10086");
        logger.info(book.getName());
    }
}
