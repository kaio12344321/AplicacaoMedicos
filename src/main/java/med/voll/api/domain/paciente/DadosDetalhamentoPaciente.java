package med.voll.api.domain.paciente;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;

public record DadosDetalhamentoPaciente(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        DadosEndereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                new DadosEndereco(paciente.getEndereco()));
    }


}
