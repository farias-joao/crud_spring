package df.senac.br.spring_mongodb.respositorio;

import df.senac.br.spring_mongodb.model.Autor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AutorRepositorio extends MongoRepository<Autor, String> {

    List<Autor> findByNome(String nome);
    List<Autor> findByAtivo(boolean ativo);

}
