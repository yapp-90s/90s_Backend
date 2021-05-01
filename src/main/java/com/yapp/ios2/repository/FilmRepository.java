package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Film;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {

    List<Film> findAllByUser(User user);


}
