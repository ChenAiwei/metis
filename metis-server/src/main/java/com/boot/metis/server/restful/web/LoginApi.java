package com.boot.metis.server.restful.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.metis.common.dto.auth.RoleDto;
import com.boot.metis.common.dto.auth.UserTokenDto;
import com.boot.metis.common.exception.ServiceException;
import com.boot.metis.common.util.ResultVoUtil;
import com.boot.metis.common.vo.ResultVo;
import com.boot.metis.feign.interfaces.Login;
import com.boot.metis.server.entity.Role;
import com.boot.metis.server.entity.Tenant;
import com.boot.metis.server.service.IRoleService;
import com.boot.metis.server.service.ITenantService;
import com.boot.metis.server.util.MD5Util;
import com.boot.metis.server.component.RedisManager;
import com.boot.metis.server.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @Author：chenaiwei
 * @Description：LoginApi
 * @CreateDate：2021/3/25 10:53
 */
@RestController
public class LoginApi implements Login {
	@Autowired
	private ITenantService tenantService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private RedisManager redisManager;

	@Override
	public ResultVo<UserTokenDto> login(String userAccount, String passWord, String ip) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		Tenant tenant = tenantService.getOne(new QueryWrapper<Tenant>().eq("u_account", userAccount));
		if (null == tenant) {
			throw new ServiceException("用户不存在！");
		}
		if (!tenant.getUStatus()) {
			throw new ServiceException("用户已禁用！");
		}
		boolean validPassword = MD5Util.validPassword(passWord, tenant.getUPassword());
		if (!validPassword) {
			throw new ServiceException("密码错误！");
		}
		Role role = roleService.getById(tenant.getURoleId());
		tenant.setLastLoginTime(new Date());
		tenant.setLastLoginIp(ip);
		tenant.updateById();
		String token = UUIDUtils.genToken(tenant.getUAccount());
		UserTokenDto tokenDto = UserTokenDto.builder().avatar(tenant.getUAvatar())
				.userId(tenant.getUAccount())
				.userName(tenant.getUName())
				.roleDto(RoleDto.builder().roleId(role.getId()).roleName(role.getRoleName()).admin(role.getIsAdmin()).build())
				.token(token).build();
		redisManager.set(tenant.getUAccount(),tokenDto,60*60*12);
		return ResultVoUtil.success(tokenDto);
	}

	@Override
	public ResultVo<?> logout(String userId) {
		redisManager.del(userId);
		return ResultVoUtil.success();
	}

	@Override
	public ResultVo<?> register(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		Tenant tenant = tenantService.getOne(new QueryWrapper<Tenant>().eq("u_account", username));
		if (null != tenant) {
			throw new ServiceException("用户名已存在！");
		}
		tenant = new Tenant();
		tenant.setUAccount(username);
		tenant.setUPassword(MD5Util.getEncryptedPwd(password));
		tenant.setRegistTime(new Date());
		tenantService.save(tenant);
		return ResultVoUtil.success();
	}
}
