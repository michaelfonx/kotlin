package com.example.Almasoft2.service

import com.example.Almasoft2.model.Usuario
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class ConexionService(private val jdbcTemplate: JdbcTemplate) {

    fun obtenerUsuarios(): List<Usuario> {

        val sql = "SELECT * FROM usuarios"

        return jdbcTemplate.query(sql) { rs, _ ->
            Usuario(
                rs.getInt("usuario_id"),
                rs.getString("usuario_primer_nombre"),
                rs.getString("usuario_primer_apellido"),
                rs.getString("usuario_correo"),
                rs.getString("usuario_telefono"),
                rs.getLong("usuario_documento")
            )
        }
    }

    fun obtenerUsuarioPorId(id: Int): Usuario? {

        val sql = "SELECT * FROM usuarios WHERE usuario_id = ?"

        return jdbcTemplate.query(sql, arrayOf(id)) { rs, _ ->
            Usuario(
                rs.getInt("usuario_id"),
                rs.getString("usuario_primer_nombre"),
                rs.getString("usuario_primer_apellido"),
                rs.getString("usuario_correo"),
                rs.getString("usuario_telefono"),
                rs.getLong("usuario_documento")
            )
        }.firstOrNull()
    }

    fun guardarUsuario(usuario: Usuario): String {

        val sql = """
            INSERT INTO usuarios 
            (usuario_primer_nombre, usuario_primer_apellido, usuario_correo, usuario_telefono, usuario_documento)
            VALUES (?, ?, ?, ?, ?)
        """

        jdbcTemplate.update(
            sql,
            usuario.usuario_primer_nombre,
            usuario.usuario_primer_apellido,
            usuario.usuario_correo,
            usuario.usuario_telefono,
            usuario.usuario_documento
        )

        return "Usuario guardado correctamente"
    }

    fun eliminarUsuario(id: Int): String {

        val sql = "DELETE FROM usuarios WHERE usuario_id = ?"

        val filas = jdbcTemplate.update(sql, id)

        return if (filas > 0) {
            "Usuario eliminado correctamente"
        } else {
            "Usuario no encontrado"
        }
    }

    // 🔹 Login correcto en Service
    fun login(correo: String, documento: Long): Boolean {

        val sql = """
            SELECT COUNT(*) 
            FROM usuarios 
            WHERE usuario_correo = ? 
            AND usuario_documento = ?
        """

        val cantidad = jdbcTemplate.queryForObject(
            sql,
            Int::class.java,
            correo,
            documento
        )

        return cantidad != null && cantidad > 0
    }
}

