package com.github.buhtr.keycloak;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserProvider;
import org.keycloak.services.resources.admin.AdminEventBuilder;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;

/**
 * @author dush
 *
 */

public class CheckPasswordResource {
    private final KeycloakSession session;

    private final AdminPermissionEvaluator auth;

    private final RealmModel realm;

    public CheckPasswordResource(KeycloakSession session, RealmModel realm, AdminPermissionEvaluator auth, AdminEventBuilder adminEvent) {
        this.session = session;
        this.auth = auth;
        this.realm = realm;
    }

    @GET
    @Path("users/{userId}/check-password")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateUserPassword(@PathParam("userId") String userId, @RequestBody CredentialModel credentialModel) {
        AdminRealmUtils.validateQueryAccess(session, auth, userId);
        var userProvider = session.getProvider(UserProvider.class);
        var user = userProvider.getUserById(realm, userId);

        var isPasswordValid = user.credentialManager().isValid(UserCredentialModel.password(credentialModel.getCredentialData()));

        if (isPasswordValid) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
