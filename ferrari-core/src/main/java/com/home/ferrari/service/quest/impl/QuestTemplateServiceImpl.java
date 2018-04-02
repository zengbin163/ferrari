package com.home.ferrari.service.quest.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.ferrari.FerrariUserDao;
import com.home.ferrari.dao.quest.QuestInvestigateDao;
import com.home.ferrari.dao.quest.QuestTemplateDao;
import com.home.ferrari.dao.quest.ShopInvestigateDao;
import com.home.ferrari.dao.shop.TeslaShopDao;
import com.home.ferrari.enums.AbcTypeEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.RoleTypeEnum;
import com.home.ferrari.enums.ShopTypeEnum;
import com.home.ferrari.enums.SubMsgTypeEnum;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.quest.QuestTemplateService;
import com.home.ferrari.service.sms.SendCustomerService;
import com.home.ferrari.util.DateUtil;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.ferrari.user.FerrariUser;
import com.home.ferrari.vo.quest.QuestInvestigate;
import com.home.ferrari.vo.quest.QuestInvestigateCount;
import com.home.ferrari.vo.quest.QuestTemplate;
import com.home.ferrari.vo.quest.ShopInvestigate;
import com.home.ferrari.vo.tesla.shop.TeslaShop;

@Service
public class QuestTemplateServiceImpl implements QuestTemplateService {

	@Autowired
	private QuestTemplateDao questTemplateDao;
	@Autowired
	private ShopInvestigateDao shopInvestigateDao;
	@Autowired
	private QuestInvestigateDao questInvestigateDao;
	@Autowired
	private FerrariUserDao ferrariUserDao;
	@Autowired
	private TeslaShopDao teslaShopDao;
	@Autowired
	private SendCustomerService sendCustomerService;
	
	private static final Logger logger = LoggerFactory.getLogger(QuestTemplateServiceImpl.class);

