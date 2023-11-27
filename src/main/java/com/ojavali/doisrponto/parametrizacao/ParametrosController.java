package com.ojavali.doisrponto.parametrizacao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parametros")
public class ParametrosController {
    @Autowired
    private ParametrosRepository repo;

    @GetMapping
    public ResponseEntity<List<Parametro>> getAllEntries() {
        List<Parametro> entries = repo.findAll();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parametro> getParametroById(@PathVariable Long id) {
        Parametro entry = repo.findById(id).orElse(null);
        return (entry != null) ?
                new ResponseEntity<>(entry, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Parametro> createParametro(@RequestBody Parametro entry) {
        Parametro savedParametro = repo.save(entry);
        return new ResponseEntity<>(savedParametro, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parametro> updateParametro(@PathVariable Long id, @RequestBody Parametro updatedParametro) {
        Parametro existingParametro = repo.findById(id).orElse(null);

        if (existingParametro != null) {
            existingParametro.setTipo(updatedParametro.getTipo());
            existingParametro.setVerba((updatedParametro.getVerba()));
            existingParametro.setHoras(updatedParametro.getHoras());
            existingParametro.setPorcentagem(updatedParametro.getPorcentagem());
            existingParametro.setDescricao(updatedParametro.getDescricao());

            Parametro savedParametro = repo.save(existingParametro);
            return new ResponseEntity<>(savedParametro, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParametro(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
