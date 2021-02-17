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
public class MemberController {
	@Autowired
	private MemberService service;
	@Autowired
	private TeamService service1;
	@RequestMapping("/members")
	public String viewMemberPage(Model model) {
		List<Member> listMembers = service.getAllMembers();
		model.addAttribute("listMembers", listMembers);
		
		return "members";
	}

	@RequestMapping("/members/new_member")
	public String addMember(Model model) {
		Member member= new Member();
		model.addAttribute("member", member);
		model.addAttribute("listTeams", service1.getAllTeams());
		return "new_member";
	}

	@RequestMapping(value = "/members/save_member", method = RequestMethod.POST)
	public String saveMember(@ModelAttribute("member") Member member) {
		service.saveMember(member);
		
		return "redirect:/members";
	}
	@RequestMapping("/members/edit_member/{id}")
	public ModelAndView editMember(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("edit_member");
		Member member = service.getMember(id);
		mav.addObject("member", member);
		mav.addObject("listTeams", service1.getAllTeams());
		return mav;
	}

	@RequestMapping("/members/delete_member/{id}")
	public String deleteMember(@PathVariable(name = "id") Integer id) {
		service.deleteMember(id);;
		return "redirect:/members";		
	}
}
