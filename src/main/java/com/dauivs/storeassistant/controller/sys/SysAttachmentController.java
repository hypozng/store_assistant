package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.common.ResponseData;
import com.dauivs.storeassistant.dao.sys.SysAttachmentDao;
import com.dauivs.storeassistant.model.sys.SysAttachment;
import com.dauivs.storeassistant.utils.IOUtil;
import com.dauivs.storeassistant.utils.StringUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/sys/attachment")
public class SysAttachmentController {

    @Autowired
    private SysAttachmentDao dao;

    @Autowired
    private String attachmentPath;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData info(@PathVariable int id) {
        return ResponseData.success(dao.findById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseData delete(@PathVariable int id) {
        SysAttachment sysAttachment = dao.findById(id).orElse(null);
        if (sysAttachment != null) {
            dao.delete(sysAttachment);
        }
        return ResponseData.success(sysAttachment);
    }

    /**
     * 上传附件
     *
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseData upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseData.fail("无文件信息");
        }
        SysAttachment sysAttachment = new SysAttachment();
        sysAttachment.setName(file.getOriginalFilename());
        sysAttachment.setType(file.getContentType());
        sysAttachment.setSize(file.getSize());
        if (sysAttachment.getName().contains(".")) {
            int dotIndex = sysAttachment.getName().lastIndexOf(".");
            sysAttachment.setExtension(sysAttachment.getName().substring(dotIndex).toLowerCase());
        } else {
            sysAttachment.setExtension("");
        }
        sysAttachment.setPath(StringUtil.uuid() + sysAttachment.getExtension());
        sysAttachment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
            File targetFile = new File(attachmentPath, sysAttachment.getPath());
            IOUtil.copy(file.getInputStream(), new FileOutputStream(targetFile));
        } catch(IOException e) {
            return ResponseData.fail(ResponseData.MESSAGE_FAIL01, e.getMessage());
        }
        return ResponseData.success(dao.saveAndFlush(sysAttachment));
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(Integer id, String path, HttpServletResponse response) {
        SysAttachment sysAttachment = null;
        if (id != null) {
            sysAttachment = dao.findById(id).orElse(null);
        } else if (!StringUtil.isEmpty(path)) {
            sysAttachment = dao.findByPath(path);
        }
        if (sysAttachment == null) {
            return;
        }
        File file = new File(attachmentPath, sysAttachment.getPath());
        if (!file.exists()) {
            return;
        }
        String fileName;
        try {
            fileName = URLEncoder.encode(sysAttachment.getName(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            fileName = file.getName();
        }
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        try {
            IOUtil.copy(new FileInputStream(file), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
