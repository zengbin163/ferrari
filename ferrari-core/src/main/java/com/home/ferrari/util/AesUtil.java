package com.home.ferrari.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AesUtil {
	
    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

	public static void main(String[] args) throws Exception {
		/*
		 * 此处使用AES-128-ECB加密模式，key需要为16位。
		 */
		String cKey = "xacl_007ppy_17rs";
		// 需要加密的字串
		String shopId = "xacl_11680\\x08\\x08\\x08\\x08\\x08\\x08";
		// 加密
		Long time = System.currentTimeMillis()/1000;
		String userId = AesUtil.Encrypt(shopId, cKey);
		String xx = "" + userId + "_" + time + "_" + "bd9ww44935c3f9a3376349ad1c74gg667";
		String sign = MD5Util.md5(xx);
		System.out.println("userId :  |" + userId + "|");
		System.out.println("userId :  |" + AesUtil.Decrypt(userId, cKey)  + "|");
		System.out.println("userId :  |" + AesUtil.Decrypt("zo/rki3f1nWZoxTvKD9S4w", cKey)  + "|");
		System.out.println(xx);
		System.out.println("sign :  " + sign);
		System.out.println("t :  " + time);
		System.out.println(MD5Util.md5("61a99aaf550ae276db0f21b4b07f3b8a_1493881747_bd9ww44935c3f9a3376349ad1c74gg667"));
	}
}
