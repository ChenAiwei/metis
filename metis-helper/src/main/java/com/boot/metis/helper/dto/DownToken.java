package com.boot.metis.helper.dto;

import lombok.Data;

@Data
public class DownToken {

	private String accessKey;

	private String encodedSign;

}
