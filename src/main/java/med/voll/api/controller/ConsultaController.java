package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsultas;
import med.voll.api.domain.consulta.DadosDetalhamentoConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {
    @Autowired
    private AgendaDeConsultas agenda;


    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsultas dados){

        agenda.agendar(dados);

        return ResponseEntity.ok(new DadosDetalhamentoConsultas(null, null, null, null));
    }

}
