package ru.tsu.hits.internship.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Filter for IP-based authentication.
 * This filter authenticates requests from trusted IP addresses.
 */
@Component
public class IpAuthenticationFilter extends GenericFilterBean {

    private final Set<String> trustedIps;

    /**
     * Creates a new IpAuthenticationFilter with the specified trusted IPs.
     *
     * @param trustedIps comma-separated list of trusted IP addresses
     */
    public IpAuthenticationFilter(@Value("${security.trusted-ips:127.0.0.1}") String trustedIps) {
        this.trustedIps = new HashSet<>(Arrays.asList(trustedIps.split(",")));
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String remoteIp = httpRequest.getRemoteAddr();
        String serviceName = httpRequest.getHeader("Service-Name");

        if (trustedIps.contains(remoteIp) && serviceName != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken
                    (serviceName, null, List.of(() -> "ROLE_TRUSTED_SERVICE"));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(req, res);
    }
}