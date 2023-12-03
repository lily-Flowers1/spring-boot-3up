package com.exam.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {
    @Value("${spring.security.jwt.key}")
    String key;
    @Value("${spring.security.jwt.expire}")
    int expire;
    @Resource
    StringRedisTemplate template;
    //invalidate 无效
    public boolean invalidateJwt(String headerToken){
        String token = convertToken(headerToken);
        if (token==null)return false;
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try{
            DecodedJWT jwt = verifier.verify(token);
            String id = jwt.getId();
            return this.deleteToken(id,jwt.getExpiresAt());
        }catch (JWTVerificationException e){
            return false;
        }
    }

    private boolean deleteToken(String uuid,Date time){
        if(this.isInvalidToken(uuid))return false;
        long expire = Math.max(time.getTime()-new Date().getTime(),0);
        template.opsForValue().set(Const.JWT_BLACK_LIST+uuid,"",expire, TimeUnit.MILLISECONDS);
        return true;
    }

    private boolean isInvalidToken(String uuid){
        return Boolean.TRUE.equals(template.hasKey(Const.JWT_BLACK_LIST + uuid));
    }

    public DecodedJWT resolveJwt(String headerToken){
        String token = this.convertToken(headerToken);
        if (token==null)return null;
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
            DecodedJWT verify = jwtVerifier.verify(token);
            if (this.isInvalidToken(verify.getId()))return null;
            Date expiresAt = verify.getExpiresAt();
            return new Date().after(expiresAt)?null:verify;
        }catch (JWTVerificationException e){
            return null;
        }
    }
    //createJWT
    public String createJwt(UserDetails details,int id,String username){
        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withClaim("id",id)
                .withClaim("name",username)
                .withClaim("authorities",details.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .withExpiresAt(this.expiresTime())
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(key));
    }
    public Date expiresTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,expire*24);
        return calendar.getTime();
    }
    public UserDetails toUser(DecodedJWT jwt){
        Map<String, Claim> claims = jwt.getClaims();
        return User
                .withUsername(claims.get("name").asString())
                .password("********")
                .authorities(claims.get("authorities").asArray(String.class))
                .build();
    }
    public Integer toId(DecodedJWT jwt){
        Map<String,Claim> claim = jwt.getClaims();
        return claim.get("id").asInt();
    }
    private String convertToken(String headerToken){
        if(headerToken==null || !headerToken.startsWith("Bearer "))return null;
        return headerToken.substring(7);
    }
}
