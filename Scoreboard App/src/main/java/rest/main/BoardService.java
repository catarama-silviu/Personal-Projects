package rest.main;

import java.util.List;



public interface BoardService {

	List<Board>getAllBoards();
	void saveBoard(Board board);
	Board getBoard(Integer id);
	void deleteBoard(Integer id);
}
