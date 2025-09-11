package com.comunidadcineutn.cine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.comunidadcineutn.cine.dto.PasswordUser;
import com.comunidadcineutn.cine.dto.UsuarioEdicionDTO;

import com.comunidadcineutn.cine.model.Rol;
import com.comunidadcineutn.cine.model.Usuario;

import com.comunidadcineutn.cine.service.InterfaceServiceRol;
import com.comunidadcineutn.cine.service.InterfaceServiceUsuario;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/cineutn/usuario")
@Controller
@Tag(name = "Usuarios", description = "API para gestion de usuarios")

public class UsuarioController {

  private static final int ID_ROL_CLIENTE = 1;

  @Autowired
  private InterfaceServiceRol rolService;
  @Autowired
  private InterfaceServiceUsuario usuarioService;

  @GetMapping("/signup")
  @Operation(summary = "Obtener formulario de alta para usuario cliente")
  public String obtenerFormularioAltaUsuario(Model m) {
    Usuario u = new Usuario();
    Rol r = rolService.findRolPorId(ID_ROL_CLIENTE);
    u.setRolUsuario(r);
    m.addAttribute("usuario", u);
    return "usuarios/sign-up";
  }

  @PostMapping("/signup")
  @Operation(summary = "Alta de usuario cliente ingresado")
  public String altaUsuarioCliente(
      @Valid @ModelAttribute("usuario") Usuario u,
      BindingResult br,
      Model m,
      RedirectAttributes ra) {
    String casoError = "usuarios/sign-up";
    String casoExito = "redirect:/cineutn/usuario/perfil";
    String destino = casoExito;

    if (br.hasErrors()) {
      System.out.println("error en validacion de atributos");
      System.out.println(br);
      m.addAttribute("usuario", u);
      destino = casoError;
    } else {
      try {
        usuarioService.addUsuario(u);
      } catch (Exception e) {
        m.addAttribute("error", e.getMessage());
        m.addAttribute("usuario", u);
        destino = casoError;
      }

    }
    ra.addAttribute("idUsuario", u.getId());

    return destino;
  }

  @GetMapping("/perfil")
  @Operation(summary = "Revisar el perfil")
  public String revisarPerfil(@AuthenticationPrincipal Usuario user, Model model, RedirectAttributes ra) {
    System.out.println(" datos de usuario cargardo desde authentication" + user.toString());
    String destino = "redirect:/cineutn/usuario/login";
    try {
      UsuarioEdicionDTO userDTO = usuarioService.getUsuarioEdicionDTOById(user.getId());
      model.addAttribute("usuario", userDTO);
      destino = "usuarios/revisar-usuario";
    } catch (Exception e) {
      ra.addAttribute("error", e.getMessage());
    }
    return destino;
  }

  @GetMapping("/edicion")
  @Operation(summary = "formulario para la edicion de perfil usuario")
  public String edicionPerfil(@AuthenticationPrincipal Usuario user, Model mod, RedirectAttributes ra) {
    String destino = "redirect:/cineutn/usuarios/sign-up";
    try {
      UsuarioEdicionDTO u = usuarioService.getUsuarioEdicionDTOById(user.getId());
      System.out.println("usuario DTo creado : " + u.toString());
      mod.addAttribute("usuario", u);
      mod.addAttribute("modoEdicion", true);
      // mod.addAttribute("edicionPassword", new PasswordUser(user.getId()));
      destino = "usuarios/sign-up";
    } catch (Exception e) {
      ra.addAttribute("error", e.getMessage());

    }
    return destino;
  }

