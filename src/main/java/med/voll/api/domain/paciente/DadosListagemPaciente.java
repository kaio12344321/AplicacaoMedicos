package med.voll.api.domain.paciente;

public record DadosListagemPaciente(
        Long id,
        String nome,
        String email,
        String cpf) {}
