package com.xlpayment;

import com.hisun.atc.common.HiArgUtils;
import com.hisun.exception.HiException;
import com.hisun.hilib.HiATLParam;
import com.hisun.hilog4j.HiLog;
import com.hisun.hilog4j.Logger;
import com.hisun.message.HiETF;
import com.hisun.message.HiMessage;
import com.hisun.message.HiMessageContext;
import com.xlpayment.encry.DESedeEncrypt;

public class DecryptDes
{
  private static Logger logger = HiLog.getLogger("DK_DECRYPT.trc");

  public int execute(HiATLParam args, HiMessageContext ctx)
    throws HiException
  {
    HiMessage msg = ctx.getCurrentMsg();
    HiETF etf = msg.getETFBody();
    logger.info("开始身份证解密");
    String certNo = HiArgUtils.getStringNotNull(args, "CERT_NO");
    String certKey = HiArgUtils.getStringNotNull(args, "CERT_KEY");
    String certNoRaw = null;
    try {
      certNoRaw = DESedeEncrypt.decrypt(certKey, certNo);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    logger.info("身份证解密完毕");
    etf.setChildValue("CERT_NO_RAW", certNoRaw);
    return 0;
  }
}