package com.liiwe.moneybook.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.manage.RoleInfo;
import com.liiwe.moneybook.base.bean.domain.mb.PageRoleReq;
import com.liiwe.moneybook.base.bean.entity.SysRole;
import com.liiwe.moneybook.mapper.SysRoleMapper;
import com.liiwe.moneybook.service.base.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lwf14
 * @since 2025/12/10 16:55
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper roleMapper;

    public SysRoleServiceImpl(SysRoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public Page<RoleInfo> getRoleList(PageRoleReq req) {
        Page<SysRole> page = new Page<>(req.getCurrent(), req.getSize());
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();

        Page<SysRole> selectPage = roleMapper.selectPage(page, wrapper);
        List<RoleInfo> list = new ArrayList<>();

        List<SysRole> records = selectPage.getRecords();
        if (records != null) {
            for (SysRole record : records) {
                list.add(new RoleInfo(record));
            }
        }

        Page<RoleInfo> result = new Page<>();
        result.setRecords(list);
        result.setTotal(selectPage.getTotal());
        result.setSize(selectPage.getSize());
        result.setCurrent(selectPage.getCurrent());
        return result;
    }
}
