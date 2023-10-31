package com.alpaca.hyperpong.di

import androidx.paging.PagingConfig
import com.alpaca.hyperpong.data.repository.AuthRepositoryImpl
import com.alpaca.hyperpong.data.repository.EventosPagingSource
import com.alpaca.hyperpong.data.repository.FirestoreRepositoryImpl
import com.alpaca.hyperpong.domain.repository.AuthRepository
import com.alpaca.hyperpong.domain.repository.CloudFunctionsRepository
import com.alpaca.hyperpong.domain.repository.FirestoreRepository
import com.alpaca.hyperpong.domain.use_case.UseCases
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
import com.alpaca.hyperpong.domain.use_case.authentication.registrar_usuario_com_email_e_senha.SignUpUseCase
import com.alpaca.hyperpong.domain.use_case.cloud_functions.get_payment_url.CloudFunctionsUseCases
import com.alpaca.hyperpong.domain.use_case.cloud_functions.get_payment_url.get_payment_url.GetPaymentUrlUseCase
import com.alpaca.hyperpong.domain.use_case.firestore.FirestoreUseCases
import com.alpaca.hyperpong.domain.use_case.firestore.get_event.GetEventUseCase
import com.alpaca.hyperpong.domain.use_case.firestore.get_eventos.GetEventosUseCase
import com.alpaca.hyperpong.domain.use_case.firestore.get_user.GetUserUseCase
import com.alpaca.hyperpong.util.Constantes.PAGE_SIZE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun provideAuthRepository(db: FirestoreRepository, auth: FirebaseAuth): AuthRepository =
        AuthRepositoryImpl(
            auth = auth,
            db = db
        )

    @Singleton
    @Provides
    fun provideFirestoreRepository(
        auth: FirebaseAuth,
        source: EventosPagingSource,
        config: PagingConfig
    ): FirestoreRepository =
        FirestoreRepositoryImpl(
            auth = auth,
            db = FirebaseFirestore.getInstance(),
            source = source,
            config = config
        )

    @Singleton
    @Provides
    fun provideFirestoreRef() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideEventosPagingSource(database: FirebaseFirestore) =
        EventosPagingSource(db = database)

    @Singleton
    @Provides
    fun providePagingConfig() = PagingConfig(pageSize = PAGE_SIZE)


    @Provides
    @Singleton
    fun provideFirestoreUseCases(repository: FirestoreRepository): FirestoreUseCases =
        FirestoreUseCases(
            getEventosUseCase = GetEventosUseCase(repository = repository),
            getEventUseCase = GetEventUseCase(repository = repository),
            getUserUseCase = GetUserUseCase(repository = repository)
        )

    @Provides
    @Singleton
    fun provideCloudFunctionsUseCases(repository: CloudFunctionsRepository): CloudFunctionsUseCases =
        CloudFunctionsUseCases(getPaymentUrlUseCase = GetPaymentUrlUseCase(repository = repository))

    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases = AuthUseCases(
        deletarUsuarioUseCase = DeletarUsuarioUseCase(repository = repository),
        deslogarUsuarioUseCase = DeslogarUsuarioUseCase(repository = repository),
        enviarEmailDeVerificacaoUseCase = EnviarEmailDeVerificacaoUseCase(repository = repository),
        enviarEmailParaRedefinirSenhaUseCase = EnviarEmailParaRedefinirSenhaUseCase(repository = repository),
        getAuthStateUseCase = GetAuthStateUseCase(repository = repository),
        isEmailVerificadoUseCase = IsEmailVerificadoUseCase(repository = repository),
        isUsuarioAutenticadoUseCase = IsUsuarioAutenticadoUseCase(repository = repository),
        signInUseCase = LogarComEmailESenhaUseCase(repository = repository),
        logarUsuarioAnonimoUseCase = LogarUsuarioAnonimoUseCase(repository = repository),
        recarregarUsuarioUseCase = RecarregarUsuarioUseCase(repository = repository),
        signUp = SignUpUseCase(repository = repository)
    )

    @Provides
    @Singleton
    fun provideUseCases(
        authUseCases: AuthUseCases,
        cloudFunctionsUseCases: CloudFunctionsUseCases,
        firestoreUseCases: FirestoreUseCases
    ): UseCases = UseCases(
        authUseCases = authUseCases,
        cloudFunctionsUseCases = cloudFunctionsUseCases,
        firestoreUseCase = firestoreUseCases
    )

//    @Singleton
//    @Provides
//    fun provideRealtimeRepository(): RealtimeRepository =
//        RealtimeRepositoryImpl(database = Firebase.database.reference)

}