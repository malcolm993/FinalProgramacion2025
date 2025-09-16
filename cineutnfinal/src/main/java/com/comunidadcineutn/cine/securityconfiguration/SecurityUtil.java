
package com.comunidadcineutn.cine.securityconfiguration;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author santi
 */
public class SecurityUtil {
  public static String getSesionUsuario() {
    String aux = null;
    Authentication aut = SecurityContextHolder.getContext().getAuthentication();
    if (!(aut instanceof AnonymousAuthenticationToken)) {
      System.out.println("Aca ingreso usuario logueado " + aut.getName());
      aux = aut.getName();

    }

    return aux;
  }

}
