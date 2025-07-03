package com.github.buhtr.keycloak;

import com.google.auto.service.AutoService;
import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProvider;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProviderFactory;

/**
 * @author Igor Slusarenko
 */
@AutoService(AdminRealmResourceProviderFactory.class)
public class CheckPasswordRestResourceProviderFactory implements AdminRealmResourceProviderFactory {

  /**
   * The rest endpoint can be accessed by the following path
   * /realms/<realm-name>/check-password/login?password=<value>
   */
  private static final String PROVIDER_ID = "check-password";

  @Override
  public AdminRealmResourceProvider create(KeycloakSession session) {
    return new CheckPasswordRestResourceProvider(session);
  }

  @Override
  public void init(Scope config) {
  }

  @Override
  public void postInit(KeycloakSessionFactory factory) {
  }

  @Override
  public void close() {
  }

  @Override
  public String getId() {
    return PROVIDER_ID;
  }
}
