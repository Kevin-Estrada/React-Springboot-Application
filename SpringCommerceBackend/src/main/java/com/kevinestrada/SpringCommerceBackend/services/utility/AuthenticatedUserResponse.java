package com.kevinestrada.SpringCommerceBackend.services.utility;

import java.util.List;
import java.util.Objects;

import com.kevinestrada.SpringCommerceBackend.security.services.UserDetailsImpl;

public class AuthenticatedUserResponse {
    private String jwt;
    private UserDetailsImpl userDetails;
    private List<String> roles;

    public AuthenticatedUserResponse() {
    }

    public AuthenticatedUserResponse(String jwt, UserDetailsImpl userDetails, List<String> roles) {
        this.jwt = jwt;
        this.userDetails = userDetails;
        this.roles = roles;
    }

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserDetailsImpl getUserDetails() {
        return this.userDetails;
    }

    public void setUserDetails(UserDetailsImpl userDetails) {
        this.userDetails = userDetails;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public AuthenticatedUserResponse jwt(String jwt) {
        setJwt(jwt);
        return this;
    }

    public AuthenticatedUserResponse userDetails(UserDetailsImpl userDetails) {
        setUserDetails(userDetails);
        return this;
    }

    public AuthenticatedUserResponse roles(List<String> roles) {
        setRoles(roles);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AuthenticatedUserResponse)) {
            return false;
        }
        AuthenticatedUserResponse authenticatedUserResponse = (AuthenticatedUserResponse) o;
        return Objects.equals(jwt, authenticatedUserResponse.jwt)
                && Objects.equals(userDetails, authenticatedUserResponse.userDetails)
                && Objects.equals(roles, authenticatedUserResponse.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwt, userDetails, roles);
    }

    @Override
    public String toString() {
        return "{" +
                " jwt='" + getJwt() + "'" +
                ", userDetails='" + getUserDetails() + "'" +
                ", roles='" + getRoles() + "'" +
                "}";
    }
}
