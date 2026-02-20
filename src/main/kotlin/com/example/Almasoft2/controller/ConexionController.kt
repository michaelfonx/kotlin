package com.example.Almasoft2.controller

import com.example.Almasoft2.model.Usuario
import com.example.Almasoft2.service.ConexionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class ConexionController(private val conexionService: ConexionService) {

    // 🔹 GET → Todos los usuarios
    @GetMapping("/usuarios")
    fun obtenerUsuarios(): List<Usuario> {
        return conexionService.obtenerUsuarios()
    }

    // 🔹 GET → Usuario por ID
    @GetMapping("/usuarios/{id}")
    fun obtenerUsuarioPorId(@PathVariable id: Int): Usuario? {
        return conexionService.obtenerUsuarioPorId(id)
    }

    // 🔹 POST → Crear usuario
    @PostMapping("/usuarios")
    fun guardarUsuario(@RequestBody usuario: Usuario): String {
        return conexionService.guardarUsuario(usuario)
    }

    // 🔹 DELETE → Eliminar usuario
    @DeleteMapping("/usuarios/{id}")
    fun eliminarUsuario(@PathVariable id: Int): String {
        return conexionService.eliminarUsuario(id)
    }

    // 🔹 POST → Login
    @PostMapping("/login")
    fun login(@RequestBody usuario: Usuario): Map<String, Any> {

        val esValido = conexionService.login(
            usuario.usuario_correo!!,
            usuario.usuario_documento!!
        )

        return if (esValido) {
            mapOf("success" to true, "message" to "Login exitoso")
        } else {
            mapOf("success" to false, "message" to "Credenciales incorrectas")
        }
    }
}

