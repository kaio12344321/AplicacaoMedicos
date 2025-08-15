package med.voll.api.controller;

import med.voll.api.endereco.DadosEndereco;
import med.voll.api.medico.Especialidade;

public record DadosListagemMedico(
    Long id,
    String nome,
    String email,
    String crm,
    Especialidade especialidade) {}

