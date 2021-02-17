package rest.main;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;





@Controller
@RequestMapping("/")
public class BoardController {
	@Autowired
	private BoardService service;
	

	@RequestMapping(value="getBoards",method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
public List<Board> vviewHomePage() {
	return service.getAllBoards();
}
	
	@RequestMapping("/")
public String viewHomePage(Model model) {
	List<Board> listBoards = service.getAllBoards();
	model.addAttribute("listBoards", listBoards);
	
	return "index";
}

@RequestMapping("/new_board")
public String addBoard(Model model) {
	Board board= new Board();
	model.addAttribute("board", board);
	
	return "new_board";
}

@RequestMapping(value = "/save_board", method = RequestMethod.POST)
public String saveBoard(@ModelAttribute("board") Board board) {
	service.saveBoard(board);
	
	return "redirect:/";
}
@RequestMapping("/edit_board/{id}")
public ModelAndView editBoard(@PathVariable(name = "id") Integer id) {
	ModelAndView mav = new ModelAndView("edit_board");
	Board board = service.getBoard(id);
	mav.addObject("board", board);
	
	return mav;
}

@RequestMapping(value="/eedit_board/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE})
@ResponseBody
public Board  eeditBoard(@PathVariable(name = "id") Integer id) {
	ModelAndView mav = new ModelAndView("edit_board");
	Board board = service.getBoard(id);
	mav.addObject("board", board);
	
	return board;
}


@RequestMapping("/delete_board/{id}")
public String deleteBoard(@PathVariable(name = "id") Integer id) {
	service.deleteBoard(id);
	return "redirect:/";		
}


}
