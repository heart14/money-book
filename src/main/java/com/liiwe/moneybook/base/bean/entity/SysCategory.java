package com.liiwe.moneybook.base.bean.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wfli
 * @since 2025/6/18 16:45
 */
@TableName("t_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysCategory {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer parentId;

    private Integer level;

    private Integer isLeaf;

    private Integer isActive;

    private String path;

    private String detail;

}
