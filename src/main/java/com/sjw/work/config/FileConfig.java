package com.sjw.work.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class FileConfig {
    @Value("${file.path}")
    private String path;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${file.host}")
    private String host;

    @Value("${file.port}")
    private String port;

    @Value("${file.mysqlPath}")
    private String mysqlPath;

    @Value("${file.time}")
    private Long time;

    @Value("${file.table}")
    private String table;
}
