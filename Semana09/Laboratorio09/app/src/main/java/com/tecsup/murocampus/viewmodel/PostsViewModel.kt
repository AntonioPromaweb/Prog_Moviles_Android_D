package com.tecsup.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.murocampus.data.PostRepository
import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.Post
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetalleUi(val post: Post, val comentarios: List<Comentario>)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista: StateFlow<UiState<List<Post>>> = _lista.asStateFlow()

    // null significa "Todos"
    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro: StateFlow<Int?> = _usuarioFiltro.asStateFlow()

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle: StateFlow<UiState<DetalleUi>> = _detalle.asStateFlow()

    init { cargar() }

    fun cargar() {
        viewModelScope.launch {
            _lista.value = UiState.Cargando
            val userId = _usuarioFiltro.value
            val resultado = if (userId == null) repo.listar() else repo.porUsuario(userId)
            resultado
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    // Guarda el filtro y recarga la lista
    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    // Dos llamadas a la vez (post y comentarios). Si una falla, todo el detalle es Error.
    fun abrir(id: Int) {
        viewModelScope.launch {
            _detalle.value = UiState.Cargando
            coroutineScope {
                val postDef = async { repo.detalle(id) }
                val comentariosDef = async { repo.comentarios(id) }
                val rPost = postDef.await()
                val rComentarios = comentariosDef.await()

                val post = rPost.getOrNull()
                val comentarios = rComentarios.getOrNull()
                _detalle.value = if (post != null && comentarios != null) {
                    UiState.Exito(DetalleUi(post, comentarios))
                } else {
                    val causa = rPost.exceptionOrNull() ?: rComentarios.exceptionOrNull()
                    UiState.Error(mensajeDe(causa ?: IllegalStateException()))
                }
            }
        }
    }
}
