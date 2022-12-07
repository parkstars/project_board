package com.project.pro.repository;

import com.project.pro.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository <Article, Long>{
}
