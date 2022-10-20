package br.fatec.vidapet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.fatec.vidapet.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>  {

}
