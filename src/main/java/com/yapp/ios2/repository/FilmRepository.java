package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findAllByUser(User user);

    @Query("select f from Film f, User u where f.user = u and f.deleteAt is null")
    List<Film> findAllByUserAndDeleteAt(User user);

    List<Film> findAllByName(String name);

    @Query("select f from Film f where f.printEndAt < CURRENT_TIMESTAMP")
    List<Film> findAllPrintedFilms();
}
