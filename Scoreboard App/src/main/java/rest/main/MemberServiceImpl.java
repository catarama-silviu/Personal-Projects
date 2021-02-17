package rest.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class MemberServiceImpl implements MemberService {
@Autowired
MemberRepository repo;

	@Override
	public List<Member> getAllMembers() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public void saveMember(Member member) {
		// TODO Auto-generated method stub
		repo.save(member);
	}

	@Override
	public rest.main.Member getMember(Integer id) {
		// TODO Auto-generated method stub
		return repo.findById(id).get();
	}

	@Override
	public void deleteMember(Integer id) {
		// TODO Auto-generated method stub
		repo.deleteById(id);
	}

}
