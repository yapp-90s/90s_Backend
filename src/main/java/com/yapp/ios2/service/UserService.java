package com.yapp.ios2.service;

import com.yapp.ios2.config.exception.InvalidValueException;
import com.yapp.ios2.config.exception.UserNotFoundException;
//import com.yapp.ios2.repository.AlbumOwnerRepository;
//import com.yapp.ios2.repository.AlbumRepository;
import com.yapp.ios2.repository.UserRepository;
import com.yapp.ios2.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User join(String emailKakao, String emailApple, String emailGoogle, String name, String phone){

        User newUser = userRepository.findUserByPhone(phone).orElse(
                User.builder()
                        .roles(Collections.singletonList("ROLE_USER"))
                        .phoneNum(phone)
                        .build()
        );

        if(!emailKakao.isBlank()){
            newUser.setEmailKakao(emailKakao);
        }else if(!emailApple.isBlank()){
            newUser.setEmailApple(emailApple);
        }else if(!emailGoogle.isBlank()) {
            newUser.setEmailGoogle(emailGoogle);
        }

        userRepository.save(newUser);
        return newUser;
    }

    public User login(String emailKakao, String emailApple, String emailGoogle, String phoneNum) throws InvalidValueException {
        User user = new User();

        if(!phoneNum.isBlank()){
            user = getUserByPhone(phoneNum);
            if(!emailApple.isBlank()) user.setEmailApple(emailApple);
            if(!emailGoogle.isBlank()) user.setEmailGoogle(emailGoogle);
            if(!emailKakao.isBlank()) user.setEmailKakao(emailKakao);
            userRepository.save(user);
        }else if(!emailKakao.isBlank()){
            user = userRepository.findUserByEmailKakao(emailKakao)
                    .orElseThrow(() -> new UserNotFoundException());
        }else if(!emailApple.isBlank()){
            user = userRepository.findUserByEmailApple(emailApple)
                    .orElseThrow(() -> new UserNotFoundException());
        }else if(!emailGoogle.isBlank()) {
            user = userRepository.findUserByEmailGoogle(emailGoogle)
                    .orElseThrow(() -> new UserNotFoundException());
        }
        return user;
    }

    public void delete(User user){

        userRepository.delete(user);

    }

    public User getUserByPhone(String phoneNum){
        User user = userRepository.findUserByPhone(phoneNum).orElse(new User());
        user.setPhoneNum(phoneNum);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        return userRepository.findById(Long.parseLong(uid))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
