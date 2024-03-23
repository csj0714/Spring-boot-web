package com.example.test.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepo;
	
	public List<BoardDTO> selectAllPageBlock(int cpage, int pageBlock){
		int startRow = (cpage - 1) * pageBlock + 1;
		int endRow = startRow + pageBlock - 1;

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		return boardRepo.selectAllPageBlock(startRow, endRow);
	}

	public long getTotalRows() {
		return boardRepo.count();
	}
	public BoardDTO insertOK(BoardDTO vo) {
		return boardRepo.save(vo); //pk 즉 num값이 있으면 수정, 없으면 입력, dao재정의 필요없음
	}

	public BoardDTO updateOK(BoardDTO vo) {
		return boardRepo.save(vo); //pk 즉 num값이 있으면 수정, 없으면 입력, dao재정의 필요없음
	}

	public int deleteOK(BoardDTO vo) {
		return boardRepo.deleteByNum(vo.getNum()); //함수커스텀
	}
}
