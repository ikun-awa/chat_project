package chat.duang.formtomysql.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    // 随机生成的 256 位密钥，生产环境请妥善保护
    private final SecretKey key = Keys.hmacShaKeyFor(
            "这是一个至少32字节长的超长随机密钥，用来签名JWT！".getBytes()
    );
    private final long validityMillis = 3600_000; // 1 小时

    // 生成 Token
    public String generateToken(String username) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityMillis))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // 从 Token 中提取用户名
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // 验证 Token 是否有效（签名和过期检查）
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
