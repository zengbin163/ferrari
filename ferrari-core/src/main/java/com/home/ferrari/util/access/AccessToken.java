package com.home.ferrari.util.access;

import java.io.Serializable;

public class AccessToken implements Serializable {
	private static final long serialVersionUID = 9078903399478628435L;
	private String taobao_user_nick;
	private long re_expires_in;
	private long expires_in;
	private long expire_time;
	private long r1_expires_in;
	private long w2_valid;
	private long w2_expires_in;
	private long taobao_user_id;
	private long w1_expires_in;
	private long r1_valid;
	private long r2_valid;
	private long w1_valid;
	private long r2_expires_in;
	private String token_type;
	private String refresh_token;
	private long refresh_token_valid_time;
	private String access_token;

	public String getTaobao_user_nick() {
		return taobao_user_nick;
	}

	public long getRe_expires_in() {
		return re_expires_in;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public long getExpire_time() {
		return expire_time;
	}

	public long getR1_expires_in() {
		return r1_expires_in;
	}

	public long getW2_valid() {
		return w2_valid;
	}

	public long getW2_expires_in() {
		return w2_expires_in;
	}

	public long getTaobao_user_id() {
		return taobao_user_id;
	}

	public long getW1_expires_in() {
		return w1_expires_in;
	}

	public long getR1_valid() {
		return r1_valid;
	}

	public long getR2_valid() {
		return r2_valid;
	}

	public long getW1_valid() {
		return w1_valid;
	}

	public long getR2_expires_in() {
		return r2_expires_in;
	}

	public String getToken_type() {
		return token_type;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public long getRefresh_token_valid_time() {
		return refresh_token_valid_time;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setTaobao_user_nick(String taobao_user_nick) {
		this.taobao_user_nick = taobao_user_nick;
	}

	public void setRe_expires_in(long re_expires_in) {
		this.re_expires_in = re_expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	public void setExpire_time(long expire_time) {
		this.expire_time = expire_time;
	}

	public void setR1_expires_in(long r1_expires_in) {
		this.r1_expires_in = r1_expires_in;
	}

	public void setW2_valid(long w2_valid) {
		this.w2_valid = w2_valid;
	}

	public void setW2_expires_in(long w2_expires_in) {
		this.w2_expires_in = w2_expires_in;
	}

	public void setTaobao_user_id(long taobao_user_id) {
		this.taobao_user_id = taobao_user_id;
	}

	public void setW1_expires_in(long w1_expires_in) {
		this.w1_expires_in = w1_expires_in;
	}

	public void setR1_valid(long r1_valid) {
		this.r1_valid = r1_valid;
	}

	public void setR2_valid(long r2_valid) {
		this.r2_valid = r2_valid;
	}

	public void setW1_valid(long w1_valid) {
		this.w1_valid = w1_valid;
	}

	public void setR2_expires_in(long r2_expires_in) {
		this.r2_expires_in = r2_expires_in;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public void setRefresh_token_valid_time(long refresh_token_valid_time) {
		this.refresh_token_valid_time = refresh_token_valid_time;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

}
