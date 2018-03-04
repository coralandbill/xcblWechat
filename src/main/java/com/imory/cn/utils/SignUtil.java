package com.imory.cn.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/***
 * 请求校验工具类
 */
public class SignUtil {
    //微信token 该处需要根微信验证token一致
	private static String token = "_imory_weixin_token_";

    /***
     *校验签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
        //对token，timestamp，nonce按字典排序
		String[] paramArr = new String[] { token, timestamp, nonce };
		Arrays.sort(paramArr);
        //排序后的结果拼接成一个字符串
		String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);
		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
            //对拼接之后的字符串进行sha－1加密
			byte[] digest = md.digest(content.toString().getBytes());
			ciphertext = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		//将sha-1加密后的字符串与singnature进行对比
		return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
	}

    /***
     *将字节数组转换为十六进制字符串
     * @param byteArray
     * @return
     */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

    /***
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}
}
