package com.alpaca.hyperpong.domain.use_case.authentication

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

data class AuthUseCases(
    val deletarUsuarioUseCase: DeletarUsuarioUseCase,
    val deslogarUsuarioUseCase: DeslogarUsuarioUseCase,
    val enviarEmailDeVerificacaoUseCase: EnviarEmailDeVerificacaoUseCase,
    val enviarEmailParaRedefinirSenhaUseCase: EnviarEmailParaRedefinirSenhaUseCase,
    val getAuthStateUseCase: GetAuthStateUseCase,
    val isEmailVerificadoUseCase: IsEmailVerificadoUseCase,
    val isUsuarioAutenticadoUseCase: IsUsuarioAutenticadoUseCase,
    val signInUseCase: LogarComEmailESenhaUseCase,
    val logarUsuarioAnonimoUseCase: LogarUsuarioAnonimoUseCase,
    val recarregarUsuarioUseCase: RecarregarUsuarioUseCase,
    val signUp: SignUpUseCase
)
