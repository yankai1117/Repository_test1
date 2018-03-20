package com.xlpayment.encry;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DesSecretUtil
{
  private static final String Algorithm = "DESede";

  public static String encryptMode(byte[] src, String key)
  {
    try
    {
      Security.addProvider(new BouncyCastleProvider());
      SecretKeySpec deskey = new SecretKeySpec(buildDesKey(key), "DESede");
      Cipher c1 = Cipher.getInstance("DESede");
      c1.init(1, deskey);
      byte[] data = c1.doFinal(src);
      String dataStr = "";
      for (int i = 0; i < data.length; i++) {
        dataStr = dataStr + toHex(data[i]);
      }
      System.out.println(new String(data));
      return dataStr;
    } catch (NoSuchAlgorithmException e1) {
      e1.printStackTrace();
    } catch (NoSuchPaddingException e2) {
      e2.printStackTrace();
    } catch (Exception e3) {
      e3.printStackTrace();
    }
    return null;
  }

  public static String decryptMode(String src, String key)
  {
    try
    {
      Security.addProvider(new BouncyCastleProvider());
      SecretKey deskey = new SecretKeySpec(buildDesKey(key), "DESede");
      Cipher c1 = Cipher.getInstance("DESede");
      c1.init(2, deskey);
      byte[] data = hexStringToBytes(src);
      System.out.println(data);
      byte[] temp = c1.doFinal(data);
      System.out.println(new String(temp,"UTF-8"));
      return new String(temp,"UTF-8");
    } catch (NoSuchAlgorithmException e1) {
      e1.printStackTrace();
    } catch (NoSuchPaddingException e2) {
      e2.printStackTrace();
    } catch (Exception e3) {
      e3.printStackTrace();
    }
    return null;
  }

  public static byte[] buildDesKey(String keyStr)
    throws UnsupportedEncodingException
  {
    byte[] key = new byte[24];
    byte[] temp = keyStr.getBytes("UTF-8");

    if (key.length > temp.length)
    {
      System.arraycopy(temp, 0, key, 0, temp.length);
    }
    else {
      System.arraycopy(temp, 0, key, 0, key.length);
    }
    return key;
  }

  public static final String toHex(byte b)
  {
    return String.valueOf("0123456789ABCDEF".charAt(0xF & b >> 4)) + String.valueOf("0123456789ABCDEF".charAt(b & 0xF));
  }

  public static byte[] hexStringToBytes(String hexString)
  {
    if ((hexString == null) || (hexString.equals(""))) {
      return null;
    }
    hexString = hexString.toUpperCase();
    int length = hexString.length() / 2;
    char[] hexChars = hexString.toCharArray();
    byte[] d = new byte[length];
    for (int i = 0; i < length; i++) {
      int pos = i * 2;
      d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[(pos + 1)]));
    }
    return d;
  }
  private static byte charToByte(char c) {
    return (byte)"0123456789ABCDEF".indexOf(c);
  }

  public static void main(String[] args)
  {
    String hexString = "112233445566789";
    String key = "DaikouSign0001";
    String result = encryptMode(hexString.getBytes(), key);
    System.out.println(encryptMode(hexString.getBytes(), key));
    System.out.println(result.length());
    hexString = "127CC229BDAD8FA22D02A946D2C0314E37D9A0D6927C4D40 ";
    System.out.println(decryptMode(hexString, key));
  }
}