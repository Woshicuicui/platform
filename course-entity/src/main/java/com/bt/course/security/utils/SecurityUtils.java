package com.bt.course.security.utils;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import java.util.Random;

public class SecurityUtils {

    //request请求头属性
    public static final String REQUEST_AUTH_HEADER="Authorization";

    //JWT-account
    public static final String ACCOUNT = "account";

    //JWT-account
    public static final String USERID = "userId";

    private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private static String algorithmName = "SHA-1";

    private static final int hashIterations = 1;

    private static final String NAMES_DELIMETER = ",";

    public final static String hashAlgorithmName = "MD5";


    /**
     * 密码加密工具类
     *
     * @param credentials 密码
     * @param saltSource 密码盐
     * @return
     */
    public static String md5(String credentials, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
    }

    /**
     * 获取随机盐值
     * @param length
     * @return
     */
    public static String getRandomSalt(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取当前用户ID
     * @return
     */
    public static Long getCurrentUserId() {
        Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
        return JwtUtils.getUserId(currentUser.getPrincipal().toString());
    }

    /**
     * 获取当前用户账户
     * @return
     */
    public static String getCurrentUserAccount() {
        Subject currentUser = org.apache.shiro.SecurityUtils.getSubject();
        return JwtUtils.getUserAccount(currentUser.getPrincipal().toString());
    }

    public static Subject getSubject() {
        return org.apache.shiro.SecurityUtils.getSubject();
    }


    public static boolean hasRole(String roleName) {
        return getSubject() != null && roleName != null
                && roleName.length() > 0 && getSubject().hasRole(roleName);
    }


    public static boolean lacksRole(String roleName) {
        return !hasRole(roleName);
    }


    public static boolean hasAnyRoles(String roleNames) {
        boolean hasAnyRole = false;
        Subject subject = getSubject();
        if (subject != null && roleNames != null && roleNames.length() > 0) {
            for (String role : roleNames.split(NAMES_DELIMETER)) {
                if (subject.hasRole(role.trim())) {
                    hasAnyRole = true;
                    break;
                }
            }
        }
        return hasAnyRole;
    }


    public static boolean hasAllRoles(String roleNames) {
        boolean hasAllRole = true;
        Subject subject = getSubject();
        if (subject != null && roleNames != null && roleNames.length() > 0) {
            for (String role : roleNames.split(NAMES_DELIMETER)) {
                if (!subject.hasRole(role.trim())) {
                    hasAllRole = false;
                    break;
                }
            }
        }
        return hasAllRole;
    }


    public static boolean hasPermission(String permission) {
        return getSubject() != null && permission != null
                && permission.length() > 0
                && getSubject().isPermitted(permission);
    }

    public static boolean lacksPermission(String permission) {
        return !hasPermission(permission);
    }

    /**
     * 输出当前用户信息，通常为登录帐号信息。
     *
     * @return 当前用户信息
     */
    public static String principal() {
        if (getSubject() != null) {
            Object principal = getSubject().getPrincipal();
            return principal.toString();
        }
        return "";
    }
}