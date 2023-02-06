package kr.co.farmstory.repository;

import kr.co.farmstory.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepo extends JpaRepository<ArticleEntity, Integer> {
}
