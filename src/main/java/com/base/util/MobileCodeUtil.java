package com.base.util;

import com.base.webService.mobileCodeWS.MobileCodeWS;
import com.base.webService.mobileCodeWS.MobileCodeWSSoap;

/**
 * @Description:
 * 通过webService调用号码归属地查询功能
 */
public class MobileCodeUtil {
    /**
     * 查询归属地
     * @param telPhoneNumber
     * @return
     */
    public static String getTelPhoneNumberInfo(String telPhoneNumber){
        MobileCodeWS mobileCodeWS=new MobileCodeWS();
        MobileCodeWSSoap mobileCodeWSSoap=mobileCodeWS.getMobileCodeWSSoap();
        return mobileCodeWSSoap.getMobileCodeInfo(telPhoneNumber,"");
    }
}
