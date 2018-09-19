//package com.sorinaidea.arayeshgah.util;
//
//import org.apache.commons.codec.binary.Base64;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//
//import javax.crypto.Cipher;
//
//
//
///**
// * Created by moeid heidari on July 26,2018
// *
// */
//
//public class RSA {
//
//
//    public static PrivateKey getPrivateKeyFromString(String key) throws IOException, GeneralSecurityException {
//        java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        PKCS8EncodedKeySpec keySpecPKCS1 = new PKCS8EncodedKeySpec(Base64.decodeBase64(key));
//        PrivateKey privKey = kf.generatePrivate(keySpecPKCS1);
//        return privKey;
//    }
//
//
//    public static PublicKey getPublicKeyFromString(String key) throws IOException, GeneralSecurityException {
//        byte[] publicBytes = Base64.decodeBase64(key);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PublicKey pubKey = keyFactory.generatePublic(keySpec);
//        return pubKey;
//    }
//
//    public static String encrypt(String rawText, PublicKey publicKey) throws IOException, GeneralSecurityException {
//        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        return Base64.encodeBase64String(cipher.doFinal(rawText.getBytes("UTF-8")));
//    }
//
//    public static String decrypt(String cipherText, PrivateKey privateKey) throws IOException, GeneralSecurityException
//    {
//        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), "UTF-8");
//    }
//
//
//}