package com.zyp.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES对称加密工具类
 *
 * @author 星空流年
 */
public class AesUtil {
    /**
     * 偏移量
     *
     * 说明：偏移量字符串必须是16位 当模式是CBC的时候必须设置偏移量
     * 此处值与前端偏移量值保持一致
     */
    private static String iv = "37fa77f6a3b0462d";

    /**
     * 加密算法
     */
    private static String Algorithm = "AES";

    /**
     * 算法/模式/补码方式
     */
    private static String AlgorithmProvider = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param src 加密内容
     * @param uniqueKey 加密key
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String src, String uniqueKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
        byte[] key = uniqueKey.getBytes();
        SecretKey secretKey = new SecretKeySpec(key, Algorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] cipherBytes = cipher.doFinal(src.getBytes("UTF-8"));
        return byteToHexString(cipherBytes);
    }

    /**
     * 解密
     *
     * @param enc       加密内容
     * @param uniqueKey 唯一key
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws DecoderException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String decrypt(String enc, String uniqueKey) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidAlgorithmParameterException, InvalidKeyException, DecoderException, BadPaddingException, IllegalBlockSizeException {
        byte[] key = uniqueKey.getBytes();
        SecretKey secretKey = new SecretKeySpec(key, Algorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("utf-8"));
        Cipher cipher = Cipher.getInstance(AlgorithmProvider);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        byte[] hexBytes = hexStringToBytes(enc);
        byte[] plainBytes = cipher.doFinal(hexBytes);
        return new String(plainBytes, "UTF-8");
    }

    /**
     * 将byte数组转换为16进制字符串
     *
     * @param src
     * @return
     */
    private static String byteToHexString(byte[] src) {
        return Hex.encodeHexString(src);
    }

    /**
     * 将16进制字符串转换为byte数组
     *
     * @param hexString
     * @return
     */
    private static byte[] hexStringToBytes(String hexString) throws DecoderException {
        return Hex.decodeHex(hexString);
    }

    /**
     * AES加密、解密测试方法
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            // 唯一key作为密钥
            String uniqueKey = "385f33cb91484b04a177828829081ab7";
            String encrypt = encrypt("123456", uniqueKey);
            System.out.println(encrypt);
            // 解密
//            String decrypt = decrypt("5e304394bd479fdf65b06a77f97f97bc", uniqueKey);
//            System.out.println(decrypt);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
