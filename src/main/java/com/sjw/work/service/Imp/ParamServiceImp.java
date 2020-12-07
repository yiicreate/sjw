package com.sjw.work.service.Imp;

import com.sjw.work.entity.Param;
import com.sjw.work.repository.ParamRepository;
import com.sjw.work.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamServiceImp implements ParamService {
    @Autowired
    private ParamRepository paramRepository;

    @Override
    public Param findOne(String type) {
        return null;
    }

    @Override
    public Param findName(String type, String code) {
        return paramRepository.findByCodeAndType(code,type);
    }
}
