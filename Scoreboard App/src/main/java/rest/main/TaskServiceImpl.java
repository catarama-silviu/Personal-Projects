package rest.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskRepository taskRepository;
	@Override
	public List<Task> getAllTasks() {
		// TODO Auto-generated method stub
		return taskRepository.findAll();
	}

	@Override
	public void saveTask(Task task) {
		this.taskRepository.save(task);
		
	}

	@Override
	public Task getTask(Integer id) {
		// TODO Auto-generated method stub
		return taskRepository.findById(id).get();
	}

	@Override
	public void deleteTask(Integer id) {
		// TODO Auto-generated method stub
		taskRepository.deleteById(id);
	}

}
