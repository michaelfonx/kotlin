package com.example.Almasoft2.controller

import com.example.Almasoft2.model.Aviso
import com.example.Almasoft2.service.AvisoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/avisos")
class AvisoController(private val avisoService: AvisoService) {

    @GetMapping
    fun obtener(): List<Aviso> {
        return avisoService.obtenerAvisos()
    }

    @PostMapping
    fun crear(@RequestBody aviso: Aviso): String {
        return avisoService.crearAviso(aviso)
    }
    @PutMapping("/{id}/atender")
    fun marcarAtendido(@PathVariable id: Int): String {
        return avisoService.marcarAtendido(id)
    }

}
