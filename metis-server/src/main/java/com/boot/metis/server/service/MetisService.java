package com.boot.metis.server.service;

import com.boot.metis.common.exception.ServiceException;
import com.boot.metis.helper.dto.Auth;
import com.boot.metis.helper.dto.DownToken;
import com.boot.metis.helper.dto.PutPolicy;
import com.boot.metis.helper.dto.UpToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：chenaiwei
 * @Description：MetisService
 * @CreateDate：2021/1/27 14:27
 */
@Service
@Slf4j
public class MetisService {
	@Autowired
	private AuthService authService;

	public UpToken analyzeUpToken(String token) {
		UpToken tokenDecrypt = new UpToken();
		if (token.isEmpty()) {
			throw new ServiceException("不合法token");
		}
		String[] tokenSplit = token.split(":");
		if (tokenSplit.length < 3) {
			throw new ServiceException("不合法token");
		}
		String accessKey = tokenSplit[0];
		String encodedSign = tokenSplit[1];
		String encodedPutPolicy = tokenSplit[2];
		Auth authInfo = authService.getAuthInfo(accessKey);
		if (authInfo == null) {
			throw new ServiceException("不合法token");
		}
		log.info("accessKey:" + accessKey);
		log.info("encodedSign:" + encodedSign);
		log.info("encodedPutPolicy:" + encodedPutPolicy);
		this.validateUpToken(encodedSign, encodedPutPolicy, authInfo.getSecretKey());
		tokenDecrypt.setAccessKey(accessKey);
		tokenDecrypt.setEncodedSign(encodedSign);
		tokenDecrypt.setPutPolicy(this.analyzePutPolicy(encodedPutPolicy));
		return tokenDecrypt;
	}

	public void validateUpToken(String encodedSign, String encodedPutPolicy, String secretKey) {
		String trueSign = Base64.encodeBase64URLSafeString(HmacUtils.hmacSha1(secretKey, encodedPutPolicy));
		log.info("trueSign:" + trueSign);
		if (!StringUtils.equals(trueSign, encodedSign)) {
			throw new ServiceException("不合法sign");
		}
	}

	public List<PutPolicy> analyzePutPolicy(String encodedPutPolicy) {
		byte[] decodeBase64 = Base64.decodeBase64(encodedPutPolicy);
		String putPolicy = new String(decodeBase64);
		ObjectMapper mapper = new ObjectMapper();
		List<PutPolicy> list = new ArrayList<>();
		try {
			list = mapper.readValue(putPolicy, new TypeReference<List<PutPolicy>>() { });
		} catch (Exception e) {
			throw new ServiceException("不合法putPolicy");
		}
		return list;
	}

	public DownToken analyzeDownToken(String token, String requestUrlWithoutToken) {
		DownToken tokenDecrypt = new DownToken();
		if (token.isEmpty()) {
			throw new ServiceException("不合法token");
		}
		String[] tokenSplit = token.split(":");
		if (tokenSplit.length < 2) {
			throw new ServiceException("不合法token");
		}
		String accessKey = tokenSplit[0];
		String encodedSign = tokenSplit[1];
		Auth authInfo = authService.getAuthInfo(accessKey);
		if (authInfo == null) {
			throw new ServiceException("不合法token");
		}
		String secretKey = authInfo.getSecretKey();
		this.validateDownToken(requestUrlWithoutToken, encodedSign, secretKey);
		tokenDecrypt.setAccessKey(accessKey);
		tokenDecrypt.setEncodedSign(encodedSign);
		return tokenDecrypt;
	}

	public void validateDownToken(String requestUrlWithoutSign, String encodedSign, String secretKey) {
		String trueSign = Base64.encodeBase64URLSafeString(HmacUtils.hmacSha1(secretKey, requestUrlWithoutSign));
		if (!StringUtils.equals(trueSign, encodedSign)) {
			throw new ServiceException("不合法sign");
		}
	}
}
