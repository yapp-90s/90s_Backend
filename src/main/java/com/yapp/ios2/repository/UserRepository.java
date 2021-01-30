package com.yapp.ios2.repository;

import com.yapp.ios2.vo.Album;
import com.yapp.ios2.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.emailKakao = :emailKakao")
    Optional<User> findUserByEmailKakao(@Param("emailKakao") String emailKakao);
    @Query("select u from User u where u.emailApple = :emailApple")
    Optional<User> findUserByEmailApple(@Param("emailApple") String emailApple);
    @Query("select u from User u where u.emailGoogle = :emailGoogle")
    Optional<User> findUserByEmailGoogle(@Param("emailGoogle") String emailGoogle);

    @Query("select u from User u, AlbumOwner ao where ao.album = :album and ao.user = u")
    List<User> findUsersByAlbum(@Param("album") Album album);

    @Query("select u from User u where u.phoneNum = :phone")
    Optional<User> findUserByPhone(@Param("phone")String phone);
}
