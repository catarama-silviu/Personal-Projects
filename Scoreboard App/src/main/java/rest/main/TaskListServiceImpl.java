package rest.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
;
@Service
@Transactional
public class TaskListServiceImpl implements TaskListService{
@Autowired
TaskListRepository taskListRepository;
	
	@Override
	public List<TaskList> getAllTaskLists() {
		// TODO Auto-generated method stub
		return taskListRepository.findAll();
	}

	@Override
	public void saveTaskList(TaskList taskList) {
		// TODO Auto-generated method stub
		taskListRepository.save(taskList);
	}

	@Override
	public TaskList getTaskList(Integer id) {
		// TODO Auto-generated method stub
		return taskListRepository.findById(id).get();
	}

	@Override
	public void deleteTaskList(Integer id) {
		// TODO Auto-generated method stub
		taskListRepository.deleteById(id);
	}

	

}
