package com.bt.course.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtils {

    // 过期时间1天
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @param token    密钥
     * @param account  登录账号
     * @param password 密码
     * @return
     */
    public static boolean verify(String token, String account, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);

            JWTVerifier verifier = JWT.require(algorithm).withClaim(SecurityUtils.ACCOUNT, account).build();

            DecodedJWT jwt = verifier.verify(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     *
     * @param token
     * @param claim
     * @return
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(SecurityUtils.USERID).asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getUserAccount(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(SecurityUtils.ACCOUNT).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 使用用户密码生成签名
     *
     * @param account
     * @param password
     * @return
     */
    public static String sign(String account, Long userId, String password) {
        try {
            // 指定过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

            Algorithm algorithm = Algorithm.HMAC256(password);

            return JWT.create()
                    .withClaim(SecurityUtils.ACCOUNT, account)
                    .withClaim(SecurityUtils.USERID, userId)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

}