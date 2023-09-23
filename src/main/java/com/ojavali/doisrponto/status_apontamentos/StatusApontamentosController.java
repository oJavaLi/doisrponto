package com.ojavali.doisrponto.status_apontamentos;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StatusApontamentosController {

    @Autowired
    StatusApontamentosRepository statusApontamentosRepository;

    //Cadastro Status de Apontamentos
    @PostMapping("/cadastrarStatus")
    public ResponseEntity<StatusApontamentos> cadastrarStatus(@RequestBody @Validated StatusApontamentosRecord statusRecord){
        var status = new StatusApontamentos();
        BeanUtils.copyProperties(statusRecord, status);
        return ResponseEntity.status(HttpStatus.OK).body(statusApontamentosRepository.save(status));
    }

    //Retornar Status de Apontamentos
    @GetMapping("/status")
    public ResponseEntity<List<StatusApontamentos>> getAllStatus(){
        return ResponseEntity.status(HttpStatus.OK).body(statusApontamentosRepository.findAll());
    }

    //Retorna status com base no id
    @GetMapping("/status/{id}")
    public ResponseEntity<Object> getStatus(@PathVariable(value = "id") Integer id){
        Optional<StatusApontamentos> product0 = statusApontamentosRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    //Atualiza dados de um Status
    @PutMapping("/status/{id}")
    public ResponseEntity<Object> updateCR(@PathVariable(value = "id") Integer id,
                                           @RequestBody @Validated StatusApontamentosRecord statusRecord){
        Optional<StatusApontamentos> product0 = statusApontamentosRepository.findById(id);
        var status = product0.get();
        BeanUtils.copyProperties(statusRecord, status);
        return ResponseEntity.status(HttpStatus.OK).body(statusApontamentosRepository.save(status));
    }

    //Deleta Status
    @DeleteMapping("/status/{id}")
    public ResponseEntity<Object> deleteCR(@PathVariable(value = "id") Integer id){
        Optional<StatusApontamentos> produto0 = statusApontamentosRepository.findById(id);
        statusApontamentosRepository.delete(produto0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Status de Apontamento deletado com sucesso!");
    }
}
