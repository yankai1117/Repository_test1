package com.xlpayment.encry;


import com.hisun.exception.HiException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Vip3DES
{
  private static DESKEY keyInstance = DESKEY.getInstance();

  private static char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7', 
    '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

  public static String toHexString(byte[] b)
  {
    StringBuilder sb = new StringBuilder(b.length * 2);
    for (int i = 0; i < b.length; i++) {
      sb.append(HEXCHAR[((b[i] & 0xF0) >>> 4)]);//十六进制 f0   对应十进制240   对应二进制11110000
      sb.append(HEXCHAR[(b[i] & 0xF)]);//16进制的f转成10进制为15 , 15转换成二进制为 0000 1111
    }
    return sb.toString();
  }

  public static final byte[] toBytes(String s)
  {
    byte[] bytes = new byte[s.length() / 2];
    for (int i = 0; i < bytes.length; i++) {
      bytes[i] = (byte)Integer.parseInt(s.substring(2 * i, 2 * i + 2), 
        16);
    }
    return bytes;
  }

  public static String DESENCODE(Object ctx, String[] args)
    throws HiException
  {
    Cipher enc = null;

    byte[] result = null;
    byte[] newKey = new byte[24];
    byte[] iv = new byte[8];

    String key1 = keyInstance.getKey();
    byte[] key = toBytes(key1);

    System.arraycopy(key, 0, newKey, 0, 16);
    System.arraycopy(key, 0, newKey, 16, 8);
    try
    {
      enc = Cipher.getInstance("DESede/CBC/PKCS5Padding");

      SecretKeySpec keySpec = new SecretKeySpec(newKey, "DESede");
      IvParameterSpec ivSpec = new IvParameterSpec(iv);

      enc.init(1, keySpec, ivSpec);

      result = enc.doFinal(args[0].getBytes("UTF-8"));
    }
    catch (InvalidKeyException e) {
      e.printStackTrace();
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    catch (NoSuchPaddingException e) {
      e.printStackTrace();
    }
    catch (InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    }
    catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    }
    catch (BadPaddingException e) {
      e.printStackTrace();
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return toHexString(result);
  }

  public static String DESDECODE(Object ctx, String[] args) throws HiException {
    Cipher enc = null;

    byte[] result = null;
    byte[] newKey = new byte[24];
    byte[] iv = new byte[8];

    String key1 = keyInstance.getKey();
    byte[] key = toBytes(key1);

    System.arraycopy(key, 0, newKey, 0, 16);
    System.arraycopy(key, 0, newKey, 16, 8);
    try
    {
      enc = Cipher.getInstance("DESede/CBC/PKCS5Padding");

      SecretKeySpec keySpec = new SecretKeySpec(newKey, "DESede");
      IvParameterSpec ivSpec = new IvParameterSpec(iv);

      enc.init(2, keySpec, ivSpec);

      result = enc.doFinal(toBytes(args[0]));

      return new String(result, "UTF-8");
    }
    catch (InvalidKeyException e) {
      e.printStackTrace();
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    catch (NoSuchPaddingException e) {
      e.printStackTrace();
    }
    catch (InvalidAlgorithmParameterException e) {
      e.printStackTrace();
    }
    catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    }
    catch (BadPaddingException e) {
      e.printStackTrace();
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }
  public static void main(String[] args) throws HiException{
	  String[] date = {"968CFEEB6B377576ABF943C5D8D167D3F7081C30CB2C3E18"};
	  String[] date2 = {"00000000000000000"};
	  System.out.println(DESDECODE(keyInstance.getKey(),date));
	  System.out.println(DESENCODE(keyInstance.getKey(),date2));
  }
}