package com.tecsup.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecsup.murocampus.data.PostRepository
import com.tecsup.murocampus.model.Comentario
import com.tecsup.murocampus.model.NuevoPost
import com.tecsup.murocampus.model.Post
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class DetalleUi(val post: Post, val comentarios: List<Comentario>)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    private val _listaOriginal = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)

    // null significa "Todos"
    private val _usuarioFiltro = MutableStateFlow<Int?>(null)
    val usuarioFiltro: StateFlow<Int?> = _usuarioFiltro.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    // Combina la lista de la red con el filtro de búsqueda por título
    val lista: StateFlow<UiState<List<Post>>> = combine(_listaOriginal, _busqueda) { estado, query ->
        when (estado) {
            is UiState.Exito -> {
                if (query.isBlank()) {
                    estado
                } else {
                    UiState.Exito(estado.datos.filter { it.title.contains(query, ignoreCase = true) })
                }
            }
            else -> estado
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Cargando
    )

    // null = aun no se envio nada
    private val _creado = MutableStateFlow<UiState<Post>?>(null)
    val creado: StateFlow<UiState<Post>?> = _creado.asStateFlow()

    // null = aun no se pidio borrar
    private val _borrado = MutableStateFlow<UiState<Unit>?>(null)
    val borrado: StateFlow<UiState<Unit>?> = _borrado.asStateFlow()

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle: StateFlow<UiState<DetalleUi>> = _detalle.asStateFlow()

    init { cargar() }

    fun cargar() {
        viewModelScope.launch {
            _listaOriginal.value = UiState.Cargando
            val userId = _usuarioFiltro.value
            val resultado = if (userId == null) repo.listar() else repo.porUsuario(userId)
            resultado
                .onSuccess { _listaOriginal.value = UiState.Exito(it) }
                .onFailure { _listaOriginal.value = UiState.Error(mensajeDe(it)) }
        }
    }

    // Guarda el filtro y recarga la lista
    fun filtrar(userId: Int?) {
        _usuarioFiltro.value = userId
        cargar()
    }

    fun buscar(texto: String) {
        _busqueda.value = texto
    }

    fun publicar(titulo: String, texto: String) {
        viewModelScope.launch {
            _creado.value = UiState.Cargando
            repo.crear(NuevoPost(userId = 1, title = titulo, body = texto))
                .onSuccess { _creado.value = UiState.Exito(it) }
                .onFailure { _creado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun limpiarCreacion() {
        _creado.value = null
    }

    fun borrar(id: Int) {
        viewModelScope.launch {
            _borrado.value = UiState.Cargando
            repo.borrar(id)
                .onSuccess { _borrado.value = UiState.Exito(Unit) }
                .onFailure { _borrado.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun limpiarBorrado() {
        _borrado.value = null
    }

    // Dos llamadas a la vez (post y comentarios). Si una falla, todo el detalle es Error.
    fun abrir(id: Int) {
        _borrado.value = null
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
