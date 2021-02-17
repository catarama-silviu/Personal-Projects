package rest.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

	
	@Autowired
	private BoardRepository boardRepository;
	
	@Override
	public List<Board> getAllBoards() {
		// TODO Auto-generated method stub
		return boardRepository.findAll();
	}

	@Override
	public void saveBoard(Board board) {
		// TODO Auto-generated method stub
		this.boardRepository.save(board);
	}

	@Override
	public Board getBoard(Integer id) {
		// TODO Auto-generated method stub
		return boardRepository.findById(id).get();
	}

	@Override
	public void deleteBoard(Integer id) {
		// TODO Auto-generated method stub
		boardRepository.deleteById(id);
	}
	
	

}
