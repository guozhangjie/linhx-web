package com.linhx.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 浪子傑
 * @version 1.0
 * @date 2021/2/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageBean<T> {

    /**
     * 内容列表
     */
    private List<T> content;

    /**
     * 每页大小
     */
    private int size;

    /**
     * list中元素有多少个
     */
    private int elementTotalSize;

    /**
     * 当前页数
     */
    private int page;

    /**
     * 总的页数
     */
    private int totalPage;

    /**
     * 总共的数量
     */
    private int totalSize;
}
