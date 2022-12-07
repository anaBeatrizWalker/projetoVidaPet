package br.fatec.vidapet.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.vidapet.dto.AtendenteDTO;
import br.fatec.vidapet.mapper.AtendenteMapper;
import br.fatec.vidapet.model.Atendente;
import br.fatec.vidapet.service.AtendenteService;

@RestController
@RequestMapping("/atendentes")
public class AtendenteController implements ControllerInterface<AtendenteDTO>{
	
	@Autowired
	private AtendenteService service;
	
	@Autowired 
	private AtendenteMapper mapper;
	
	@Override
	@GetMapping
	public ResponseEntity<List<AtendenteDTO>> getAll(){
		return ResponseEntity.ok(mapper.toDTO(service.findAll()));
	}
	
	@Override
	@GetMapping(value = "/{id}")
	public ResponseEntity<AtendenteDTO> getOne(@PathVariable("id") Long id){
		Atendente obj = service.findById(id);
		if(obj != null) {
			return ResponseEntity.ok(mapper.toDTO(obj));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@Override
	@PostMapping
	public ResponseEntity<AtendenteDTO> post(@Valid @RequestBody AtendenteDTO obj) throws URISyntaxException{
		Atendente atendente = service.create(mapper.toEntity(obj));
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(atendente.getId()).toUri();
		return ResponseEntity.created(location).body(mapper.toDTO(atendente));
	}
	
	@Override
	@PutMapping
	public ResponseEntity<AtendenteDTO> put(@Valid @RequestBody AtendenteDTO obj){
		if(service.update(mapper.toEntity(obj))) {
			return ResponseEntity.ok(obj);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@Override
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id){
		if(service.delete(id)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
}
