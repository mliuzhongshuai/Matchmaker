package org.matchmaker.common.payload;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Liu Zhongshuai
 * @description
 * @date 2021-03-19 14:49
 **/
public class PayloadFactory {

    private PayloadFactory() {
    }


    private final static Map<String, Class> payloadClassFactory = new HashMap<>(4);

    static {
        payloadClassFactory.put("BROKER", BrokerSvcInfo.class);
        payloadClassFactory.put("CONSOLE", ConsoleSvcInfo.class);
        payloadClassFactory.put("NORTH_GATEWAY", NorthGateWaySvcInfo.class);
        payloadClassFactory.put("SOUTH_GATEWAY", SouthGateWaySvcInfo.class);
    }

    public static Class getClass(String svcRoleName) {
        return payloadClassFactory.get(svcRoleName);
    }
}
