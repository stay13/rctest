package com.example.rctest.mapper;

import com.example.rctest.dao.FileInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文件信息映射层
 * @author linbaoling
 * @date 2020/4/21,12:50
 */
@Mapper
public interface FileInfoMapper {

    void insert(@Param("fileInfo")FileInfo fileInfo);

    void update(@Param("fileInfo")FileInfo fileInfo);

    List<FileInfo> getFileInfoList();

    FileInfo getFileInfo(int id);
}
