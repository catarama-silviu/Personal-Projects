package rest.main;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Lob;

@XmlRootElement(name="getMembers") 
@XmlAccessorType(XmlAccessType.NONE)@Entity
public class Member implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5372992614215916076L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idMember;
	private String member_name;
	private String activity_domanin;
	
	private String username;
	
	private String password;
	@Enumerated(STRING)
	private UserRole userRole;
	
	@ManyToMany(mappedBy = "task_memberList")
	private List<Task> member_taskList= new ArrayList<>();
	
	@ManyToOne
	private Team member_teamList;
	
	public Member(String member_name, String activity_domanin,  String username,  String password,
			UserRole userRole) {
		super();
		this.member_name = member_name;
		this.activity_domanin = activity_domanin;
		this.username = username;
		this.password = password;
		this.userRole = userRole;
	
	}
	public Member() {
		super();
	}
	@XmlElement
	public Integer getIdMember() {
		return idMember;
	}
	public void setIdMember(Integer idMember) {
		this.idMember = idMember;
	}
	@XmlElement
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	@XmlElement
	public String getActivity_domanin() {
		return activity_domanin;
	}
	public void setActivity_domanin(String activity_domanin) {
		this.activity_domanin = activity_domanin;
	}
	@XmlElement
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public List<Task> getMember_taskList() {
		return member_taskList;
	}
	public void setMember_taskList(List<Task> member_taskList) {
		this.member_taskList = member_taskList;
	}
	public Team getMember_teamList() {
		return member_teamList;
	}
	public void setMember_teamList(Team member_teamList) {
		this.member_teamList = member_teamList;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMember == null) ? 0 : idMember.hashCode());
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
		Member other = (Member) obj;
		if (idMember == null) {
			if (other.idMember != null)
				return false;
		} else if (!idMember.equals(other.idMember))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Member [idMember=" + idMember + ", member_name=" + member_name + ", activity_domanin="
				+ activity_domanin + ", userRole=" + userRole + "]";
	}
	
	
	
}
