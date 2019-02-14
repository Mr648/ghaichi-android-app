package com.sorinaidea.ghaichi.util;

import android.util.Base64;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


/**
 * Created by moeid heidari on July 26,2018
 */

public class RSA {


    public static PrivateKey getPrivate() throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.decode("MIIEogIBAAKCAQEAuvSrnLWckeaeKDor5Wl6IllXaRsk8njxwu9Jg8Ud8ARJ5auyEzVRTIA/7WjjQzZ30kdpgVXJ+rZxwon1H/L9uhJWG/w3RFxoKmT8GE+9yPAXHAK2oGcYgs2fq5LGQQA3vfBIEMs0TTCGM/dAfwwr6wV/r4lGTNLYdXapd9MXi6/f3TsUlQB6TnOa+gnR3msSphnDY6cKT2xIcGHdtlRHf+UN/nGn96IWlflhD/tHOuplR0s6+lS9WZmpIM7f2YYBGxqJE6MkYC3lS40xuXsMV8I1bxSQ2ZRwH0CLsWYOG2+9rMCP+6nhMygUx9onqD5GMAc4QNttPgaS9hJxXh403QIDAQABAoIBAF1ocXXSp2+eMdcXeWcd1A/lY274SPV8yA4FUE2t5s9M1T8TEd3W/c+jJE2R4jpqtGMzmtsr6caB8USpePeFiI+3KZ7B3TsikEjeO7ZJ4SDMFD/Ce0yZNf3kjqpCoxtG6JLVwqvhWzhVzjeYiXjws+Cw1qL1i1JG5i+KIhHkPhdj5XFKy3xtns2yLZUOkZ5AnI62HFnmuAatcxWB3Rv5fQHDcDEjCTrw4/Juu6jjCf20+KLzWcHHMW89p+rxc4KTFyOsB2iOGxLNK9B24EcWaec4QMR7gJTMlUbx2vfy/bOpKN/3XUgqSyZaDzVdoJOJ4STZ71HDNSlp6syhirtVe2ECgYEA2z42BwqBX2at/5eW0uAZBbytLCdiO6ssyq1pMpgS4FNuJ8Y35hJVyy6DH9ernltV3Vw06u0aYBK7iFeoLqUSXvZog/sRTY4ERLm2U1wwYJarvazGY5utseQX273pVbCfyS9MsilE3oHo9/7OguvlLnhvA4Gwhmd1TVoIwaaWMzsCgYEA2ky1JQBzAiyAh+BSS4MGeR8RT/7jKHpMrLXcUJGZ1cqzrbR6mVG8YqUsdzMxTWzxHJcemDB/E7w18u9bHiDSwIzfN2jV3FFg/bgIpjbWE+lZtGQz/jR6pc+GPapzG1ANNuenTPgs8Yy92W2Q0dJ4VDV5pq0id2hudqgjRcnrBscCgYArPC1zXgL9SUZu7Pn1Q/QrSXXtCyc4FR2UKnZgX9T5H/rNJS3I1TzOALIjCWjypZhdOt3EplVZNNh+nqqv6CWjTHx36n8TCDqXQvhNoBR6N7xOf0/wPLcYKZ762oy1NUuNZSJ+z9yg9IV4wmr/Ln3XmDBsRJsHJkffpS2s4qkkGQKBgE180wP6X9iSsWEJ2ExjiwWtaHjL0hhJKTRZLYYP2NpRpGvxV19a6xLc7Ka3LjX8iK2MWtjMxh7cPQ4ptl9AaW7d0lIZzWfyHwqQq8lMR7szpprowqfmOu7mdhEnkspMJRSrL/CbW2C2g6FNSg2gtwO1I6RtaoGWOTe3QSYBoYJ7AoGAAOe0jnZRwPH6/2lmlrjreTzS0IUYB0wy/UIKt4Ck+Yen/CNn92KHYTas2iL9pCKzWVqggEMLDUEzsv+flCkhzsEF8vThuq9mC+BBRrwhNBoiMNi51z9A9/Hjb0wTLwIsv3O5oK+T0JRl8U/pgw3A9jO6Kx6Ln5zINDiAKqfnF0Q=", Base64.DEFAULT));
        PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);

        return privKey;
    }

    public static PublicKey getPublic() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");


        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.decode("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuvSrnLWckeaeKDor5Wl6IllXaRsk8njxwu9Jg8Ud8ARJ5auyEzVRTIA/7WjjQzZ30kdpgVXJ+rZxwon1H/L9uhJWG/w3RFxoKmT8GE+9yPAXHAK2oGcYgs2fq5LGQQA3vfBIEMs0TTCGM/dAfwwr6wV/r4lGTNLYdXapd9MXi6/f3TsUlQB6TnOa+gnR3msSphnDY6cKT2xIcGHdtlRHf+UN/nGn96IWlflhD/tHOuplR0s6+lS9WZmpIM7f2YYBGxqJE6MkYC3lS40xuXsMV8I1bxSQ2ZRwH0CLsWYOG2+9rMCP+6nhMygUx9onqD5GMAc4QNttPgaS9hJxXh403QIDAQAB", Base64.DEFAULT));
        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);


        return pubKey;
    }

    public static byte[] encrypt(PublicKey publicKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(message.getBytes());
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encrypted);
    }


}