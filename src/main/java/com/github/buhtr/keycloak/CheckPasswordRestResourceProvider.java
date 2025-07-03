package com.github.buhtr.keycloak;

import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.Config;
import org.keycloak.models.*;
import org.keycloak.services.resources.admin.AdminEventBuilder;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProvider;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProviderFactory;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;

/**
 * @author Igor Slusarenko
 */
@Slf4j
@RequiredArgsConstructor
@Provider
public class CheckPasswordRestResourceProvider implements AdminRealmResourceProvider, AdminRealmResourceProviderFactory {

  private static final String PROVIDER_ID = "check-password";

  @Override
  public AdminRealmResourceProvider create(KeycloakSession session) {
    return this;
  }

  @Override
  public void init(Config.Scope config) {
  }

  @Override
  public void postInit(KeycloakSessionFactory factory) {
  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }

  @Override
  public Object getResource(KeycloakSession session, RealmModel realm, AdminPermissionEvaluator auth, AdminEventBuilder adminEvent) {
    return new CheckPasswordResource(session, realm, auth);
  }

  @Override
  public void close() {
  }
}
