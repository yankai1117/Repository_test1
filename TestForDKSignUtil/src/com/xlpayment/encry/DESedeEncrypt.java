package com.xlpayment.encry;


import java.security.SecureRandom;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESedeEncrypt
{
  private static final String DEFAULT_ENCODING = "UTF-8";
  public static final String KEY_ALGORITHM = "DESede";

  public static String generateKey(String seed)
    throws Exception
  {
    return generateKey(seed, "UTF-8");
  }

  public static String generateKey(String seed, String encoding)
    throws Exception
  {
    KeyGenerator generator = KeyGenerator.getInstance("DESede");
    generator.init(168, new SecureRandom(seed.getBytes(encoding)));
    SecretKey key = generator.generateKey();
    byte[] buffer = key.getEncoded();
    return byte2hex(buffer).toUpperCase();
  }

  public static String encrypt(String strKey, String strIn)
    throws Exception
  {
    byte[] bKey = hex2byte(strKey.getBytes());
    SecretKeySpec key = new SecretKeySpec(bKey, "DESede");

    Cipher encryptCipher = Cipher.getInstance("DESede/ECB/NoPadding");
    encryptCipher.init(1, key);

    if ((strIn == null) || ("".equals(strIn)))
    {
      return "";
    }

    byte[] bData = strIn.getBytes("UTF-8");

    int iLenOfSourceData = bData.length;
    int iMod = iLenOfSourceData % 8;
    byte[] bCryptData = (byte[])null;

    if (iMod == 0)
    {
      bCryptData = (byte[])bData.clone();
    }
    else
    {
      bCryptData = new byte[iLenOfSourceData + 8 - iMod];
      System.arraycopy(bData, 0, bCryptData, 0, iLenOfSourceData);

      for (int i = 0; i < 8 - iMod; i++)
      {
        bCryptData[(iLenOfSourceData + i)] = 10;
      }
    }

    return byte2hex(encryptCipher.doFinal(bCryptData));
  }

  public static String decrypt(String strKey, String strIn)
    throws Exception
  {
    byte[] bKey = hex2byte(strKey.getBytes());
    SecretKeySpec key = new SecretKeySpec(bKey, "DESede");
    Cipher decryptCipher = Cipher.getInstance("DESede/ECB/NoPadding");
    decryptCipher.init(2, key);
    return new String(decryptCipher.doFinal(hex2byte(strIn.getBytes())), "UTF-8").trim();
  }

  public static final String byte2hex(byte[] b)
  {
    StringBuilder sb = new StringBuilder();
    String stmp = "";
    for (int n = 0; n < b.length; n++)
    {
      stmp = Integer.toHexString(b[n] & 0xFF);
      if (stmp.length() == 1)
      {
        sb.append("0").append(stmp);
      }
      else
      {
        sb.append(stmp);
      }
    }
    return sb.toString().toUpperCase(Locale.getDefault());
  }

  public static final byte[] hex2byte(byte[] b)
  {
    if (b.length % 2 != 0)
    {
      throw new IllegalArgumentException("The byte Array's length is not even!");
    }
    byte[] b2 = new byte[b.length / 2];
    for (int n = 0; n < b.length; n += 2)
    {
      String item = new String(b, n, 2);
      b2[(n / 2)] = (byte)Integer.parseInt(item, 16);
    }
    return b2;
  }

  public static void main(String[] args)
  {
    try
    {
      System.out.println("Key:" + generateKey("DaikouSign0001"));

      String test = "123456";
      System.out.println("加密前的字符：" + test);

      String stren = encrypt("D543CB3EB6ADA879A8D5260E79976183AD3B915DFEADFD16", test);
      System.out.println("加密后的字符：" + stren);

      String strde = decrypt("D543CB3EB6ADA879A8D5260E79976183AD3B915DFEADFD16", stren);
      System.out.println("解密后的字符：" + strde.trim());
      
      String x = decrypt("D543CB3EB6ADA879A8D5260E79976183AD3B915DFEADFD16", "7DBACA37C4C65AD88C3D6E1ABC873E2E2423558807091D39");
      System.out.println("解密后的字符：" + x.trim());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}