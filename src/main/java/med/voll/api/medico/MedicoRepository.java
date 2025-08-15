package med.voll.api.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;


public interface MedicoRepository extends JpaRepository<Medico, Long> {
    @Query("""
            SELECT m
            FROM Medico m
            WHERE m.ativo = true
            ORDER BY m.nome ASC
            """)
    Page<Medico> listarMedicosOrdenarNome(Pageable paginacao);
}
