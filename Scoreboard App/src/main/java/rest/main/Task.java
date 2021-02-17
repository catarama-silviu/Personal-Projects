package rest.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.persistence.CascadeType.ALL;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
@XmlRootElement(name="getTasks") 
@XmlAccessorType(XmlAccessType.NONE)
@Entity
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 665189216504231984L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer idTask;
	private String taskName;
	private String taskDescription;
	private String taskStatus;
	@ManyToOne
	private TaskList task_taskList;
	
	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "member_taskList_idTask", referencedColumnName = "idTask"), inverseJoinColumns = @JoinColumn(name = "task_memberList_idMember", referencedColumnName = "idMember"))
	private List<Member>task_memberList= new ArrayList<>();
	@OneToMany(cascade = ALL, mappedBy = "subtask_task", orphanRemoval = true)
	private List<Subtask>task_subtaskList= new ArrayList<>();
	
	
	public Task(Integer idTask, String taskName, String taskDescription, String taskStatus, TaskList task_taskList,
			List<Member> task_memberList, List<Subtask> task_subtaskList) {
		super();
		this.idTask = idTask;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
		this.taskStatus = taskStatus;
		this.task_taskList = task_taskList;
		this.task_memberList = task_memberList;
		this.task_subtaskList = task_subtaskList;
	}
	public Task() {
		super();
	}
	@XmlElement
	public Integer getIdTask() {
		return idTask;
	}
	public void setIdTask(Integer idTask) {
		this.idTask = idTask;
	}
	@XmlElement
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	@XmlElement
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	@XmlElement
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public TaskList getTask_taskList() {
		return task_taskList;
	}
	public void setTask_taskList(TaskList task_taskList) {
		this.task_taskList = task_taskList;
	}
	public List<Member> getTask_memberList() {
		return task_memberList;
	}
	public void setTask_memberList(List<Member> task_memberList) {
		this.task_memberList = task_memberList;
	}
	public List<Subtask> getTask_subtaskList() {
		return task_subtaskList;
	}
	public void setTask_subtaskList(List<Subtask> task_subtaskList) {
		this.task_subtaskList = task_subtaskList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTask == null) ? 0 : idTask.hashCode());
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
		Task other = (Task) obj;
		if (idTask == null) {
			if (other.idTask != null)
				return false;
		} else if (!idTask.equals(other.idTask))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Task [idTask=" + idTask + ", taskName=" + taskName + ", taskDescription=" + taskDescription
				+ ", taskStatus=" + taskStatus + ", task_taskList=" + task_taskList + ", task_memberList="
				+ task_memberList + ", task_subtaskList=" + task_subtaskList + "]";
	}
	
	
	
}
