package de.hhn.labswps.wefactor.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.web.context.request.NativeWebRequest;

public class SimpleSignInAdapter implements SignInAdapter {

    private final RequestCache requestCache;

    private SocialUserDetailsService socialUserDetailsService;

    @Inject
    public SimpleSignInAdapter(
            SocialUserDetailsService socialUserDetailsService,
            RequestCache requestCache) {
        this.requestCache = requestCache;
        this.socialUserDetailsService = socialUserDetailsService;
    }

    @Override
    public String signIn(String localUserId, Connection<?> connection,
            NativeWebRequest request) {

        UserDetails user = socialUserDetailsService
                .loadUserByUserId(localUserId);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user
                        .getAuthorities()));

        return extractOriginalUrl(request);
    }

    private String extractOriginalUrl(NativeWebRequest request) {
        HttpServletRequest nativeReq = request
                .getNativeRequest(HttpServletRequest.class);
        HttpServletResponse nativeRes = request
                .getNativeResponse(HttpServletResponse.class);
        SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
        if (saved == null) {
            return null;
        }
        requestCache.removeRequest(nativeReq, nativeRes);
        removeAutheticationAttributes(nativeReq.getSession(false));
        return saved.getRedirectUrl();
    }

    private void removeAutheticationAttributes(HttpSession session) {
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}