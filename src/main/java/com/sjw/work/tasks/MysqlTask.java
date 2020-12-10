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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
@EnableAsync
//@Service
@Slf4j
public class MysqlTask {

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private ParamServiceImp paramServiceImp;

    @Autowired
    private TaskServiceImp taskServiceImp;

    @Async
    @Scheduled(cron = "0 0/1 * * * *")
    public void first(){
        Param taskName = paramServiceImp.findName("task","2");
        Calendar now = Calendar.getInstance();
        String str = taskName.getName();
        String[] arr = str.split(" ");
        do{
            Integer day = now.get(Calendar.DAY_OF_MONTH);
            Integer week = now.get(Calendar.WEEK_OF_MONTH);
            Boolean bool = false;
            if(!arr[4].equals("*") && week == Integer.parseInt(arr[4])){
                bool = true;
            }else if(!arr[3].equals("*") && day == Integer.parseInt(arr[3])){
                bool = true;
            }else if (arr[4].equals("*") && arr[3].equals("*")){
                bool = true;
            }

            if(!bool){
                break;
            }
            Integer hour = now.get(Calendar.HOUR_OF_DAY);
            if(!arr[2].equals("*") && hour != Integer.parseInt(arr[2])){
                break;
            }
            Integer min = now.get(Calendar.MINUTE);
            if(min != Integer.parseInt(arr[1])){
                break;
            }
            saveSql();
        }while (false);
    }

    // 实现数据库的导出（方法1）
    public  void saveSql() {
        do{

            Param taskName = paramServiceImp.findName("task","1");
            if(Integer.parseInt(taskName.getName()) != 1){
                break;
            }
            log.info("执行任务"+System.currentTimeMillis()/1000);
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
                log.info("执行任务1"+System.currentTimeMillis()/1000);
            } catch (IOException e) {
                log.info(e.getMessage());
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }while (false);
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
