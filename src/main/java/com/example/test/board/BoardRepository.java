package com.example.test.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardDTO, Object>{
	@Query(nativeQuery=true,
			value="select rnum,num,title,content,writer,wdate  "
			+ "		from (select ROW_NUMBER() OVER(ORDER BY num desc) AS rnum,"
			+ "		num,title ,content,writer,wdate from BoardDTO)  "
			+ "		where rnum between ?1 and ?2")
	public List<BoardDTO> selectAllPageBlock(Integer startRow, Integer endRow);

	public int deleteByNum(int num);

}
