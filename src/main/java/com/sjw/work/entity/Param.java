package com.sjw.work.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "lumen_param")
public class Param implements Serializable {

    @Id
    Integer id;

    String code;

    String name;

    String sort;

    String type;

    String other;

    String status;

}
