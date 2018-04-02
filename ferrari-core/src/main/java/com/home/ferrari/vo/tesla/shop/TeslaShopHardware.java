package com.home.ferrari.vo.tesla.shop;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 门店车间硬件
 * 
 * @author Administrator
 *
 */
public class TeslaShopHardware implements Serializable {

	private static final long serialVersionUID = -3900004149275877382L;

	private Integer id;
	private Integer shopId;
	private Integer carLeft;// 举升机数量
	private Integer fourWheel;// 四轮定位仪数量
	private Integer engine;// 发动机吊机数量
	private Integer press;// 扒胎机数量
	private Integer jack;// 千斤顶数量
	private Integer curingBarn;// 烤房数量
	private Integer polisher;// 油漆抛光机
	private Integer grinding;// 钣金砂轮机
	private Integer carWash;// 洗车泡沫机
	private Integer hairdressing;// 美容抛光机
	private String specialComputer;// 专检电脑
	private String commonComputer;// 通用电脑
	private Integer balancer;// 动平衡机
	private Integer cleaner;// 空调清洗机
	private Integer hotLamps;// 烤灯数
	private Integer sander;// 钣金打磨机
	private Integer inspection;// 钣金事故校正机
	private Integer carCleaner;// 车内吸尘器数量
	private Integer waterTorch;// 高压水枪
	private Integer transJack;// 变速箱千斤顶
	private Integer tester;// 尾气测试机
	private Integer vacuumCleaner;// 油漆干磨/吸尘机
	private Integer repair;// 钣金修复机
	private Integer carWasher;// 洗车机数量
	private Integer coatingMachine;// 镀膜机
	private Integer nozzle;// 普通水枪
	private Date createTime;
	private Date modifyTime;

	public TeslaShopHardware() {

	}

	public TeslaShopHardware(Integer shopId, Integer carLeft,
			Integer fourWheel, Integer engine, Integer press, Integer jack,
			Integer curingBarn, Integer polisher, Integer grinding,
			Integer carWash, Integer hairdressing, String specialComputer,
			String commonComputer, Integer balancer, Integer cleaner,
			Integer hotLamps, Integer sander, Integer inspection,
			Integer carCleaner, Integer waterTorch, Integer transJack,
			Integer tester, Integer vacuumCleaner, Integer repair,
			Integer carWasher, Integer coatingMachine, Integer nozzle) {
		this.shopId = shopId;
		this.carLeft = carLeft;
		this.fourWheel = fourWheel;
		this.engine = engine;
		this.press = press;
		this.jack = jack;
		this.curingBarn = curingBarn;
		this.polisher = polisher;
		this.grinding = grinding;
		this.carWash = carWash;
		this.hairdressing = hairdressing;
		this.specialComputer = specialComputer;
		this.commonComputer = commonComputer;
		this.balancer = balancer;
		this.cleaner = cleaner;
		this.hotLamps = hotLamps;
		this.sander = sander;
		this.inspection = inspection;
		this.carCleaner = carCleaner;
		this.waterTorch = waterTorch;
		this.transJack = transJack;
		this.tester = tester;
		this.vacuumCleaner = vacuumCleaner;
		this.repair = repair;
		this.carWasher = carWasher;
		this.coatingMachine = coatingMachine;
		this.nozzle = nozzle;
	}

