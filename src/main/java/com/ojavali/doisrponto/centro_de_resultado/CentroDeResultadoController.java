package com.ojavali.doisrponto.centro_de_resultado;

// import com.ojavali.doisrponto.apontamentos.Apontamentos;
// import com.ojavali.doisrponto.apontamentos.ApontamentosRecord;
// import com.ojavali.doisrponto.apontamentos.ApontamentosRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class CentroDeResultadoController {
    @Autowired
    CentroDeResultadoRepository CRRepository;

    //Cadastro Centro de Resultado
    @PostMapping("/cadastrarCR")
    public ResponseEntity<CentroDeResultado> cadastrarCR(@RequestBody @Validated CentroDeResultadoRecord CRRecordDTO){
        var CR = new CentroDeResultado();
        BeanUtils.copyProperties(CRRecordDTO, CR);
        return ResponseEntity.status(HttpStatus.OK).body(CRRepository.save(CR));
    }

    //Retornar Centro de Resultado
    @GetMapping("/CR")
    public ResponseEntity<List<CentroDeResultado>> getAllCR(){
        return ResponseEntity.status(HttpStatus.OK).body(CRRepository.findAll());
    }

    //Retorna usuario com base na sua matricula
    @GetMapping("/CR/{id}")
    public ResponseEntity<Object> getCR(@PathVariable(value = "id") Integer id){
        Optional<CentroDeResultado> product0 = CRRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product0.get());
    }

    //Atualiza dados de um Centro de Resultado
    @PutMapping("/CR/{id}")
    public ResponseEntity<Object> updateCR(@PathVariable(value = "id") Integer id,
                                                    @RequestBody @Validated CentroDeResultadoRecord CRRecordDTO){
        Optional<CentroDeResultado> product0 = CRRepository.findById(id);
        var CR = product0.get();
        BeanUtils.copyProperties(CRRecordDTO, CR);
        return ResponseEntity.status(HttpStatus.OK).body(CRRepository.save(CR));
    }

    //Deleta Centro de Resultado
    @DeleteMapping("/CR/{id}")
    public ResponseEntity<Object> deleteCR(@PathVariable(value = "id") Integer id){
        Optional<CentroDeResultado> produto0 = CRRepository.findById(id);
        CRRepository.delete(produto0.get());
        return ResponseEntity.status(HttpStatus.OK).body("CR deletado com sucesso!");
    }
}
