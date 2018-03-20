package com.xlpayment.encry;
class DESKEY
{
  private static DESKEY instance = null;
  private static String key = null;

  private DESKEY() {
    key = "00000000000000000000000000000000";
  }

  protected static DESKEY getInstance() {
    if (instance == null) {
      synchronized (DESKEY.class) {
        if (instance == null) {
          instance = new DESKEY();
        }
      }
    }

    return instance;
  }

  protected String getKey() {
    return key;
  }
}