	public static TeslaShopHardware create(String shopHardwareJson) {
		JSONObject json = JSONObject.parseObject(shopHardwareJson);
		TeslaShopHardware teslaShopHardware = new TeslaShopHardware(
				json.getInteger("shopId"), json.getInteger("carLeft"),
				json.getInteger("fourWheel"), json.getInteger("engine"),
				json.getInteger("press"), json.getInteger("jack"),
				json.getInteger("curingBarn"), json.getInteger("polisher"),
				json.getInteger("grinding"), json.getInteger("carWash"),
				json.getInteger("hairdressing"),
				json.getString("specialComputer"),
				json.getString("commonComputer"), json.getInteger("balancer"),
				json.getInteger("cleaner"), json.getInteger("hotLamps"),
				json.getInteger("sander"), json.getInteger("inspection"),
				json.getInteger("carCleaner"), json.getInteger("waterTorch"),
				json.getInteger("transJack"), json.getInteger("tester"),
				json.getInteger("vacuumCleaner"), json.getInteger("repair"),
				json.getInteger("carWasher"),
				json.getInteger("coatingMachine"), json.getInteger("nozzle"));
		return teslaShopHardware;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getCarLeft() {
		return carLeft;
	}

	public void setCarLeft(Integer carLeft) {
		this.carLeft = carLeft;
	}

	public Integer getFourWheel() {
		return fourWheel;
	}

	public void setFourWheel(Integer fourWheel) {
		this.fourWheel = fourWheel;
	}

	public Integer getEngine() {
		return engine;
	}

	public void setEngine(Integer engine) {
		this.engine = engine;
	}

	public Integer getPress() {
		return press;
	}

	public void setPress(Integer press) {
		this.press = press;
	}

	public Integer getJack() {
		return jack;
	}

	public void setJack(Integer jack) {
		this.jack = jack;
	}

	public Integer getCuringBarn() {
		return curingBarn;
	}

	public void setCuringBarn(Integer curingBarn) {
		this.curingBarn = curingBarn;
	}

	public Integer getPolisher() {
		return polisher;
	}

	public void setPolisher(Integer polisher) {
		this.polisher = polisher;
	}

	public Integer getGrinding() {
		return grinding;
	}

	public void setGrinding(Integer grinding) {
		this.grinding = grinding;
	}

	public Integer getCarWash() {
		return carWash;
	}

	public void setCarWash(Integer carWash) {
		this.carWash = carWash;
	}

	public Integer getHairdressing() {
		return hairdressing;
	}

	public void setHairdressing(Integer hairdressing) {
		this.hairdressing = hairdressing;
	}

	public String getSpecialComputer() {
		return specialComputer;
	}

	public void setSpecialComputer(String specialComputer) {
		this.specialComputer = specialComputer;
	}

	public String getCommonComputer() {
		return commonComputer;
	}

	public void setCommonComputer(String commonComputer) {
		this.commonComputer = commonComputer;
	}

	public Integer getBalancer() {
		return balancer;
	}

	public void setBalancer(Integer balancer) {
		this.balancer = balancer;
	}

	public Integer getCleaner() {
		return cleaner;
	}

	public void setCleaner(Integer cleaner) {
		this.cleaner = cleaner;
	}

	public Integer getHotLamps() {
		return hotLamps;
	}

	public void setHotLamps(Integer hotLamps) {
		this.hotLamps = hotLamps;
	}

	public Integer getSander() {
		return sander;
	}

	public void setSander(Integer sander) {
		this.sander = sander;
	}

	public Integer getInspection() {
		return inspection;
	}

	public void setInspection(Integer inspection) {
		this.inspection = inspection;
	}

	public Integer getCarCleaner() {
		return carCleaner;
	}

	public void setCarCleaner(Integer carCleaner) {
		this.carCleaner = carCleaner;
	}

	public Integer getWaterTorch() {
		return waterTorch;
	}

	public void setWaterTorch(Integer waterTorch) {
		this.waterTorch = waterTorch;
	}

	public Integer getTransJack() {
		return transJack;
	}

	public void setTransJack(Integer transJack) {
		this.transJack = transJack;
	}

	public Integer getTester() {
		return tester;
	}

	public void setTester(Integer tester) {
		this.tester = tester;
	}

	public Integer getVacuumCleaner() {
		return vacuumCleaner;
	}

	public void setVacuumCleaner(Integer vacuumCleaner) {
		this.vacuumCleaner = vacuumCleaner;
	}

	public Integer getRepair() {
		return repair;
	}

	public void setRepair(Integer repair) {
		this.repair = repair;
	}

	public Integer getCarWasher() {
		return carWasher;
	}

	public void setCarWasher(Integer carWasher) {
		this.carWasher = carWasher;
	}

	public Integer getCoatingMachine() {
		return coatingMachine;
	}

	public void setCoatingMachine(Integer coatingMachine) {
		this.coatingMachine = coatingMachine;
	}

	public Integer getNozzle() {
		return nozzle;
	}

	public void setNozzle(Integer nozzle) {
		this.nozzle = nozzle;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
