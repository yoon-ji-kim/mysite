package com.douzone.mysite.config.auth;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class TokenProvider{
	private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
	private final Key key;
	 
    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    
	@Value("${jwt.token-validity-in-seconds}")
	private Long tokenValidiryInSeconds;
	
	//token 생성
	public String createToken(Authentication authentication) {
		//권한 가져오기
		String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
		
		Date now = new Date();
		Date expiration = new Date(now.getTime() + tokenValidiryInSeconds);
		return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
	}
	
	//token에 담긴 정보를 이용해 authentication 객체 리턴, 정보 꺼냄
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parserBuilder()
							.setSigningKey(key)
							.build()
							.parseClaimsJws(token)
							.getBody();
		Collection<? extends GrantedAuthority> authorities =
				Arrays.stream(claims.get("auth").toString().split(","))
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList());
		User principal = new User(claims.getSubject(),"", authorities);
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}
	
	/**token 유효성 검증 */
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            logger.info("잘못된 JWT 서명입니다.");
        }catch(ExpiredJwtException e){
        	logger.info("만료된 JWT 토큰입니다.");
        }catch(UnsupportedJwtException e){
        	logger.info("지원하지 않는 JWT 토큰입니다.");
        }catch(IllegalArgumentException e){
        	logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
