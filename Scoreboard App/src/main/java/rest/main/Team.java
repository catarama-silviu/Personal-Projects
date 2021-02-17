package rest.main;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
@XmlRootElement(name="getTeams") 
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Team implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4768934831300492065L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idteam;
	private String teamName;
	@OneToMany(cascade = ALL, mappedBy = "team_taskList",orphanRemoval = true)
	private List<TaskList>team_taskList= new ArrayList<TaskList>();
	
	@OneToMany(cascade = ALL, mappedBy = "member_teamList",orphanRemoval = true)
	private List<Member>team_memberList= new ArrayList<Member>();
	
	public Team(Integer idteam, String teamName, List<TaskList> team_taskList,
			List<Member> team_memberList) {
		super();
		this.idteam = idteam;
		this.teamName = teamName;
		this.team_taskList = team_taskList;
		this.team_memberList = team_memberList;
	}
	
	public Team() {
		super();
	}
	@XmlElement
	public Integer getIdteam() {
		return idteam;
	}

	public void setIdteam(Integer idteam) {
		this.idteam = idteam;
	}
	@XmlElement
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public List<TaskList> getTeam_taskList() {
		return team_taskList;
	}

	public void setTeam_taskList(List<TaskList> team_taskList) {
		this.team_taskList = team_taskList;
	}

	public List<Member> getTeam_memberList() {
		return team_memberList;
	}

	public void setTeam_memberList(List<Member> team_memberList) {
		this.team_memberList = team_memberList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idteam == null) ? 0 : idteam.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (idteam == null) {
			if (other.idteam != null)
				return false;
		} else if (!idteam.equals(other.idteam))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Team [idteam=" + idteam + ", teamName=" + teamName + "]";
	}
	
	
	
	
	
	
	
}
