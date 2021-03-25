package com.boot.metis.web.vo;

import com.boot.metis.web.validation.ValidationGroups;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author：chenaiwei
 * @Description：LoginVo
 * @CreateDate：2020/7/9 18:37
 */
@Data
public class LoginVo implements Serializable {
	@NotEmpty(message = "用户名不允许为空")
	@Pattern(regexp = "[0-9A-Za-z]+$",message = "账号只能字母或数字")
	@Size(max=16,message = "账号长度在0-16之间")
	private String userAccount;
	@NotEmpty(message = "密码不允许为空")
	@Size(min=6,max=12,message = "密码长度在6-12之间")
	private String passWord;
	@NotEmpty(message = "验证码不允许为空",groups = ValidationGroups.LoginV1.class)
	private String captcha;
	@NotEmpty(message = "验证码过期",groups = ValidationGroups.LoginV1.class)
	private String codeKey;
}
