package com.dauivs.storeassistant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class LocalConfig {

    @Bean
    public String attachmentPath() {
        File[] roots = File.listRoots();
        File root = roots.length >= 2 ? roots[1] : roots[0];
        String path = root.getPath() + "store_assistant" + File.separator + "attachment";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        System.out.println(path);
        return path;
    }
}
