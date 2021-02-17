package rest.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TaskController {
	@Autowired
	private TaskService service;
	@Autowired
	private TaskListService service2;
	
	@RequestMapping("/tasks")
	public String viewTaskPage(Model model) {
		List<Task> listTasks = service.getAllTasks();
		model.addAttribute("listTasks", listTasks);
		
		return "tasks";
	}

	@RequestMapping("/tasks/new_task")
	public String addTask(Model model) {
		Task task= new Task();
		model.addAttribute("task", task);
		model.addAttribute("listTaskLists", service2.getAllTaskLists());
		
		return "new_task";
	}

	@RequestMapping(value = "/tasks/save_task", method = RequestMethod.POST)
	public String saveTask(@ModelAttribute("task") Task task) {
		service.saveTask(task);
		
		return "redirect:/tasks";
	}
	@RequestMapping("/tasks/edit_task/{id}")
	public ModelAndView editTask(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("edit_task");
		Task task = service.getTask(id);
		mav.addObject("task", task);
        mav.addObject("listTaskLists", service2.getAllTaskLists());
		return mav;
	}

	@RequestMapping("/tasks/delete_task/{id}")
	public String deleteTask(@PathVariable(name = "id") Integer id) {
		service.deleteTask(id);;
		return "redirect:/tasks";		
	}
}
