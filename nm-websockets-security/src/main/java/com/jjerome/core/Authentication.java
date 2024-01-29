package com.jjerome.core;

import java.security.Principal;
import java.util.List;

public interface Authentication extends Principal {

    List<? extends GrantedAuthority> getAuthorities();

    /**
     * The credentials that prove the principal is correct. This is usually a password,
     * but could be anything relevant to the <code>AuthenticationManager</code>. Callers
     * are expected to populate the credentials.
     * @return the credentials that prove the identity of the <code>Principal</code>
     */
    Object getCredentials();

    /**
     * Stores additional details about the authentication request. These might be an IP
     * address, certificate serial number etc.
     * @return additional details about the authentication request, or <code>null</code>
     * if not used
     */
    Object getDetails();

    /**
     * The identity of the principal being authenticated. In the case of an authentication
     * request with username and password, this would be the username. Callers are
     * expected to populate the principal for an authentication request.
     * <p>
     * The <tt>AuthenticationManager</tt> implementation will often return an
     * <tt>Authentication</tt> containing richer information as the principal for use by
     * the application. Many of the authentication providers will create a
     * {@code UserDetails} object as the principal.
     * @return the <code>Principal</code> being authenticated or the authenticated
     * principal after authentication.
     */
    Object getPrincipal();

    boolean isAuthenticated();

    void setAuthenticated(boolean isAuthenticated);
}
