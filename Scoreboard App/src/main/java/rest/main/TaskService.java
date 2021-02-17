package rest.main;

import java.util.List;



public interface TaskService {

	List<Task>getAllTasks();
	void saveTask(Task task);
	Task getTask(Integer id);
	void deleteTask(Integer id);
}

