package rest.main;

import java.util.List;


public interface TeamService {
	List<Team>getAllTeams();
	void saveTeam(Team team);
	Team getTeam(Integer id);
	void deleteTeam(Integer id);

}