  @PutMapping("/edicion")
  @Operation(summary = "realizo la edicion de usuario")
  public String edicionPerfil(@Valid @ModelAttribute("usuario") UsuarioEdicionDTO u,
      BindingResult br,
      Model m,
      RedirectAttributes ra) {
    String casoError = "usuarios/sign-up";
    String casoExito = "redirect:/cineutn/usuario/perfil";

    if (br.hasErrors()) {
      System.out.println("error en validacion de atributos");
      System.out.println(br);
      m.addAttribute("usuario", u);
      m.addAttribute("modoEdicion", true);
      // m.addAttribute("edicionPassword", new PasswordUser(u.getId()));
      return casoError;
    } else {
      try {
        System.out.println("punto 1:" + u.toString());
        usuarioService.editUsuario(u);
      } catch (Exception e) {
        m.addAttribute("error", e.getMessage());
        m.addAttribute("usuario", u);
        m.addAttribute("modoEdicion", true);
        // m.addAttribute("edicionPassword", new PasswordUser(u.getId()));
        return casoError;
      }

    }
    ra.addAttribute("idUsuario", u.getId());

    return casoExito;
  }

  @GetMapping("/eliminar/{id}")
  @Operation(summary = "obtengo ventana de aviso para la eliminacion de usuario")
  public String elimininarUsuario(Model m, @PathVariable(name = "id") Integer id, RedirectAttributes ra) {

    try {
      m.addAttribute("usuario", usuarioService.findUsuarioPorId(id));
      return "usuarios/eliminar-usuario";
    } catch (Exception e) {
      ra.addAttribute("error", e.getMessage());
      return "redirect:/cineutn/usuario/signup";
    }
  }

  @DeleteMapping("/eliminar")
  @Operation(summary = "Eliminar película por ID")
  public String eliminarPelicula(@RequestParam(required = true, name = "id") Integer id,
      RedirectAttributes ra) {
    Usuario u = usuarioService.findUsuarioPorId(id);
    usuarioService.deleteUserPorId(id);
    ra.addFlashAttribute("mensaje", "Usuario " + u.getId() + " eliminada con éxito!");

    return "redirect:/cineutn/usuario/signup";
  }

  @GetMapping("/login")
  @Operation(summary = "Formulario de ingreso")
  public String ingresoUsuario() {
    return "usuarios/login";
  }

  @GetMapping("/cambiarPassword")
  @Operation(summary = "obtengo formulario para cambio de contraseña")
  public String cambioPasswordUsuario(@AuthenticationPrincipal Usuario user, Model mod, RedirectAttributes ra) {
    String destino = "redirect:/cineutn/usuarios/perfil";
    try {
      PasswordUser nuevaPassword = new PasswordUser(user.getId());
      System.out.println(" DTo creado para cambio password: " + nuevaPassword.toString());
      mod.addAttribute("passwordUser", nuevaPassword);
      destino = "usuarios/cambio-password";
    } catch (Exception e) {
      ra.addAttribute("error", e.getMessage());
    }
    return destino;
  }

  @PostMapping("/cambiarPassword")
  @Operation(summary = "POST - envio el formulario con la password nueva")
  public String procesarCambioPassword(
      @Valid @ModelAttribute PasswordUser passwordUser,
      BindingResult bindingResult,
      @AuthenticationPrincipal Usuario usuarioActual,
      RedirectAttributes ra) {

    //String destino = "redirect:/cineutn/usuario/cambiarPassword";
    System.out.println("passworduser string : " + passwordUser.toString());

    if (!passwordUser.getId().equals(usuarioActual.getId())) {
      ra.addFlashAttribute("error", "No autorizado");
      return "redirect:/cineutn/usuario/perfil";
    }

    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors()
          .forEach(error -> System.out.println("Error de validación: " + error.getDefaultMessage()));

      return "usuarios/cambio-password"; // ← Mantiene el formulario con errores
    }

    try {
      usuarioService.changePasswordUser(passwordUser);
      ra.addFlashAttribute("exito", "Contraseña cambiada exitosamente");
      return "redirect:/cineutn/usuario/perfil";

    } catch (Exception e) {
      // Log real para developers
      System.err.println("Error al cambiar password: " + e.getMessage());
      ra.addFlashAttribute("error", "Error al cambiar la contraseña");
      return "redirect:/cineutn/usuario/cambiarPassword";
    }
  }
}
