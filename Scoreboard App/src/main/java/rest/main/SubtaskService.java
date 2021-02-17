package rest.main;

import java.util.List;


public interface SubtaskService {
	List<Subtask>getAllSubtasks();
	void saveSubtask(Subtask subtask);
	Subtask getSubtask(Integer id);
	void deleteSubtask(Integer id);
}

