package com.home.ferrari.global;

import org.springframework.stereotype.Component;

@Component
public class PropertiesValue {

	private String desKey;
	private Integer isExecuteMachine;
	private Long acceptTimeout;
	private Integer isProduct;
	private String fileDomain;
	private String fileCrmDomain;

	public String getDesKey() {
		return desKey;
	}

	public void setDesKey(String desKey) {
		this.desKey = desKey;
	}

	public Integer getIsExecuteMachine() {
		return isExecuteMachine;
	}

	public void setIsExecuteMachine(Integer isExecuteMachine) {
		this.isExecuteMachine = isExecuteMachine;
	}

	public Long getAcceptTimeout() {
		return acceptTimeout;
	}

	public void setAcceptTimeout(Long acceptTimeout) {
		this.acceptTimeout = acceptTimeout;
	}

	public Integer getIsProduct() {
		return isProduct;
	}

	public void setIsProduct(Integer isProduct) {
		this.isProduct = isProduct;
	}

	public String getFileDomain() {
		return fileDomain;
	}

	public void setFileDomain(String fileDomain) {
		this.fileDomain = fileDomain;
	}

	public String getFileCrmDomain() {
		return fileCrmDomain;
	}

	public void setFileCrmDomain(String fileCrmDomain) {
		this.fileCrmDomain = fileCrmDomain;
	}
}
