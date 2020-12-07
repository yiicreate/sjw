package com.sjw.work.service.Imp;

import com.sjw.work.entity.Task;
import com.sjw.work.repository.TaskRepository;
import com.sjw.work.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImp implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void setTaskService(Task task) {
        taskRepository.save(task);
    }
}
