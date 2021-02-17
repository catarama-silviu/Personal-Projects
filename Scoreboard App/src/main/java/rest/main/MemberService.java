package rest.main;

import java.util.List;



public interface MemberService {

	List<Member>getAllMembers();
	void saveMember(Member member);
	Member getMember(Integer id);
	void deleteMember(Integer id);
}

