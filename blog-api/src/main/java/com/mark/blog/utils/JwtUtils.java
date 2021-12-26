package com.mark.blog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /**
     * token 过期时间, 单位: 秒. 这个值表示 30 天
     */
    private static final long TOKEN_EXPIRED_TIME = 30 * 60 * 1000;

    /**
     * jwt 加密解密密钥(可自行填写)
     */
    private static final String JWT_SECRET = "1225markedBlog$#%$@&";

    /**
     * 创建JWT
     */
    public static String createJWT(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        //下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() //这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setIssuedAt(new Date())           //iat: jwt的签发时间
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET) //设置签名使用的签名算法和签名使用的秘钥
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRED_TIME));

        return builder.compact();
    }


    /**
     * 验证jwt
     */
    public static Map<String, Object> verifyJwt(String token) {

        Map<String, Object> claims;
        try {
            claims = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(JWT_SECRET)         //设置签名的秘钥
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return claims;

    }

}