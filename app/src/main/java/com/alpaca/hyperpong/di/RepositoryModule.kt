package com.alpaca.hyperpong.di

import androidx.paging.PagingConfig
import com.alpaca.hyperpong.data.repository.AuthRepositoryImpl
import com.alpaca.hyperpong.data.repository.EventosPagingSource
import com.alpaca.hyperpong.data.repository.RealtimeRepositoryImpl
import com.alpaca.hyperpong.domain.repository.AuthRepository
import com.alpaca.hyperpong.domain.repository.RealtimeRepository
import com.alpaca.hyperpong.domain.use_case.authentication.AuthUseCases
import com.alpaca.hyperpong.domain.use_case.authentication.deletar_usuario.DeletarUsuarioUseCase
import com.alpaca.hyperpong.domain.use_case.authentication.deslogar_usuario.DeslogarUsuarioUseCase
import com.alpaca.hyperpong.domain.use_case.authentication.enviar_email_de_verificacao.EnviarEmailDeVerificacaoUseCase
import com.alpaca.hyperpong.domain.use_case.authentication.enviar_email_para_redefinir_senha.EnviarEmailParaRedefinirSenhaUseCase
import com.alpaca.hyperpong.domain.use_case.authentication.get_auth_state.GetAuthStateUseCase
import com.alpaca.hyperpong.domain.use_case.authentication.is_email_verificado.IsEmailVerificadoUseCase
import com.alpaca.hyperpong.domain.use_case.authentication.is_usuario_autenticado.IsUsuarioAutenticadoUseCase
import com.alpaca.hyperpong.domain.use_case.authentication.logar_com_email_e_senha.LogarComEmailESenhaUseCase
import com.alpaca.hyperpong.domain.use_case.authentication.logar_usuario_anonimo.LogarUsuarioAnonimoUseCase
import com.alpaca.hyperpong.domain.use_case.authentication.recarregar_usuario.RecarregarUsuarioUseCase
import com.alpaca.hyperpong.domain.use_case.authentication.registrar_usuario_com_email_e_senha.RegistrarUsuarioComEmailESenhaUseCase
import com.alpaca.hyperpong.domain.use_case.realtime.RealtimeUseCases
import com.alpaca.hyperpong.domain.use_case.realtime.get_eventos.GetEventosUseCase
import com.alpaca.hyperpong.util.Constantes.TAMANHO_PAGINA
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(auth = Firebase.auth)

    @Singleton
    @Provides
    fun provideRealtimeRef() = Firebase.database.reference

    @Singleton
    @Provides
    fun provideEventosPagingSource(database: DatabaseReference) =
        EventosPagingSource(database = database)

    @Singleton
    @Provides
    fun providePagingConfig() = PagingConfig(pageSize = TAMANHO_PAGINA)

    @Singleton
    @Provides
    fun provideProductsRepository(
        source: EventosPagingSource,
        config: PagingConfig
    ): RealtimeRepository = RealtimeRepositoryImpl(
        source = source,
        config = config
    )

    @Provides
    @Singleton
    fun provideRealtimeUseCases(repository: RealtimeRepository): RealtimeUseCases =
        RealtimeUseCases(
            getEventosUseCase = GetEventosUseCase(repository = repository)
        )

    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases =
        AuthUseCases(
            deletarUsuarioUseCase = DeletarUsuarioUseCase(repository = repository),
            deslogarUsuarioUseCase = DeslogarUsuarioUseCase(repository = repository),
            enviarEmailDeVerificacaoUseCase = EnviarEmailDeVerificacaoUseCase(repository = repository),
            enviarEmailParaRedefinirSenhaUseCase = EnviarEmailParaRedefinirSenhaUseCase(repository = repository),
            getAuthStateUseCase = GetAuthStateUseCase(repository = repository),
            isEmailVerificadoUseCase = IsEmailVerificadoUseCase(repository = repository),
            isUsuarioAutenticadoUseCase = IsUsuarioAutenticadoUseCase(repository = repository),
            logarComEmailESenhaUseCase = LogarComEmailESenhaUseCase(repository = repository),
            logarUsuarioAnonimoUseCase = LogarUsuarioAnonimoUseCase(repository = repository),
            recarregarUsuarioUseCase = RecarregarUsuarioUseCase(repository = repository),
            registrarUsuarioComEmailESenhaUseCase = RegistrarUsuarioComEmailESenhaUseCase(repository = repository)
        )

//    @Singleton
//    @Provides
//    fun provideRealtimeRepository(): RealtimeRepository =
//        RealtimeRepositoryImpl(database = Firebase.database.reference)

}