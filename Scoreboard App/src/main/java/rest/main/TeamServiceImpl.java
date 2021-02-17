package rest.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	TeamRepository repo;
	@Override
	public List<Team> getAllTeams() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public void saveTeam(Team team) {
		// TODO Auto-generated method stub
		repo.save(team);
		
	}

	@Override
	public rest.main.Team getTeam(Integer id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	@Override
	public void deleteTeam(Integer id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
