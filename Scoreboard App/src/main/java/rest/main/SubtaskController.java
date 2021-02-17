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
public class SubtaskController {
	@Autowired
	private SubtaskService service;
	@Autowired
	private TaskService service1;
	@Autowired
	private MemberService service2;
	
	@RequestMapping("/subtasks")
	public String viewSubtaskPage(Model model) {
		List<Subtask> listSubtasks = service.getAllSubtasks();
		model.addAttribute("listSubtasks", listSubtasks);
		
		
		return "subtasks";
	}

	@RequestMapping("/subtasks/new_subtask")
	public String addSubtask(Model model) {
		Subtask subtask= new Subtask();
		model.addAttribute("subtask", subtask);
		model.addAttribute("listTasks", service1.getAllTasks());
		model.addAttribute("listMembers", service2.getAllMembers());
		return "new_subtask";
	}

	@RequestMapping(value = "/subtasks/save_subtask", method = RequestMethod.POST)
	public String saveSubtask(@ModelAttribute("subtask") Subtask subtask) {
		service.saveSubtask(subtask);
		
		return "redirect:/subtasks";
	}
	@RequestMapping("/subtasks/edit_subtask/{id}")
	public ModelAndView editSubtask(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("edit_subtask");
		Subtask subtask = service.getSubtask(id);
		mav.addObject("subtask", subtask);
		mav.addObject("listTasks", service1.getAllTasks());
		mav.addObject("listMembers", service2.getAllMembers());
		
		return mav;
	}

	@RequestMapping("/subtasks/delete_subtask/{id}")
	public String deleteSubtask(@PathVariable(name = "id") Integer id) {
		service.deleteSubtask(id);
		return "redirect:/subtasks";		
	}
}
