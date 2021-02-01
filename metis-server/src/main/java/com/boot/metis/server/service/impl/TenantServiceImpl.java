package com.boot.metis.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.metis.server.entity.Tenant;
import com.boot.metis.server.mapper.TenantMapper;
import com.boot.metis.server.service.ITenantService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author auto
 * @since 2021-02-01
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

}
