package com.example.rctest.dao;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 文件信息
 * @author linbaoling
 * @date 2020/4/21,0:34
 */
@Getter
@Setter
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 7858202829994156422L;

    /**
     * 逻辑id
     */
    private Integer id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 内容
     */
    private String content;
}
