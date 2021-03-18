package org.matchmaker.common.util;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Liu Zhongshuai
 * @description 网络相关工具类
 * @date 2021-03-15 15:35
 **/
public class NetUtil {

    /**
     * 获取内网ip
     *
     * @return {@link byte[]}
     */
    public static byte[] getLocalIp() {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            byte[] internalIP = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        byte[] ipByte = ip.getAddress();
                        if (ipByte.length == 4) {
                            if (ipCheck(ipByte)) {
                                if (!isInternalIP(ipByte)) {
                                    return ipByte;
                                } else if (internalIP == null) {
                                    internalIP = ipByte;
                                }
                            }
                        }
                    } else if (ip != null && ip instanceof Inet6Address) {
                        byte[] ipByte = ip.getAddress();
                        if (ipByte.length == 16) {
                            if (ipV6Check(ipByte)) {
                                if (!isInternalV6IP(ip)) {
                                    return ipByte;
                                }
                            }
                        }
                    }
                }
            }
            if (internalIP != null) {
                return internalIP;
            } else {
                throw new RuntimeException("Can not get local ip");
            }
        } catch (Exception e) {
            throw new RuntimeException("Can not get local ip", e);
        }
    }

    private static boolean ipCheck(byte[] ip) {
        if (ip.length != 4) {
            throw new RuntimeException("illegal ipv4 bytes");
        }

        if (ip[0] >= (byte) 1 && ip[0] <= (byte) 126) {
            if (ip[1] == (byte) 1 && ip[2] == (byte) 1 && ip[3] == (byte) 1) {
                return false;
            }
            if (ip[1] == (byte) 0 && ip[2] == (byte) 0 && ip[3] == (byte) 0) {
                return false;
            }
            return true;
        } else if (ip[0] >= (byte) 128 && ip[0] <= (byte) 191) {
            if (ip[2] == (byte) 1 && ip[3] == (byte) 1) {
                return false;
            }
            if (ip[2] == (byte) 0 && ip[3] == (byte) 0) {
                return false;
            }
            return true;
        } else if (ip[0] >= (byte) 192 && ip[0] <= (byte) 223) {
            if (ip[3] == (byte) 1) {
                return false;
            }
            if (ip[3] == (byte) 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    private static boolean isInternalIP(byte[] ip) {
        if (ip.length != 4) {
            throw new RuntimeException("illegal ipv4 bytes");
        }

        //10.0.0.0~10.255.255.255
        //172.16.0.0~172.31.255.255
        //192.168.0.0~192.168.255.255
        if (ip[0] == (byte) 10) {

            return true;
        } else if (ip[0] == (byte) 172) {
            if (ip[1] >= (byte) 16 && ip[1] <= (byte) 31) {
                return true;
            }
        } else if (ip[0] == (byte) 192) {
            if (ip[1] == (byte) 168) {
                return true;
            }
        }
        return false;
    }

    private static boolean ipV6Check(byte[] ip) {
        if (ip.length != 16) {
            throw new RuntimeException("illegal ipv6 bytes");
        }

        InetAddressValidator validator = InetAddressValidator.getInstance();
        return validator.isValidInet6Address(ipToIPv6Str(ip));
    }

    public static String ipToIPv4Str(byte[] ip) {
        if (ip.length != 4) {
            return null;
        }
        return new StringBuilder().append(ip[0] & 0xFF).append(".").append(
                ip[1] & 0xFF).append(".").append(ip[2] & 0xFF)
                .append(".").append(ip[3] & 0xFF).toString();
    }

    public static String ipToIPv6Str(byte[] ip) {
        if (ip.length != 16) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ip.length; i++) {
            String hex = Integer.toHexString(ip[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
            if (i % 2 == 1 && i < ip.length - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }

    public static boolean isInternalV6IP(InetAddress inetAddr) {
        // Wild card ipv6
        if (inetAddr.isAnyLocalAddress()
                || inetAddr.isLinkLocalAddress() // Single broadcast ipv6 address: fe80:xx:xx...
                || inetAddr.isLoopbackAddress() //Loopback ipv6 address
                || inetAddr.isSiteLocalAddress()) { // Site local ipv6 address: fec0:xx:xx...
            return true;
        }
        return false;
    }

    public static class InetAddressValidator implements Serializable {
        private static final int IPV4_MAX_OCTET_VALUE = 255;
        private static final int MAX_UNSIGNED_SHORT = 65535;
        private static final int BASE_16 = 16;
        private static final long serialVersionUID = -919201640201914789L;
        private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
        private static final int IPV6_MAX_HEX_GROUPS = 8;
        private static final int IPV6_MAX_HEX_DIGITS_PER_GROUP = 4;
        private static final InetAddressValidator VALIDATOR = new InetAddressValidator();
        private final RegexValidator ipv4Validator = new RegexValidator("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");

        public InetAddressValidator() {
        }

        public static InetAddressValidator getInstance() {
            return VALIDATOR;
        }

        public boolean isValid(String inetAddress) {
            return this.isValidInet4Address(inetAddress) || this.isValidInet6Address(inetAddress);
        }

        public boolean isValidInet4Address(String inet4Address) {
            String[] groups = this.ipv4Validator.match(inet4Address);
            if (groups == null) {
                return false;
            } else {
                String[] arr$ = groups;
                int len$ = groups.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    String ipSegment = arr$[i$];
                    if (ipSegment == null || ipSegment.length() == 0) {
                        return false;
                    }

                    boolean var7 = false;

                    int iIpSegment;
                    try {
                        iIpSegment = Integer.parseInt(ipSegment);
                    } catch (NumberFormatException var9) {
                        return false;
                    }

                    if (iIpSegment > 255) {
                        return false;
                    }

                    if (ipSegment.length() > 1 && ipSegment.startsWith("0")) {
                        return false;
                    }
                }

                return true;
            }
        }

        public boolean isValidInet6Address(String inet6Address) {
            boolean containsCompressedZeroes = inet6Address.contains("::");
            if (containsCompressedZeroes && inet6Address.indexOf("::") != inet6Address.lastIndexOf("::")) {
                return false;
            } else if ((!inet6Address.startsWith(":") || inet6Address.startsWith("::")) && (!inet6Address.endsWith(":") || inet6Address.endsWith("::"))) {
                String[] octets = inet6Address.split(":");
                if (containsCompressedZeroes) {
                    List<String> octetList = new ArrayList(Arrays.asList(octets));
                    if (inet6Address.endsWith("::")) {
                        octetList.add("");
                    } else if (inet6Address.startsWith("::") && !octetList.isEmpty()) {
                        octetList.remove(0);
                    }

                    octets = (String[]) octetList.toArray(new String[octetList.size()]);
                }

                if (octets.length > 8) {
                    return false;
                } else {
                    int validOctets = 0;
                    int emptyOctets = 0;

                    for (int index = 0; index < octets.length; ++index) {
                        String octet = octets[index];
                        if (octet.length() == 0) {
                            ++emptyOctets;
                            if (emptyOctets > 1) {
                                return false;
                            }
                        } else {
                            emptyOctets = 0;
                            if (index == octets.length - 1 && octet.contains(".")) {
                                if (!this.isValidInet4Address(octet)) {
                                    return false;
                                }

                                validOctets += 2;
                                continue;
                            }

                            if (octet.length() > 4) {
                                return false;
                            }

                            boolean var8 = false;

                            int octetInt;
                            try {
                                octetInt = Integer.parseInt(octet, 16);
                            } catch (NumberFormatException var10) {
                                return false;
                            }

                            if (octetInt < 0 || octetInt > 65535) {
                                return false;
                            }
                        }
                        ++validOctets;
                    }
                    if (validOctets <= 8 && (validOctets >= 8 || containsCompressedZeroes)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
    }

    public static class RegexValidator implements Serializable {
        private static final long serialVersionUID = -8832409930574867162L;
        private final Pattern[] patterns;

        public RegexValidator(String regex) {
            this(regex, true);
        }

        public RegexValidator(String regex, boolean caseSensitive) {
            this(new String[]{regex}, caseSensitive);
        }

        public RegexValidator(String[] regexs) {
            this(regexs, true);
        }

        public RegexValidator(String[] regexs, boolean caseSensitive) {
            if (regexs != null && regexs.length != 0) {
                this.patterns = new Pattern[regexs.length];
                int flags = caseSensitive ? 0 : 2;

                for (int i = 0; i < regexs.length; ++i) {
                    if (regexs[i] == null || regexs[i].length() == 0) {
                        throw new IllegalArgumentException("Regular expression[" + i + "] is missing");
                    }

                    this.patterns[i] = Pattern.compile(regexs[i], flags);
                }

            } else {
                throw new IllegalArgumentException("Regular expressions are missing");
            }
        }

        public boolean isValid(String value) {
            if (value == null) {
                return false;
            } else {
                for (int i = 0; i < this.patterns.length; ++i) {
                    if (this.patterns[i].matcher(value).matches()) {
                        return true;
                    }
                }

                return false;
            }
        }

        public String[] match(String value) {
            if (value == null) {
                return null;
            } else {
                for (int i = 0; i < this.patterns.length; ++i) {
                    Matcher matcher = this.patterns[i].matcher(value);
                    if (matcher.matches()) {
                        int count = matcher.groupCount();
                        String[] groups = new String[count];

                        for (int j = 0; j < count; ++j) {
                            groups[j] = matcher.group(j + 1);
                        }

                        return groups;
                    }
                }

                return null;
            }
        }

        public String validate(String value) {
            if (value == null) {
                return null;
            } else {
                for (int i = 0; i < this.patterns.length; ++i) {
                    Matcher matcher = this.patterns[i].matcher(value);
                    if (matcher.matches()) {
                        int count = matcher.groupCount();
                        if (count == 1) {
                            return matcher.group(1);
                        }

                        StringBuilder buffer = new StringBuilder();

                        for (int j = 0; j < count; ++j) {
                            String component = matcher.group(j + 1);
                            if (component != null) {
                                buffer.append(component);
                            }
                        }

                        return buffer.toString();
                    }
                }

                return null;
            }
        }

        @Override
        public String toString() {
            StringBuilder buffer = new StringBuilder();
            buffer.append("RegexValidator{");

            for (int i = 0; i < this.patterns.length; ++i) {
                if (i > 0) {
                    buffer.append(",");
                }

                buffer.append(this.patterns[i].pattern());
            }

            buffer.append("}");
            return buffer.toString();
        }
    }

    /*public static void main(String[] args) {

        long startTime=System.currentTimeMillis();
        String ip=ipToIPv4Str(getLocalIp());
        long endTime=System.currentTimeMillis();

        System.out.println("获取本地内网ip耗时:"+(endTime-startTime)+";ip地址为:"+ip);
    }*/
}
