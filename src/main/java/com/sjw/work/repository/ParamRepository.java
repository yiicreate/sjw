package com.sjw.work.repository;

import com.sjw.work.entity.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParamRepository extends JpaRepository<Param,Integer> {
    Param findByCodeAndType(String code,String type);

    String findNameByCodeAndType(String code,String type);
}
