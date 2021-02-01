package com.boot.metis.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.metis.server.entity.TenantNode;
import com.boot.metis.server.mapper.TenantNodeMapper;
import com.boot.metis.server.service.ITenantNodeService;
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
public class TenantNodeServiceImpl extends ServiceImpl<TenantNodeMapper, TenantNode> implements ITenantNodeService {

}
