package com.ojavali.doisrponto.apontamentos;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;

@RestController

public class ApontamentosController {

    @Autowired
    ApontamentosRepository apontamentosRepository;

    //Cadastro Apontamento
    @PostMapping("/cadastrarApontamentos")
    public ResponseEntity<Apontamentos> cadastrarApontamentos(@RequestBody @Validated ApontamentosRecord apontamentosRecordDto){
        var apontamentos = new Apontamentos();
        BeanUtils.copyProperties(apontamentosRecordDto, apontamentos);
        return ResponseEntity.status(HttpStatus.OK).body(apontamentosRepository.save(apontamentos));
    }

    //Retornar Usuarios Cadastrados
    @GetMapping("/apontamentos")
    public ResponseEntity<List<Apontamentos>> getAllApontamentos(){
        return ResponseEntity.status(HttpStatus.OK).body(apontamentosRepository.findAll());
    }

    //Retorna usuario com base na sua matricula
    @GetMapping("/apontamentos/{id}")
    public ResponseEntity<Object> getApontamento(@PathVariable(value = "id") Long id){
        Optional<Apontamentos> product0 = apontamentosRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    //Atualiza dados de um apontamento
    @PutMapping("/apontamentos/{id}")
    public ResponseEntity<Object> updateApontamento(@PathVariable(value = "id") Long id,
                                                @RequestBody @Validated ApontamentosRecord apontamentosRecordDto){
        Optional<Apontamentos> product0 = apontamentosRepository.findById(id);
        var apontamentos = product0.get();
        BeanUtils.copyProperties(apontamentosRecordDto, apontamentos);
        return ResponseEntity.status(HttpStatus.OK).body(apontamentosRepository.save(apontamentos));
    }

    //Deleta Apontamento
    @DeleteMapping("/apontamentos/{id}")
    public ResponseEntity<Object> deleteApontamento(@PathVariable(value = "id") Long id){
        Optional<Apontamentos> produto0 = apontamentosRepository.findById(id);
        apontamentosRepository.delete(produto0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Apontamento deletado com sucesso!");
    }

    @GetMapping("/apontamentos/matricula/{matricula}")
    public ResponseEntity<List<Apontamentos>> getApontamentosPorMatricula(@PathVariable(value = "matricula") int matricula){
        List<Apontamentos> apontamentos = apontamentosRepository.findByUsuarioMatricula(matricula);
        return ResponseEntity.status(HttpStatus.OK).body(apontamentos);
    }


}