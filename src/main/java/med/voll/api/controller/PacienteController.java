package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.DadosListagemPaciente;
import med.voll.api.domain.paciente.DadosAtualizacaoPaciente;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    @Autowired
    PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody DadosCadastroPaciente dados){
        repository.save(new Paciente(dados));
    }

//    @GetMapping
//    public Page<DadosListagemPaciente> listar(Pageable paginacao){
//        return repository.findAll(paginacao)
//                .map(p -> new DadosListagemPaciente(p.getId(), p.getNome(), p.getEmail(), p.getCpf()));
//    }


//    @GetMapping
//    public List<DadosListagemPaciente> listar(@PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.DESC) Pageable paginacao){
//        return repository.findAll(paginacao).stream()
//                .map(p -> new DadosListagemPaciente(p.getId(), p.getNome(), p.getEmail(), p.getCpf()))
//                .toList();
//    }

    @GetMapping
    public Page<DadosListagemPaciente> listar(@PageableDefault(page = 0, size = 10, sort = "nome") Pageable paginacao){
        return repository.findAll(paginacao)
                .map(p -> new DadosListagemPaciente(p.getId(), p.getNome(), p.getEmail(), p.getCpf()));
    }

    @PutMapping
    @Transactional
    public void atualizacaoPacientes(@RequestBody @Valid DadosAtualizacaoPaciente dados){
        var paciente = repository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.exclui();
    }

}
