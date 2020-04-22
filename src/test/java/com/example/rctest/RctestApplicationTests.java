package com.example.rctest;

import com.example.rctest.dao.FileInfo;
import com.example.rctest.service.FileInfoManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;


@SpringBootTest
public class RctestApplicationTests {

    @Autowired
    private FileInfoManager fileInfoManager;

    private int count = 5;
    private CountDownLatch latch = new CountDownLatch(count);

    @Test
    public void save(){
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName("测试1");
        fileInfo.setContent("测试内容");
        fileInfoManager.save(fileInfo);
        System.out.println("保存文件信息成功");
    }

    @Test
    public void update() throws Exception {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(1);
        fileInfo.setFileName("test3");
        fileInfo.setContent("adfsfdf34");
        fileInfoManager.update(fileInfo);
        System.out.println("更新文件信息成功");
    }

    @Test
    public void getFileInfoList() {
        List<FileInfo> list = fileInfoManager.getFileInfoList();
        Iterator iterator = list.iterator();
        FileInfo fileInfo = null;
        while(iterator.hasNext()){
            fileInfo = (FileInfo)iterator.next();
            System.out.println("name--" + fileInfo.getFileName());
            System.out.println("content--" + fileInfo.getContent());
        }
    }

    @Test
    public void getFileInfo(){
        int id = 1;
        FileInfo fileInfo = fileInfoManager.getFileInfo(id);
        System.out.println("name--" + fileInfo.getFileName());
        System.out.println("content--" + fileInfo.getContent());
    }

    @Test
    public void concurrentTest() throws Exception{
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(1);
        fileInfo.setFileName("test3");
        fileInfo.setContent("adfsfdf34");
        for(int i=0; i<count; i++){
            int finalI = i + 1;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        fileInfoManager.update(fileInfo);
                        System.out.println("第" + finalI + "次更新");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 执行完毕，计数器减1
                    latch.countDown();
                }
            }).start();
        }

        try {
            // 主线程等待
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
