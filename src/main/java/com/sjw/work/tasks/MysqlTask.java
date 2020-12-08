package com.sjw.work.tasks;

import com.sjw.work.config.FileConfig;
import com.sjw.work.entity.Param;
import com.sjw.work.entity.Task;
import com.sjw.work.service.Imp.ParamServiceImp;
import com.sjw.work.service.Imp.TaskServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Configuration
@EnableScheduling
@EnableAsync
@Slf4j
public class MysqlTask {

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private ParamServiceImp paramServiceImp;

    @Autowired
    private TaskServiceImp taskServiceImp;

    @Async
    @Scheduled(cron = "0/0 * 1 * * ?")
    public void first(){
        Param taskName = paramServiceImp.findName("task","1");
        Long timestamp = System.currentTimeMillis() / 1000;
        if(timestamp - fileConfig.getTime()>= Integer.parseInt(taskName.getName())){
            this.saveSql();
            fileConfig.setTime(timestamp);
        }
    }

    // 实现数据库的导出（方法1）
    public  void saveSql() {
        Runtime runtime = Runtime.getRuntime();
        Map res = getExportCommand();
        // 这里其实是在命令窗口中执行的 command 命令行
        try {
            Process exec = runtime.exec((String) res.get("command"));
            Task task = new Task();
            task.setPath((String) res.get("name"));
            Long timestamp = System.currentTimeMillis() / 1000;
            task.setCreateTime(timestamp);
            taskServiceImp.setTaskService(task);
        } catch (IOException e) {
            log.info(e.getMessage());
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 得到导出数据的命令行语句
    private  Map<String, String> getExportCommand() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddkkmmss");
        String name =sdf.format(d)+".sql";
        String username = fileConfig.getUsername();// 用户名
        String password = fileConfig.getPassword();// 密码
        String host = fileConfig.getHost();// 数据库所在的主机
        String port = fileConfig.getPort();// 端口号
        String path = fileConfig.getPath();
        String MysqlPath = fileConfig.getMysqlPath(); //路径是mysql中
        String exportDatabaseName = fileConfig.getTable();// 导入的目标数据库的名称

        StringBuffer command = new StringBuffer();
        String exportPath = path+name;// 导入的目标文件所在的位置

        // 注意哪些地方要空格，哪些不要空格
        command.append(MysqlPath).append("mysqldump -u").append(username).append(" -p").append(password)// 密码是用的小p，而端口是用的大P。
                .append(" -h").append(host).append(" -P").append(port).append(" ").append(exportDatabaseName)
                .append(" -r ").append(exportPath);
        Map<String,String> map=  new HashMap<>();
        map.put("command",command.toString());
        map.put("name",exportPath);
        return map;
    }
}
