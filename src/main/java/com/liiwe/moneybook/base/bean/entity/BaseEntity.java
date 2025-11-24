package com.liiwe.moneybook.base.bean.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lwf14
 * @since 2025/11/19 16:35
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    private Date createAt;

    private Date updateAt;
}
