package de.hhn.labswps.wefactor.web.util;

import java.security.Principal;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import de.hhn.labswps.wefactor.domain.UserConnection;
import de.hhn.labswps.wefactor.domain.UserConnectionRepository;
import de.hhn.labswps.wefactor.domain.UserProfile;
import de.hhn.labswps.wefactor.domain.UserProfileRepository;

/**
 * The Class SocialControllerUtil.
 */
@Component
public class SocialControllerUtil {

    /** The Constant LOG. */
    private static final Logger LOG = LoggerFactory
            .getLogger(SocialControllerUtil.class);

    /** The Constant USER_CONNECTION. */
    private static final String USER_CONNECTION = "MY_USER_CONNECTION";

    /** The Constant USER_PROFILE. */
    private static final String USER_PROFILE = "MY_USER_PROFILE";

    /** The user profile repository. */
    @Autowired
    private UserProfileRepository userProfileRepository;

    /** The user connection repository. */
    @Autowired
    private UserConnectionRepository userConnectionRepository;

    /**
     * Sets the model.
     *
     * @param request
     *            the request
     * @param currentUser
     *            the current user
     * @param model
     *            the model
     */
    public void setModel(final HttpServletRequest request,
            final Principal currentUser, final Model model) {

        // SecurityContext ctx = (SecurityContext)
        // session.getAttribute("SPRING_SECURITY_CONTEXT");

        if ((currentUser == null) || model.containsAttribute("currentUserId")) {
            return;
        }

        final String userId = currentUser == null ? null : currentUser
                .getName();
        final String path = request.getRequestURI();
        final HttpSession session = request.getSession();

        UserConnection connection = null;
        UserProfile profile = null;
        String displayName = null;

        // Collect info if the user is logged in, i.e. userId is set
        if (userId != null) {

            // Get the current UserConnection from the http session
            connection = this.getUserConnection(session, userId);

            // Get the current UserProfile from the http session
            profile = this.getUserProfile(session, userId);

            // Compile the best display name from the connection and the profile
            displayName = this.getDisplayName(connection, profile);

        }

        final Throwable exception = (Throwable) session
                .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        // Update the model with the information we collected
        model.addAttribute("exception",
                exception == null ? null : exception.getMessage());
        model.addAttribute("currentUserId", userId);
        model.addAttribute("currentUserProfile", profile);

        if (connection != null) {
            String imageUrl = connection.getImageUrl();

            imageUrl = imageUrl.replace("sz=50", "sz=150");
            model.addAttribute("currentUserImageUrl", imageUrl);

        }

        model.addAttribute("currentUserDisplayName", displayName);

        if (LOG.isDebugEnabled()) {
            this.logInfo(request, model, userId, path, session);
        }
    }

    /**
     * Log info.
     *
     * @param request
     *            the request
     * @param model
     *            the model
     * @param userId
     *            the user id
     * @param path
     *            the path
     * @param session
     *            the session
     */
    protected void logInfo(final HttpServletRequest request, final Model model,
            final String userId, final String path, final HttpSession session) {
        // Log the content of the model
        LOG.debug("Path: " + path + ", currentUserId: " + userId);

        LOG.debug("Non-null request-attributes:");
        for (final Enumeration<String> rane = request.getAttributeNames(); rane
                .hasMoreElements();) {
            final String key = rane.nextElement();
            final Object value = session.getAttribute(key);
            if (value != null) {
                LOG.debug(" - " + key + " = " + value);
            }
        }

        LOG.debug("Session-attributes:");
        for (final Enumeration<String> sane = session.getAttributeNames(); sane
                .hasMoreElements();) {
            final String key = sane.nextElement();
            LOG.debug(" - " + key + " = " + session.getAttribute(key));
        }

        final Set<Map.Entry<String, Object>> me = model.asMap().entrySet();
        LOG.debug("ModelElements (" + me.size() + "):");
        for (final Map.Entry<String, Object> e : me) {
            LOG.debug(" - " + e.getKey() + " = " + e.getValue());
        }
    }

    /**
     * Gets the user profile.
     *
     * @param session
     *            the session
     * @param userId
     *            the user id
     * @return the user profile
     */
    protected UserProfile getUserProfile(final HttpSession session,
            final String userId) {
        UserProfile profile = (UserProfile) session.getAttribute(USER_PROFILE);

        // Reload from persistence storage if not set or invalid (i.e. no valid
        // userId)
        if ((profile == null) || !userId.equals(profile.getUserId())) {
            profile = this.userProfileRepository.findByUsername(userId);
            session.setAttribute(USER_PROFILE, profile);
        }
        return profile;
    }

    /**
     * Gets the user connection.
     *
     * @param session
     *            the session
     * @param userId
     *            the user id
     * @return the user connection
     */
    public UserConnection getUserConnection(final HttpSession session,
            final String userId) {
        UserConnection connection;
        connection = (UserConnection) session.getAttribute(USER_CONNECTION);

        // Reload from persistence storage if not set or invalid (i.e. no valid
        // userId)
        if ((connection == null) || !userId.equals(connection.getUserId())) {
            connection = this.userConnectionRepository
                    .findByProviderUserId(userId);
            session.setAttribute(USER_CONNECTION, connection);
        }
        return connection;
    }

    /**
     * Gets the display name.
     *
     * @param connection
     *            the connection
     * @param profile
     *            the profile
     * @return the display name
     */
    protected String getDisplayName(final UserConnection connection,
            final UserProfile profile) {

        return profile.getName();
    }
}
