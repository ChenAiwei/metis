package com.boot.metis.server.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author auto
 * @since 2021-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Tenant extends Model<Tenant> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 账号
	 */
	@TableField("u_account")
	private String uAccount;

	/**
	 * 账户密码
	 */
	@TableField("u_password")
	private String uPassword;

	/**
	 * 账户名
	 */
	@TableField("u_name")
	private String uName;

	/**
	 * 账号状态 0 禁用 1正常
	 */
	@TableField("u_status")
	private Boolean uStatus;

	/**
	 * 头像
	 */
	@TableField("u_avatar")
	private String uAvatar;

	/**
	 * 邮箱
	 */
	@TableField("u_email")
	private String uEmail;

	/**
	 * 手机号码
	 */
	@TableField("u_phone")
	private String uPhone;

	@TableField("u_role_id")
	private Long uRoleId;
	/**
	 * 鉴权ID
	 */
	@TableField("app_id")
	private String appId;

	/**
	 * 鉴权密钥
	 */
	@TableField("app_secret")
	private String appSecret;

	/**
	 * 总共磁盘空间 KB
	 */
	@TableField("total_space")
	private Long totalSpace;

	/**
	 * 使用空间 KB
	 */
	@TableField("use_space")
	private Long useSpace;

	/**
	 * 剩余空间 KB
	 */
	@TableField("free_space")
	private Long freeSpace;

	/**
	 * 文件数量
	 */
	@TableField("file_count")
	private Long fileCount;

	/**
	 * 最后登录时间
	 */
	@TableField("last_login_time")
	private Date lastLoginTime;

	/**
	 * 最后登录IP
	 */
	@TableField("last_login_ip")
	private String lastLoginIp;

	/**
	 * 注册时间
	 */
	@TableField("regist_time")
	private Date registTime;

	/**
	 * 是否删除 0 未删除 1已删除
	 */
	private Integer deleted;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
