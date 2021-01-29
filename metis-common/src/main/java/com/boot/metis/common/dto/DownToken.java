package com.boot.metis.common.dto;

import lombok.Data;

@Data
public class DownToken {

	private String accessKey;

	private String encodedSign;

}
