package com.main.api.security;

import com.base.common.model.User;
import com.base.common.util.Util;
import com.main.api.model.BlackListRedis;
import com.main.api.service.BlackListRedisService;
import com.main.api.service.impl.CustomUserDetailsService;
import com.main.api.service.impl.UserCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private BlackListRedisService blackListRedisService;

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Autowired
    private UserCacheService userCacheService;

    @Value("${base.project.jwt.key}")
    private String jwtKey;

    @Value("${base.project.ip.key}")
    private String ip;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                try {
//                    URI redisUri = new URI("redis://default:6oQMm6mxeh392IS1i14c5nCDKN4XSWgR@redis-19863.c270.us-east-1-3.ec2.cloud.redislabs.com:19863");
                    URI redisUri = new URI("redis://default:admindev@localhost:6379");
                    JedisPool pool = new JedisPool(new JedisPoolConfig(),
                            redisUri.getHost(),
                            redisUri.getPort(),
                            Protocol.DEFAULT_TIMEOUT,
                            redisUri.getUserInfo().split(":",2)[1]);
                    Jedis jedis = pool.getResource();
                    System.out.println(jedis);
                } catch (Exception e) {
                    // URI couldn't be parsed.
                }
                String tokenFind = blackListRedisService.findByToken(jwt);
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(tokenFind)) {

                    Long userId = tokenProvider.getUserIdFromToken(jwt);

                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put(jwtKey, jwt);
                    attributes.put(ip, getClientIp(request));
                    UserDetails userDetails;
                    User user = userCacheService.getInstance().get(userId);
                    if(Util.validate(user)){
                        userDetails = UserPrincipal.create(user, attributes);
                    } else {
                        userDetails = customUserDetailsService.loadUserById(userId, attributes);
                    }

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    private static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
}
