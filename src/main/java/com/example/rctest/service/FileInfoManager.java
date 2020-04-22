package com.example.rctest.service;

import com.example.rctest.dao.FileInfo;
import com.example.rctest.mapper.FileInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 文件信息服务层
 * @author linbaoling
 * @date 2020/4/21,12:49
 */
@Service
public class FileInfoManager {

    @Autowired
    private FileInfoMapper mapper;

    private Lock lock = new ReentrantLock();

    /**
     * 存储文件信息
     * @param fileInfo
     */
    public void save(FileInfo fileInfo){
        if(null == fileInfo){
            return;
        }
        try {
            mapper.insert(fileInfo);
        } catch (Exception e){
            System.out.println("存储文件信息失败:" + e.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * 更新文件信息
     * @param fileInfo
     */
    public void update(FileInfo fileInfo) throws Exception{
        if(null == fileInfo){
            return;
        }
        //若成功获取锁，则执行更新操作，否则等待60秒再尝试获取锁
        synchronized (fileInfo.getId()){
            try {
                mapper.update(fileInfo);
                System.out.println("更新成功");
                Thread.sleep(60000);
            } catch (Exception e){
                System.out.println("更新文件信息失败:" + e.getMessage());
                throw new RuntimeException();
            }
        }

    }

    /**
     * 获取文件信息列表
     */
    public List<FileInfo> getFileInfoList(){
        List<FileInfo> list;
        try {
            list = mapper.getFileInfoList();
        } catch (Exception e){
            System.out.println("获取文件信息列表失败:" + e.getMessage());
            throw new RuntimeException();
        }
        return list;
    }

    /**
     * 根据id获取文件信息
     * @param id
     */
    public FileInfo getFileInfo(int id){
        FileInfo fileInfo;
        try {
            fileInfo = mapper.getFileInfo(id);
        } catch (Exception e){
            System.out.println("获取文件信息失败:" + e.getMessage());
            throw new RuntimeException();
        }
        return fileInfo;
    }

    /**
     * 下载具体文件
     * @param fileInfo
     * @param response
     */
    public void downloadFile(FileInfo fileInfo, HttpServletResponse response) throws IOException{
        OutputStreamWriter write = null;
        BufferedWriter writer = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(fileInfo.getFileName().getBytes(), "iso-8859-1"));
            write = new OutputStreamWriter(response.getOutputStream(), "utf-8");
            writer = new BufferedWriter(write);
            StringBuilder sql = new StringBuilder();
            sql.append(fileInfo.getContent());
            writer.write(sql + "\r\n");
        } catch (UnsupportedEncodingException e) {
            System.out.println("不支持的编码类型" + e.getMessage());
            throw new RuntimeException();
        } catch (IOException e){
            System.out.println("文件IO异常" + e.getMessage());
            throw new RuntimeException();
        } finally {
            if(writer != null){
                writer.close();
            }
            if(write != null){
                write.close();
            }
        }
    }
}
