package edu.scnu.wiki.utils;

/**
 * @author long
 * @version 1.0
 * @ClassName RequestContext
 * @description: TODO
 * @date 2023/10/11 9:57
 */
public class RequestContext {
    private static ThreadLocal<String> remoteAddr = new ThreadLocal<>();

    public static String getRemoteAddr() {
        return remoteAddr.get();
    }

    public static void setRemoteAddr(String remoteAddr) {
        RequestContext.remoteAddr.set(remoteAddr);
    }
}
