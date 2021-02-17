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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class TaskListController {
	@Autowired
	private TaskListService service;
	@Autowired
	private BoardService service1;
	@Autowired
	private TeamService service2;
	
	
	
	@RequestMapping("/tasklists")
	public String viewTaskListPage(Model model) {
		List<TaskList> listTaskLists = service.getAllTaskLists();
		model.addAttribute("listTaskLists", listTaskLists);
		return "tasklists";
	}

	
	


	@RequestMapping("/tasklists/new_tasklist")
	public String addTaskList(Model model) {
		TaskList taskList= new TaskList();
		model.addAttribute("taskList", taskList);
		model.addAttribute("listBoards", service1.getAllBoards());
		model.addAttribute("listTeams", service2.getAllTeams());
		return "new_tasklist";
	}

	@RequestMapping(value = "/tasklists/save_tasklist", method = RequestMethod.POST)
	public String saveTaskList(@ModelAttribute("taskList") TaskList taskList) {
		service.saveTaskList(taskList);
		
		return "redirect:/tasklists";
	}
	@RequestMapping("/tasklists/edit_tasklist/{id}")
	public ModelAndView editTaskList(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("edit_tasklist");
		TaskList taskList = service.getTaskList(id);
		mav.addObject("taskList", taskList);
		mav.addObject("listBoards", service1.getAllBoards());
		mav.addObject("listTeams", service2.getAllTeams());
		return mav;
	}

	@RequestMapping("/tasklists/delete_tasklist/{id}")
	public String deleteTaskList(@PathVariable(name = "id") Integer id) {
		service.deleteTaskList(id);;
		return "redirect:/tasklists";		
	}
}
