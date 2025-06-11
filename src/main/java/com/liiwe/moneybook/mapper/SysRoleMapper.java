package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liiwe.moneybook.base.bean.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author wfli
 * @since 2025/6/11 14:18
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectUserRoleByUid(long uid);
}
