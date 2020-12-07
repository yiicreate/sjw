package com.sjw.work.service;


import com.sjw.work.entity.Param;

public interface ParamService {
    Param findOne(String type);

    Param findName(String type,String code);
}
