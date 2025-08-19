package med.voll.api.domain.medico;

public record DadosListagemMedico(
    Long id,
    String nome,
    String email,
    String crm,
    Especialidade especialidade) {}

