package com.example.Almasoft2.service

import com.example.Almasoft2.model.Aviso
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class AvisoService(private val jdbcTemplate: JdbcTemplate) {

    fun obtenerAvisos(): List<Aviso> {

        val sql = "SELECT * FROM avisos"

        return jdbcTemplate.query(sql) { rs, _ ->
            Aviso(
                rs.getInt("aviso_id"),
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("categoria"),
                rs.getString("estado")
            )
        }
    }

    fun crearAviso(aviso: Aviso): String {

        val sql = """
            INSERT INTO avisos (titulo, descripcion, categoria, estado)
            VALUES (?, ?, ?, ?)
        """

        jdbcTemplate.update(
            sql,
            aviso.titulo,
            aviso.descripcion,
            aviso.categoria,
            aviso.estado
        )

        println(" Notificación: Nuevo aviso publicado en la categoría ${aviso.categoria}")

        return "Aviso creado correctamente"
    }

    fun marcarAtendido(id: Int): String {

        val sql = "UPDATE avisos SET estado = 'Atendido' WHERE aviso_id = ?"

        val filas = jdbcTemplate.update(sql, id)

        if (filas > 0) {
            println(" Notificación: El aviso con ID $id fue marcado como atendido")
            return "Aviso marcado como atendido"
        } else {
            return "Aviso no encontrado"
        }
    }
}