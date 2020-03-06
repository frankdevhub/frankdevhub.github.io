package com.frankdevhub.site.core.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class HostUtils {
    public static String getMyIp() {
        String localip = null;
        String netip = null;
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;
            while ((netInterfaces.hasMoreElements()) && (!finded)) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = (InetAddress) address.nextElement();
                    if ((!ip.isSiteLocalAddress()) && (!ip.isLoopbackAddress())
                            && (ip.getHostAddress().indexOf(":") == -1)) {
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    }
                    if ((ip.isSiteLocalAddress()) && (!ip.isLoopbackAddress())
                            && (ip.getHostAddress().indexOf(":") == -1)) {
                        localip = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if ((netip != null) && (!"".equals(netip))) {
            return netip;
        }
        return localip;
    }

}
