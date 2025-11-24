package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lwf14
 * @since 2025/11/20 10:10
 */
@TableName("T_CATEGORY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父分类id
     */
    private Long parentId;

    /**
     * 类型，1-收入，2-支出，3-收支（投资理财进出，借贷还款进出）
     */
    private Integer type;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 分类名
     */
    private String name;

    /**
     * 分类路径
     */
    private String path;

    /**
     * 是否删除，0-否，1-是
     */
    private Integer isDeleted;
}
