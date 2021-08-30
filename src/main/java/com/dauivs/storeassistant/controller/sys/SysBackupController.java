package com.dauivs.storeassistant.controller.sys;

import com.dauivs.storeassistant.common.ResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/api/sys/backup")
public class SysBackupController {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseData backup() {
        System.out.println("===========================开始备份数据===================");
        System.out.println("url:" + url);
        System.out.println("username:" + username);
        System.out.println("password:" + password);
        try {
            String command = "cmd /c mysqldump -hlocalhost -P3306 -uroot -p store_assistant > D:\\temp\\store_assistant.sql";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseData.success(null);
    }
}
