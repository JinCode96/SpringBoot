package kr.co.bookstore.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.bookstore.vo.BookVO;

@Mapper
@Repository
public interface BookDAO {

	public void insertBook(BookVO vo);
	public BookVO selectBook(int bookId);
	public List<BookVO> selectBooks();
	public void updateBook(BookVO vo);
	public void deleteBook(int bookId);
}
