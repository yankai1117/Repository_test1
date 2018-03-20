package com.xlpayment.test;

import com.xlpayment.encry.DESedeEncryptUtils;
import com.xlpayment.encry.DesSecretUtil;



public class Test
{
  public static void main(String[] args) throws Exception
  {
    String certNo = "73CAD7BB0C7476F95C2CE807D734D32AE0DA999AB6E01F18";//
    			   //FA8BE9FD279E3BB565007931256C5D4909A82B3D243E5FF9
    
    String aa = DESedeEncryptUtils.decrypt("D543CB3EB6ADA879A8D5260E79976183AD3B915DFEADFD16", "7DBACA37C4C65AD88C3D6E1ABC873E2E2423558807091D39");
    //String bb = DesSecretUtil.decryptMode(certNo, "D543CB3EB6ADA879A8D5260E79976183AD3B915DFEADFD16");
    System.out.println("aa------------>"+aa);
    //System.out.println("bb------------>"+bb);
  }
}