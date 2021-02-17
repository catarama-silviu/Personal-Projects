package rest.main;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface SubTaskRepository extends JpaRepository<Subtask, Integer>{

}
