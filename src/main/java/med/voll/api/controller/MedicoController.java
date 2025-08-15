package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.endereco.DadosEndereco;
import med.voll.api.endereco.Endereco;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroMedico dados){
        repository.save(new Medico(dados)); // Converte MedicoDTO para Medico
    }


    @GetMapping
    public Page<DadosListagemMedico> listarMedicosOrdenarNome(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        return repository.listarMedicosOrdenarNome(paginacao).map(m -> new DadosListagemMedico(m.getId(), m.getNome(),
                        m.getEmail(), m.getCrm(), m.getEspecialidade()));
    }


    @PutMapping
    @Transactional
    public void atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

    }

    // Exclusão Física
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void deletar(@PathVariable Long id){
//        repository.deleteById(id);
//    }

    // Exclusão Lógica
    @DeleteMapping("/{id}")
    @Transactional
    public void deletar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }


}
