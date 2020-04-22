package com.example.rctest.controller;

import com.example.rctest.dao.FileInfo;
import com.example.rctest.service.FileInfoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件信息控制层
 * @author linbaoling
 * @date 2020/4/21,0:52
 */
@Controller
public class FileInfoController {

    @Autowired
    private FileInfoManager manager;

    /**
     * 保存文件
     * @param fileInfo
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(@ModelAttribute FileInfo fileInfo) {
        try {
            manager.save(fileInfo);
        } catch (Exception e){
            return "error";
        }
        return "success";
    }

    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("fileInfo", new FileInfo());
        return "new";
    }

    /**
     * 文件列表
     * @param model
     * @return
     */
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String list(Model model) {
        List<FileInfo> list;
        try {
           list =  manager.getFileInfoList();
        } catch (Exception e){
            return "error";
        }
        model.addAttribute("fileInfos", list);
        return "view";
    }

    /**
     * 下载文件
     * @param request
     * @return
     */
    @RequestMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            FileInfo fileInfo = manager.getFileInfo(id);
            manager.downloadFile(fileInfo, response);
        } catch (Exception e){
            System.out.println("下载文件失败");
        }
    }

    /**
     * 编辑文件
     * @param request
     * @return
     */
    @RequestMapping("edit")
    public String edit(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        FileInfo fileInfo;
        try {
            fileInfo =  manager.getFileInfo(id);
        } catch (Exception e){
            return "error";
        }
        model.addAttribute("fileInfo", fileInfo);
        return "edit";
    }

    /**
     * 修改文件
     * @param fileInfo
     * @return
     */
    @RequestMapping(value = "modify", method = RequestMethod.POST)
    public String modify(@ModelAttribute FileInfo fileInfo) {
        try {
            manager.update(fileInfo);
        } catch (Exception e){
            return "error";
        }
        return "redirect:/view";
    }
}
