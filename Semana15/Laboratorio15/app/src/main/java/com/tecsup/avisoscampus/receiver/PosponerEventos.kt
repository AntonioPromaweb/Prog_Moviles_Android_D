package com.tecsup.avisoscampus.receiver

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object PosponerEventos {
    private val _pedidos = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val pedidos: SharedFlow<Unit> = _pedidos.asSharedFlow()

    fun pedir() {
        _pedidos.tryEmit(Unit)
    }
}
