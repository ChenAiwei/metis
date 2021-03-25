package com.boot.metis.web.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author：chenaiwei
 * @Description：RegisterVo
 * @CreateDate：2020/7/9 16:41
 */
@Data
public class RegisterVo implements Serializable {
	@NotEmpty(message = "用户名不允许为空")
	@Pattern(regexp = "[0-9A-Za-z]+$",message = "账号只能字母或数字")
	@Size(max=16,message = "账号长度在0-16之间")
	private String userAccount;
	@NotEmpty(message = "密码不允许为空")
	@Size(min=6,max=12,message = "密码长度在6-12之间")
	private String passWord;
}
