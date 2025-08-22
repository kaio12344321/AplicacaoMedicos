package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class AgendaDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository Consultarepository;


    public void agendar(DadosAgendamentoConsultas dados) {
        // ----------------- Código para satisfazer as regras de negócio -----------------
        // Validar se o id do paciente existe
        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe!");
        }

        // Validar se o id do médico existe e se ele não for nulo
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe!");
        }



        // ----------------- Lógica final do método de agendar consulta -----------------
        // Escolher um médico de forma aleatória
        var medico = escolherMedico(dados);


        var paciente = pacienteRepository.findById(dados.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, dados.data());

        Consultarepository.save(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsultas dados) {
        if (dados.idMedico() != null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido!");
        }

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade() , dados.data());
    }
}
