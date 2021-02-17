package rest.main;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;



@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

}
