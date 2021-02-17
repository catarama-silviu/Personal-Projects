package rest.main;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;





@Controller
public class TestController {
	@Autowired
	private BoardService bs;
	@Autowired
	private TaskListService tls;
	@Autowired
	private TaskService ts;
	@Autowired
	private SubtaskService sts;
	@Autowired
	private TeamService teamS;
	@Autowired
    private MemberService ms;
	

@RequestMapping("/tests")
public String viewHomePage() {
	
	
	return "tests";
}

@RequestMapping("/tests/new_test")
public String populateBD() {
	Board b1= new Board();
	Board b2= new Board();
	
	TaskList tl1= new TaskList();
	TaskList tl2= new TaskList();
	TaskList tl3= new TaskList();
	
	
	Task t1=new Task();
	Task t2=new Task();
	Task t3=new Task();
	Task t4=new Task();
	
	Subtask st1= new Subtask();
	Subtask st2= new Subtask();
	Subtask st3= new Subtask();
	Subtask st4= new Subtask();
	Subtask st5= new Subtask();
	
	Team team1= new Team();
	Team team2= new Team();
	Team team3= new Team();
	
	Member m1= new Member();
	Member m2= new Member();
	Member m3= new Member();
	Member m4= new Member();
	Member m5= new Member();
	
	//boards
	b1.setBoardName("Travel App");
	b1.setBoardStorie("Storie for Travel App Board");
	b1.setBoardStatus("In Lucru");
	b1.setStartDate_board("19-01-2021");
	b1.setEndDate_board("19-02.2021");
	
	b2.setBoardName("UAIC App");
	b2.setBoardStorie("Storie for UAIC App Board");
	b2.setBoardStatus("In Lucru");
	b2.setStartDate_board("19-01-2021");
	b2.setEndDate_board("25-03.2021");
	//teams
	team1.setTeamName("Backend Travel App");
	team2.setTeamName("Fronted Travel App");
	team3.setTeamName("Full Stack UAIC App");
	//members
	m1.setMember_teamList(team1);
	m1.setMember_name("Ionut");
	m1.setActivity_domanin("IT");
	m1.setUsername("Ionut90");
	m1.setUserRole(UserRole.SCRUM_Master);
	
    m2.setMember_teamList(team1);	
    m2.setMember_name("Alex");
	m2.setActivity_domanin("IT");
	m2.setUsername("Alex09");
	m2.setUserRole(UserRole.Team_Leader);
    
    
    m3.setMember_teamList(team1);
    m3.setMember_name("Mihai");
	m3.setActivity_domanin("IT");
	m3.setUsername("Mihai889");
	m3.setUserRole(UserRole.Team_Member);
    
    m4.setMember_teamList(team2);
    m4.setMember_name("Mark");
	m4.setActivity_domanin("IT");
	m4.setUsername("Markoo0o");
	m4.setUserRole(UserRole.Team_Leader);
    
    m5.setMember_teamList(team3);
    m5.setMember_name("Sebastian");
	m5.setActivity_domanin("IT");
	m5.setUsername("Sebyy");
	m5.setUserRole(UserRole.Team_Leader);
	
	//tasklists
	tl1.setBoard_taskList(b1);
	tl1.setTeam_taskList(team1);
	tl1.setTaskListName("Frontend");
	tl1.setTaskListStatus("In Lucru");
	tl1.setStartDate_taskList("19-01-2021");
	tl1.setEndDate_TaskList("----");
	
	tl2.setBoard_taskList(b1);
	tl2.setTeam_taskList(team2);
	tl2.setTaskListName("Backend");
	tl2.setTaskListStatus("In Lucru");
	tl2.setStartDate_taskList("19-01-2021");
	tl2.setEndDate_TaskList("----");
	
	tl3.setBoard_taskList(b2);
	tl3.setTeam_taskList(team3);
	tl3.setTaskListName("Full Stack");
	tl3.setTaskListStatus("In Lucru");
	tl3.setStartDate_taskList("19-01-2021");
	tl3.setEndDate_TaskList("----");
	//tasks
	t1.setTask_taskList(tl1);
	t1.setTaskName("Implementare Frontend");
	t1.setTaskDescription("Implementare template Themeleaf");
	t1.setTaskStatus("Finalizat");
	
	t2.setTask_taskList(tl1);
	t2.setTaskName("Testare Frontend");
	t2.setTaskDescription("Testare functionalitati");
	t2.setTaskStatus("In Lucru");
	
	t3.setTask_taskList(tl2);
	t3.setTaskName("Implementare Backendend");
	t3.setTaskDescription("Implementare functionalitati aplicatie");
	t3.setTaskStatus("Finalizat");
	
	
	t4.setTask_taskList(tl3);
	t4.setTaskName("Testare Aplicatie");
	t4.setTaskDescription("Testare Aplicatie");
	t4.setTaskStatus("In Lucru");
	
	//subtasks
	
	st1.setSubtask_task(t1);
	st1.setSubtask_member(m1);
	st1.setSubtask_name("Pagini HTML ADD");
	st1.setSubtask_description("Realizarea paginilor HTML necesare operatiunilor de creare obiecte");
	st1.setSubtask_status("Finalizat");
	
	st2.setSubtask_task(t1);
	st2.setSubtask_member(m2);
	st2.setSubtask_name("Pagini HTML Edit");
	st2.setSubtask_description("Realizarea paginilor HTML necesare operatiunilor de editare obiecte");
	st2.setSubtask_status("Finalizat");
	
	st3.setSubtask_task(t2);
	st3.setSubtask_member(m3);
	st3.setSubtask_name("Realizare teste");
	st3.setSubtask_description("Testarea frontend-ului aplicatiei");
	st3.setSubtask_status("In Lucru");
	
	st4.setSubtask_task(t3);
	st4.setSubtask_member(m4);
	st4.setSubtask_name("Implementare clase Controller");
	st4.setSubtask_description("Realizarea claselor Controller");
	st4.setSubtask_status("Finalizat");
	
	st5.setSubtask_task(t4);
	st5.setSubtask_member(m5);
	st5.setSubtask_name("Testare Backend");
	st5.setSubtask_description("Realizarea testelor functionalitatilor aplicatiei");
	st5.setSubtask_status("Finalizat");
	
	//save BD
	
	bs.saveBoard(b1);
	bs.saveBoard(b2);
	
	teamS.saveTeam(team1);
	teamS.saveTeam(team2);
	teamS.saveTeam(team3);
	
	ms.saveMember(m1);
	ms.saveMember(m2);
	ms.saveMember(m3);
	ms.saveMember(m4);
	ms.saveMember(m5);
	
	tls.saveTaskList(tl1);
	tls.saveTaskList(tl2);
	tls.saveTaskList(tl3);
	
	ts.saveTask(t1);
	ts.saveTask(t2);
	ts.saveTask(t3);
	ts.saveTask(t4);
	
	sts.saveSubtask(st1);
	sts.saveSubtask(st2);
	sts.saveSubtask(st3);
	sts.saveSubtask(st4);
	sts.saveSubtask(st5);
	
	return "redirect:/tests";
}






}
