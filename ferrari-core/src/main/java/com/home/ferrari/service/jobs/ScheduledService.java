package com.home.ferrari.service.jobs;

public interface ScheduledService {

	/**
	 * 门店24小时不接单，给服务商提示
	 */
	void acceptTimeoutTask();
}
