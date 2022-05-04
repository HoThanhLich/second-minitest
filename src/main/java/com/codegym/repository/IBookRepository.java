package com.codegym.repository;

import com.codegym.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends PagingAndSortingRepository<Book, Long> {

    Page<Book> findByNameContaining(String name, Pageable pageable);

    @Query(value = "select * from books where category_id = ?1",nativeQuery = true)
    Page<Book> findByCategory(Long id, Pageable pageable);
}
