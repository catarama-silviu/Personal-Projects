package rest.main;

import java.util.List;




public interface TaskListService {

	List<TaskList>getAllTaskLists();
	void saveTaskList(TaskList taskList);

	TaskList getTaskList(Integer id);
	void deleteTaskList(Integer id);
	
}
