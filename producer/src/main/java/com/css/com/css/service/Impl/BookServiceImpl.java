package com.css.com.css.service.Impl;

import com.css.com.css.rpc.annotation.ServiceContainer;
import com.css.com.css.service.IBookService;
import com.css.com.css.vo.Book;

import java.util.logging.Logger;
@ServiceContainer(value = "BookService")
public class BookServiceImpl implements IBookService {
    final Logger logger = Logger.getLogger(BookServiceImpl.class.getName());
    /**
     * 获取图书信息
     * @param id 图书编号
     * @return 图书信息
     */
    public Book getBookById(String id){
        logger.info("Id:"+id);
        final Book book = new Book();
        book.setId("100100011");
        book.setName("少儿不宜");
        book.setPrice(100);
        book.setRemark("此书少儿不宜！");
        return book;
    }
}
