package com.azha.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {
    private static long expire = 604800;
    private static String secret="miyaozhibunengtaiduanburanhuibaocuobuzhidaoxianzaigoubugouchangnandaohaiyaozaichangyiidancaixingkanyangzideichangdaowubaiyishiecaixng";
    public static String generateToken(String name){//生成TOKEN
        Date now = new Date();
        Date expiration = new Date(now.getTime()+1000*expire);
        JwtBuilder jwtBuilder= Jwts.builder()
                .setHeaderParam("type","JWT")
                .setSubject(name)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512,secret);
        System.out.println(jwtBuilder.compact());
        return jwtBuilder.compact();
    }
    public static Claims getClaimsByToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}
