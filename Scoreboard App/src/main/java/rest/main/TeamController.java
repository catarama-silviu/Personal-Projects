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
public class TeamController {
	@Autowired
	private TeamService service;
	
	@RequestMapping("/teams")
	public String viewTeamPage(Model model) {
		List<Team> listTeams = service.getAllTeams();
		model.addAttribute("listTeams", listTeams);
		
		return "teams";
	}

	@RequestMapping("/teams/new_team")
	public String addTeam(Model model) {
		Team team= new Team();
		model.addAttribute("team", team);
		
		return "new_team";
	}

	@RequestMapping(value = "/teams/save_team", method = RequestMethod.POST)
	public String saveTeam(@ModelAttribute("team") Team team) {
		service.saveTeam(team);
		
		return "redirect:/teams";
	}
	@RequestMapping("/teams/edit_team/{id}")
	public ModelAndView editTeam(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("edit_team");
		Team team = service.getTeam(id);
		mav.addObject("team", team);
		
		return mav;
	}

	@RequestMapping("/teams/delete_team/{id}")
	public String deleteTeam(@PathVariable(name = "id") Integer id) {
		service.deleteTeam(id);;
		return "redirect:/teams";		
	}
}
