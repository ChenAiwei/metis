package com.boot.metis.feign.fallback;

import com.boot.metis.common.dto.auth.UserTokenDto;
import com.boot.metis.common.exception.ServiceException;
import com.boot.metis.common.util.ResultVoUtil;
import com.boot.metis.common.vo.ResultVo;
import com.boot.metis.feign.interfaces.Login;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：chenaiwei
 * @Description：LoginFallbackFactory
 * @CreateDate：2021/3/25 10:40
 */
@Component
public class LoginFallbackFactory implements FallbackFactory<Login> {
	@Override
	public Login create(Throwable throwable) {
		throwable.printStackTrace();
		return new Login() {
			@Override
			public ResultVo<UserTokenDto> login(String userAccount, String passWord,String ip) {
				return ResultVoUtil.error(throwable.getMessage());
			}

			@Override
			public ResultVo<?> logout(String userId) {
				return ResultVoUtil.error(throwable.getMessage());
			}

			@Override
			public  ResultVo<?> register(String username, String password) {
				return ResultVoUtil.error(throwable.getMessage());
			}
		};
	}
}
