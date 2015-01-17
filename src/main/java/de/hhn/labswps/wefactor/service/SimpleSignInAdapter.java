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

/**
 * The weFactor implementation of {@link SignInAdapter}.
 */
public class SimpleSignInAdapter implements SignInAdapter {

    /** The request cache. */
    private final RequestCache requestCache;

    /** The social user details service. */
    private SocialUserDetailsService socialUserDetailsService;

    /**
     * Instantiates a new simple sign in adapter.
     *
     * @param socialUserDetailsService
     *            the social user details service
     * @param requestCache
     *            the request cache
     */
    @Inject
    public SimpleSignInAdapter(
            SocialUserDetailsService socialUserDetailsService,
            RequestCache requestCache) {
        this.requestCache = requestCache;
        this.socialUserDetailsService = socialUserDetailsService;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.springframework.social.connect.web.SignInAdapter#signIn(java.lang
     * .String, org.springframework.social.connect.Connection,
     * org.springframework.web.context.request.NativeWebRequest)
     */
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

    /**
     * Extract original url.
     *
     * @param request
     *            the request
     * @return the string
     */
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

    /**
     * Removes the authetication attributes.
     *
     * @param session
     *            the session
     */
    private void removeAutheticationAttributes(HttpSession session) {
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}