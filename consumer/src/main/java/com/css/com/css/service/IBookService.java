package com.css.com.css.service;

import com.css.com.css.vo.Book;

public interface IBookService {
    /**
     * 获取图书信息
     * @param id 图书编号
     * @return 图书信息
     */
    public Book getBookById(String id);
}
