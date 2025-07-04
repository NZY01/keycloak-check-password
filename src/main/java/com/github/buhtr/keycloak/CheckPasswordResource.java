package com.github.buhtr.keycloak;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserProvider;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;

public class CheckPasswordResource {
    private final KeycloakSession session;
    private final AdminPermissionEvaluator auth;
    private final RealmModel realm;

    public CheckPasswordResource(KeycloakSession session, RealmModel realm, AdminPermissionEvaluator auth) {
        this.session = session;
        this.auth = auth;
        this.realm = realm;
    }

    @POST
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUserPassword(@PathParam("userId") String userId, CredentialModel credentialModel) {
        auth.users().requireManage();
        var userProvider = session.getProvider(UserProvider.class);
        var user = userProvider.getUserById(realm, userId);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var isPasswordValid = user.credentialManager().isValid(UserCredentialModel.password(credentialModel.getCredentialData()));

        return Response.ok(isPasswordValid).build();
    }
}