	@Override
	public Map<String, Object> saveQuestTemplate(QuestTemplate questTemplate) {
		Integer flag = this.questTemplateDao.insertQuestTemplate(questTemplate);
		if (flag < 1) {
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "问卷模板保存失败");
		}
		return ResultUtil.successMap("success");
	}

	@Override
	public Map<String, Object> getQuestTemplateListByType(Integer pageOffset, Integer pageSize, Integer tempType, Integer questType) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, Page.ORDER_BY_MODIFY_TIME);
		List<QuestTemplate> templateList = this.questTemplateDao.getQuestTemplateListByType(page, tempType, questType);
		if(CollectionUtils.isEmpty(templateList)) {
			return ResultUtil.successMap(templateList);
		}
		for(QuestTemplate qt : templateList) {
			FerrariUser ferrariUser = this.ferrariUserDao.getFerrariUserById(qt.getOperatorId());
			if(null!=ferrariUser){
				qt.setOperatorName(ferrariUser.getNickName());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sum", this.questTemplateDao.countQuestTemplateListByType(tempType, questType));
		map.put("list", templateList);
		map.put("headerSum", this.questTemplateDao.countQuestTemplateGroupByType());
		return ResultUtil.successMap(map);
	}

	@Override
	public Map<String, Object> deleteQuestTemplateById(Integer id) {
		Assert.notNull(id, "模板id不能为空");
		//Integer flag = this.questTemplateDao.deleteQuestTemplateById(id);
		//修改为逻辑删除
		QuestTemplate questTemplate = new QuestTemplate();
		questTemplate.setId(id);
		questTemplate.setFlag(DefaultEnum.NO.getCode());
		Integer flag = this.questTemplateDao.updateQuestTemplateById(questTemplate);
		if (flag < 1) {
			throw new FerrariBizException(ResultCode.DELETE_FAIL, "问卷模板删除失败");
		}
		return ResultUtil.successMap("success");
	}
	
	public Map<String, Object> getQuestTemplateById(Integer id) {
		Assert.notNull(id, "模板id不能为空");
		return ResultUtil.successMap(this.questTemplateDao.getQuestTemplateById(id));
	}

	@Override
	public Map<String, Object> updateQuestTemplateById(QuestTemplate questTemplate) {
		Integer flag = this.questTemplateDao.updateQuestTemplateById(questTemplate);
		if (flag < 1) {
			throw new FerrariBizException(ResultCode.UPDATE_FAIL, "问卷模板更新失败");
		}
		return ResultUtil.successMap("success");
	}

	public Map<String, Object> getQuestShopList(Integer pageOffset,
			Integer pageSize, String province, String city, String capacityName,
			String brandName, String shopName) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize);
		Map<String,Object> map = new HashMap<>();
		map.put("sum", this.questTemplateDao.countQuestShopList(province, city, null, capacityName, brandName, shopName));
		map.put("list", this.questTemplateDao.getQuestShopList(page, province, city, null, capacityName, brandName, shopName));
		return ResultUtil.successMap(map);
	}
	
	public List<TeslaShop> getMessageShopList(String province, String city, String capacityName, Integer shopType) {
		Page<?> page = new Page<>(0, Integer.MAX_VALUE);
		return this.questTemplateDao.getQuestShopList(page, province, capacityName, shopType, capacityName, null, null);
	}
	
	public Integer saveQuestInvestigate(QuestInvestigate questInvestigate) {
		this.questInvestigateDao.insertQuestInvestigate(questInvestigate);
		Integer id = questInvestigate.getId();
		if (id == null) {
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "发起调研失败");
		}
		return id;
	}
	
	public QuestInvestigate getQuestInvestigateById(Integer investigateId) {
		Assert.notNull(investigateId, "调研id不能为空");
		return this.questInvestigateDao.getQuestInvestigateById(investigateId);
	}
	
	public Map<String, Object> saveShopInvestigate(ShopInvestigate shopInvestigate) {
		ShopInvestigate shopInvestigateTemp = this.shopInvestigateDao
				.getShopInvestigateByShopIdAndInvestigateId(
						shopInvestigate.getShopId(),
						shopInvestigate.getInvestigateId(),
						shopInvestigate.getSubMsgType());
		if (null != shopInvestigateTemp) {
			return ResultUtil.successMap(ResultUtil.DATA_IS_EXISTS);
		}
		Integer flag = this.shopInvestigateDao.insertShopInvestigate(shopInvestigate);
		if (flag < 1) {
			throw new FerrariBizException(ResultCode.SAVE_FAIL, "问卷发送失败");
		}
		return ResultUtil.successMap("success");
	}

	public Map<String, Object> updateShopInvestigate(ShopInvestigate shopInvestigate) {
		Integer flag = this.shopInvestigateDao.updateShopInvestigate(shopInvestigate);
		if (flag < 1) {
			throw new FerrariBizException(ResultCode.UPDATE_FAIL, "问卷回复失败");
		}
		return ResultUtil.successMap("success");
	}
	
	public Map<String, Object> deleteInvestigateById(Integer investigateId) {
		Assert.notNull(investigateId, "调研id不能为空");
		this.questInvestigateDao.deleteQuestInvestigateById(investigateId);
		return ResultUtil.successMap("删除成功");
	}
	
	public Map<String, Object> getShopInvestigateListByQuestIdAndReply(Integer investigateId, Integer isReply) {
		Assert.notNull(investigateId, "investigateId不能为空");
		Assert.notNull(isReply, "isReply不能为空");
		return ResultUtil.successMap(this.shopInvestigateDao.getShopInvestigateListByInvestigateIdAndReply(investigateId, isReply));
	}
	
	public Map<String, Object> getShopInvestigateDetailById(Integer shopInvestigateId) {
		Assert.notNull(shopInvestigateId, "shopInvestigateId不能为空");
		ShopInvestigate shopInvestigate = this.shopInvestigateDao.getShopInvestigateDetailById(shopInvestigateId);
		if(null!=shopInvestigate){
			return ResultUtil.successMap(shopInvestigate);
		}else{
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
	}
	
	public Map<String, Object> getShopInvestigateListByShopId(Integer pageOffset, Integer pageSize, Integer shopId, Integer isReply, Integer platform) {
		Assert.notNull(shopId, "shopId不能为空");
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.create_time");
		Map<String,Object> map = new HashMap<>();
		map.put("list", this.shopInvestigateDao.getShopInvestigateListByShopId(page, shopId, isReply, platform));
		map.put("sum", this.shopInvestigateDao.countShopInvestigateListByShopId(shopId, isReply, platform));
		return ResultUtil.successMap(map);
	}

	@Override
	public Map<String, Object> sendMessage(Integer operatorId, String shopIds,
			Integer questId, Integer msgType, Long days, String title,
			String text, Integer subMsgType, String province, String city,
			String capacityName, Integer shopType, Integer isSms) {
		Assert.notNull(days, "间隔天数days不能为空");
		Assert.notNull(msgType, "消息类型不能为空");
		if(null==questId) {
			questId = -1000;
		}
		//先生成消息主体
		QuestInvestigate questInvestigate = new QuestInvestigate(questId, operatorId, new Date(), DateUtil.getIntervalTime(days), msgType, title, text);
		this.saveQuestInvestigate(questInvestigate);
		Integer questInvestigateId = questInvestigate.getId();
		//发送消息
		this.sendMsg(operatorId, questInvestigateId, shopIds, msgType, subMsgType, province, city, capacityName, shopType, isSms);
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}
	
	public Map<String, Object> sendOthersMessage(Integer operatorId,
			String shopIds, Integer investigateId, Integer msgType,
			Integer subMsgType, String province, String city,
			String capacityName, Integer shopType, Integer isSms) {
		Assert.notNull(investigateId, "消息体不能为空");
		QuestInvestigate questInvestigate = this.getQuestInvestigateById(investigateId);
		if(null == questInvestigate) {
			throw new FerrariBizException(ResultCode.NOT_EXISTS, String.format("消息体不存在[investigateId=%s]", investigateId));
		}
		Date beginTime = questInvestigate.getBeginTime();
		Date endTime = questInvestigate.getEndTime();
		Date currentTime = new Date();
		if(currentTime.before(beginTime) || currentTime.after(endTime)) {
			throw new FerrariBizException(ResultCode.SHOP_QUEST_EXPIRE, String.format("消息体已经过期，investigateId=%s", investigateId));
		}
		//发送消息
		this.sendMsg(operatorId, investigateId, shopIds, msgType, subMsgType, province, city, capacityName, shopType, isSms);
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}
	
	public void sendMsg(Integer operatorId, Integer questInvestigateId, String shopIds,
			Integer msgType, Integer subMsgType, String province, String city,
			String capacityName, Integer shopType, Integer isSms) {
		Assert.notNull(questInvestigateId, "消息体id不能为空");
		Assert.notNull(msgType, "消息类型不能为空");
		logger.error("===================subMsgType=" + subMsgType);
		
		List<TeslaShop> shopList = null;
		if(SubMsgTypeEnum.ALL_SHOP.getCode().equals(subMsgType)) {
			//给所有门店发消息（只发送给入驻中和已上线中的门店列表）
			Page<?> page = new Page<>(0, Integer.MAX_VALUE);
			String[] shopStatusList = { "100", "101", "102", "103", "104",
					"105", "106", "444" };
			shopList = this.teslaShopDao.getTeslaShopList(page, null, null,null,
					shopStatusList, null, null, null, null, null, null);
		}else if(SubMsgTypeEnum.ALL_STAFF.getCode().equals(subMsgType)){
			//给所有星奥员工发消息
			List<FerrariUser> ferrariUserList = this.ferrariUserDao.getAllFerrariUserList();
			String []mobiles = new String[ferrariUserList.size()];
			for (int i = 0; i < ferrariUserList.size(); i++) {
				FerrariUser ferrariUser = ferrariUserList.get(i);
				ShopInvestigate shopInvestigate = new ShopInvestigate(
						ferrariUser.getId(), questInvestigateId, null, msgType,
						subMsgType, DefaultEnum.NO.getCode(), null, null, null,
						null, null, null, null);
				this.saveShopInvestigate(shopInvestigate);
				mobiles[i] = ferrariUser.getPhone();
			}
			//发短信
			if(DefaultEnum.YES.getCode().equals(isSms)) {
				sendCustomerService.sendSmsByMobiles(operatorId, operatorId, 1, mobiles, "您有一个新的消息通知，请尽快查看");
			}
		}else if(SubMsgTypeEnum.SHOP_LIST.getCode().equals(subMsgType)){
			shopList = this.getMessageShopList(province, city, capacityName, shopType);
		}else if(SubMsgTypeEnum.RESULT_LIST.getCode().equals(subMsgType)){
			//传入的门店id
			String []sIds = shopIds.split(",");
			String []mobiles = new String[sIds.length];
			for (int i = 0; i < sIds.length; i++) {
				Integer shopId = Integer.valueOf(sIds[i]);
				ShopInvestigate shopInvestigate = new ShopInvestigate(shopId,
						questInvestigateId, null, msgType, subMsgType,
						DefaultEnum.NO.getCode(), null, null, null, null, null,
						null, null);
				this.saveShopInvestigate(shopInvestigate);
				TeslaShop shop = this.teslaShopDao.getTeslaShopById(shopId);
				mobiles[i] = shop.getLinkPhone();
			}
			//发短信
			if(DefaultEnum.YES.getCode().equals(isSms)) {
				sendCustomerService.sendSmsByMobiles(operatorId, operatorId, 1, mobiles, "您有一个新的消息通知，请尽快查看");
			}
		}else if(SubMsgTypeEnum.MY_SHOP.getCode().equals(subMsgType)){
			Page<?> page = new Page<>(0, Integer.MAX_VALUE);
			shopList = this.teslaShopDao.getTeslaShopList(page, null, null,null,
					null, null, null, RoleTypeEnum.ROLE_BD.getCode(),
					operatorId, null, null);
		}else if(SubMsgTypeEnum.CUNANDCHE.getCode().equals(subMsgType)){
			Page<?> page = new Page<>(0, Integer.MAX_VALUE);
			shopList = this.teslaShopDao.getTeslaShopList(page, null, null,null,
					null, null, null, null, null,
					ShopTypeEnum.CHEMATOUCUNTAO.getCode(), null);
		}else if(SubMsgTypeEnum.CUNTAO.getCode().equals(subMsgType)){
			Page<?> page = new Page<>(0, Integer.MAX_VALUE);
			shopList = this.teslaShopDao.getTeslaShopList(page, null, null,null,
					null, null, null, null, null,
					ShopTypeEnum.CUNTAO.getCode(), null);
		}else if(SubMsgTypeEnum.XINGAO.getCode().equals(subMsgType)){
			Page<?> page = new Page<>(0, Integer.MAX_VALUE);
			shopList = this.teslaShopDao.getTeslaShopList(page, null, null,null,
					null, null, null, null, null,
					ShopTypeEnum.XINGAO.getCode(), null);
		}else if(SubMsgTypeEnum.CHE.getCode().equals(subMsgType)){
			Page<?> page = new Page<>(0, Integer.MAX_VALUE);
			shopList = this.teslaShopDao.getTeslaShopList(page, null, null,null,
					null, null, null, null, null,
					ShopTypeEnum.CHEMATOU.getCode(), null);
		}else if(SubMsgTypeEnum.A.getCode().equals(subMsgType)){
			Page<?> page = new Page<>(0, Integer.MAX_VALUE);
			shopList = this.teslaShopDao.getTeslaShopList(page, null, null,null,
					null, null, null, null, null,
					null, AbcTypeEnum.A.getCode());
		}else if(SubMsgTypeEnum.B.getCode().equals(subMsgType)){
			Page<?> page = new Page<>(0, Integer.MAX_VALUE);
			shopList = this.teslaShopDao.getTeslaShopList(page, null, null,null,
					null, null, null, null, null,
					null, AbcTypeEnum.B.getCode());
		}else if(SubMsgTypeEnum.C.getCode().equals(subMsgType)){
			Page<?> page = new Page<>(0, Integer.MAX_VALUE);
			shopList = this.teslaShopDao.getTeslaShopList(page, null, null,null,
					null, null, null, null, null,
					null, AbcTypeEnum.C.getCode());
		}else{
			logger.error("=========subMsgType error");
		}
		if(null!=shopList) {
			String []mobiles = new String[shopList.size()];
			for (int i = 0; i < shopList.size(); i++) {
				TeslaShop shop = shopList.get(i);
				Integer shopId = shop.getId();
				ShopInvestigate shopInvestigate = new ShopInvestigate(shopId,
						questInvestigateId, null, msgType, subMsgType,
						DefaultEnum.NO.getCode(), null, null, null, null, null,
						null, null);
				this.saveShopInvestigate(shopInvestigate);
				mobiles[i] = shop.getLinkPhone();
			}
			//发短信
			if(DefaultEnum.YES.getCode().equals(isSms)) {
				sendCustomerService.sendSmsByMobiles(operatorId, operatorId, 1, mobiles, "您有一个新的消息通知，请尽快查看");
			}
		}
	}
	
	public Map<String, Object> messageList(Integer pageOffset, Integer pageSize, Integer msgType, Integer roleType, Integer operatorId) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t2.create_time");
		List<QuestInvestigateCount> questInvestigateCountList = this.questInvestigateDao.getMessageList(page, msgType, roleType, operatorId);
		if(CollectionUtils.isEmpty(questInvestigateCountList)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
		for(QuestInvestigateCount qcount : questInvestigateCountList) {
			//反馈情况
			List<Map<String, Object>> listmap = this.shopInvestigateDao.getAgreeListByInvestigateId(qcount.getInvestigateId(), null);
			Integer agree = 0;
			Integer noAgree = 0;
			Integer noReply = 0;
			if(!CollectionUtils.isEmpty(listmap)) {
				for(Map<String, Object> map : listmap) {
					if(Integer.valueOf("1").equals(map.get("isAgree"))) {
						agree = Integer.valueOf(map.get("count").toString());
					} else if(Integer.valueOf("0").equals(map.get("isAgree"))) {
						noAgree = Integer.valueOf(map.get("count").toString());
					} else{
						noReply = Integer.valueOf(map.get("count").toString());
					}
				}
			}
			qcount.setAgree(agree);
			qcount.setReply(agree + noAgree);
			qcount.setNoReply(noReply);
			//阅读情况
			List<Map<String, Object>> listmap2 = this.shopInvestigateDao.getReadListByInvestigateId(qcount.getInvestigateId(), null);
			Integer read = 0;
			Integer noRead = 0;
			Integer errorRead = 0;
			if(!CollectionUtils.isEmpty(listmap2)) {
				for(Map<String, Object> map : listmap2) {
					if(Integer.valueOf("1").equals(map.get("isRead"))) {
						read = Integer.valueOf(map.get("count").toString());
					} else if(Integer.valueOf("0").equals(map.get("isRead"))) {
						noRead = Integer.valueOf(map.get("count").toString());
					} else{
						errorRead = Integer.valueOf(map.get("count").toString());
					}
				}
			}
			qcount.setRead(read);
			qcount.setNoRead(noRead + errorRead);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("list", questInvestigateCountList);
		map.put("sum", this.questInvestigateDao.countMessageList(msgType, roleType, operatorId));
		return ResultUtil.successMap(map);
	}
	
	public Map<String, Object> readMsg(Integer shopInvestigateId) {
		this.shopInvestigateDao.updateShopInvestigateIsRead(shopInvestigateId);
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}
	
	public Map<String, Object> msgStat(Integer investigateId, Integer isReply) {
		Assert.notNull(investigateId, "消息体id不能为空");
		Assert.notNull(isReply, "回复类型不能为空");
		Map<String,Object> mapList = new HashMap<>();
		mapList.put("msgList", this.shopInvestigateDao.getShopInvestigateListByInvestigateIdAndReply(investigateId, isReply));
		//反馈情况
		List<Map<String, Object>> listmap = this.shopInvestigateDao.getAgreeListByInvestigateId(investigateId, null);
		Integer agree = 0;
		Integer noAgree = 0;
		Integer noReply = 0;
		if(!CollectionUtils.isEmpty(listmap)) {
			for(Map<String, Object> map : listmap) {
				if(Integer.valueOf("1").equals(map.get("isAgree"))) {
					agree = Integer.valueOf(map.get("count").toString());
				} else if(Integer.valueOf("0").equals(map.get("isAgree"))) {
					noAgree = Integer.valueOf(map.get("count").toString());
				} else{
					noReply = Integer.valueOf(map.get("count").toString());
				}
			}
		}
		mapList.put("reply", agree + noAgree);
		mapList.put("noReply", noReply);
		//阅读情况
		List<Map<String, Object>> listmap2 = this.shopInvestigateDao.getReadListByInvestigateId(investigateId, null);
		Integer read = 0;
		Integer noRead = 0;
		Integer errorRead = 0;
		if(!CollectionUtils.isEmpty(listmap2)) {
			for(Map<String, Object> map : listmap2) {
				if(Integer.valueOf("1").equals(map.get("isRead"))) {
					read = Integer.valueOf(map.get("count").toString());
				} else if(Integer.valueOf("0").equals(map.get("isRead"))) {
					noRead = Integer.valueOf(map.get("count").toString());
				} else{
					errorRead = Integer.valueOf(map.get("count").toString());
				}
			}
		}
		mapList.put("read", read);
		mapList.put("noRead", noRead + errorRead);
		return ResultUtil.successMap(mapList);
	}
	
	public Map<String,Object> msgBody(Integer investigateId) {
		Assert.notNull(investigateId, "消息体id不能为空");
		return ResultUtil.successMap(this.questInvestigateDao.getQuestInvestigateDetailById(investigateId));
	}
	
	public List<Map<String, Object>> getExportMessageByMsgType(Integer pageOffset, Integer pageSize, Integer msgType) {
		Page<?> page = new Page<>(pageOffset, pageSize, Page.ASC, "t2.title");
		return this.shopInvestigateDao.getExportMessageByMsgType(page, msgType);
	}
}
