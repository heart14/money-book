package com.liiwe.moneybook.service.base.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.manage.RoleInfo;
import com.liiwe.moneybook.base.bean.domain.mb.PageRoleReq;
import com.liiwe.moneybook.base.bean.entity.SysRole;
import com.liiwe.moneybook.base.common.Constants;
import com.liiwe.moneybook.mapper.SysRoleMapper;
import com.liiwe.moneybook.service.base.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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

    @Override
    public void saveOrEditRole(RoleInfo roleInfo) {

    }

    @Override
    public void deleteRole(Long id) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getId, id);
        SysRole selected = roleMapper.selectOne(wrapper);
        if (selected == null) {
            throw new RuntimeException("禁用失败，角色不存在");
        }
        selected.setStatus(Constants.CommonStatus.NO);
        selected.setUpdateAt(new Date());

        roleMapper.updateById(selected);
    }
}
