package rest.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubtaskServiceImpl implements SubtaskService{

	@Autowired
	SubTaskRepository repo;
	@Override
	public List<Subtask> getAllSubtasks() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public void saveSubtask(Subtask subtask) {
		// TODO Auto-generated method stub
		repo.save(subtask);
	}

	@Override
	public Subtask getSubtask(Integer id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	@Override
	public void deleteSubtask(Integer id) {
		repo.deleteById(id);
		
	}

}
