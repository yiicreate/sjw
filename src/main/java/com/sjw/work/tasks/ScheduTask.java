package com.sjw.work.tasks;

import com.sjw.work.entity.Param;
import com.sjw.work.service.Imp.ParamServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@EnableScheduling
public class ScheduTask implements SchedulingConfigurer {

    @Autowired
    ParamServiceImp paramServiceImp;

    @Autowired
    MysqlTask mysqlTask;


    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.addTriggerTask(
//                //1.添加任务内容(Runnable)
//                () -> mysqlTask.saveSql(),
//                //2.设置执行周期(Trigger)
//                triggerContext -> {
//                    //2.1 从数据库获取执行周期
//                    Param cron = paramServiceImp.findName("task","1");
//                    Param task = paramServiceImp.findName("task","2");
//                    //2.2 合法性校验.
//                    if (Integer.parseInt(cron.getName()) != 1) {
//                        return new CronTrigger("0/5 * * * * ?").nextExecutionTime(triggerContext);
//                        // Omitted Code ..
//                    }
//                    //2.3 返回执行周期(Date)
//                    return new CronTrigger(task.getName()).nextExecutionTime(triggerContext);
//                }
//        );
    }
}
