package com.home.ferrari.vo.breakdoc;

import java.io.Serializable;

public class BreakTypelist implements Serializable {

	private static final long serialVersionUID = -8318856448688255700L;

	private Integer id;
	private Integer breakId;
	private Integer breakTypeId;
	
	public BreakTypelist() {
		
	}

	public BreakTypelist(Integer breakId, Integer breakTypeId) {
		this.breakId = breakId;
		this.breakTypeId = breakTypeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBreakId() {
		return breakId;
	}

	public void setBreakId(Integer breakId) {
		this.breakId = breakId;
	}

	public Integer getBreakTypeId() {
		return breakTypeId;
	}

	public void setBreakTypeId(Integer breakTypeId) {
		this.breakTypeId = breakTypeId;
	}

}
