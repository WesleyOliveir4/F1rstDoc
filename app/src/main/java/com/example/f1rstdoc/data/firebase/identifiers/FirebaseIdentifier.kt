package com.example.f1rstdoc.data.firebase.identifiers

enum class FirebaseAuthIdentifier(val text: String) {
    ERROR_GENERIC_CREATE_USER("Erro ao efetuar o cadastro"),
    ERROR_GENERIC_SING_IN("email ou senha invalidos"),
    ERROR_NETWORK_CONNECTION("Sem internet, conecte-se e tente novamente"),
    ERROR_EMAIL_INVALID("E-mail inválido"),
    ERROR_EMAIL_USED("Já existe um cadastro com este e-mail"),
    ERROR_MIN_SIX_CHAR("A senha deve conter no minimo 6 caracteres"),
}

enum class RealtimeIdentifier(val text: String) {
    DOCS("Docs"),
}