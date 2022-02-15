package com.yapp.ios2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yapp.ios2.dto.*;
//import com.yapp.ios2.service.AlbumService;
//import com.yapp.ios2.service.SnsService;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.service.SnsService;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Api(tags = {"1. User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/*")
public class UserController {

    private final UserService userService;

    private final SnsService snsService;

    private final JwtProvider jwtProvider;

    @GetMapping(value = "/")
    @ResponseBody
    public IDto home(){
        IDto dataDto = new ResponseDto.DataDto("Hello This url is about HOME");

        return dataDto;
    }

    @GetMapping("/getTesterJwt")
    @ResponseBody
    public String getTester(){

        ObjectMapper json = new ObjectMapper();
        String jsonString = new String();
        try{
            jsonString = json.writerWithDefaultPrettyPrinter().writeValueAsString(userService.getTestersJWT());
        } catch (Exception e){

        }
        return jsonString;
    }


    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseDto.JwtDto login(@Valid @RequestBody LoginDto loginDto){


        User user = userService.login(
                loginDto.getEmailKakao(),
                loginDto.getEmailApple(),
                loginDto.getEmailGoogle(),
                loginDto.getPhoneNum()
        );

        String jwt = jwtProvider.createToke(user);

        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
        jwtDto.setJwt(jwt);

        return jwtDto;
    }

    @PostMapping("/checkPhoneNum")
    @ResponseBody
    public SmsResponseDto sendSms(@RequestBody SmsRequestDto smsRequestDto){

        String num = snsService.send(smsRequestDto.getPhoneNumber());
        SmsResponseDto smsResponseDto = new SmsResponseDto();
        smsResponseDto.setNum(num);

        return smsResponseDto;

    }

//    @ApiOperation(value = "이메일 변경", notes = "" +
//            "이메일을 변경합니다." +
//            "<br>변경할 이메일을 보내주세요." +
//            "<br>JWT 토큰의 정보를 활용해 사용자의 이메일을 변경합니다."
//    )
//    @PostMapping("/updateEmail")
//    @ResponseBody
//    public ResponseDto.JwtDto updateEmail(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserDto.AccountInfo userDto){
//
//        User user = userService.getUserByPhone(userDetails.getUsername());
//        userService.updateEmail(user, userDto.getEmail());
//
//        String jwt = jwtProvider.createToken(user.getUid().toString(), user.getRoles());
//        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
//        jwtDto.setJwt(jwt);
//        return jwtDto;
//    }

//    @ApiOperation(value = "핸드폰번호 변경", notes = "" +
//            "핸드폰 번호를 변경합니다." +
//            "<br>변경할 핸드폰 번호를 보내주세요." +
//            "<br>JWT 토큰의 정보를 활용해 사용자의 핸드폰 번호를 변경합니다."
//    )
//    @PostMapping("/updatePhoneNumber")
//    @ResponseBody
//    public ResponseDto.JwtDto updatePhoneNumber(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserDto.PhoneNum phoneNumDto){
//
//        User user = userService.getUserByEmail(userDetails.getUsername());
//        userService.updatePhoneNumber(user, phoneNumDto.getPhoneNum());
//
//        String jwt = jwtProvider.createToken(user.getUid().toString(), user.getRoles());
//        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
//        jwtDto.setJwt(jwt);
//        return jwtDto;
//    }

//    @GetMapping("/getUserProfile")
//    @ResponseBody
//    public UserDto.UserProfile getUserProile(@AuthenticationPrincipal UserDetails userDetail){
//        User user = userService.getUserByPhone(userDetail.getUsername());
//
//        UserDto.UserProfile userProfile = new UserDto.UserProfile();
//
//        userProfile.setUserInfo(user);
//        userProfile.setAlbumTotalCount(albumService.getAlbumsByUser(user).size());
//
//        return userProfile;
//    }
//
//    @ApiOperation(value = "비밀번호 변경", notes = "" +
//            "비밀번호를 변경합니다." +
//            "<br>변경할 비밀번호를 보내주세요." +
//            "<br>핸드폰 번호를 활용해 사용자의 비밀번호를 변경합니다."
//    )
//    @PostMapping("/updatePassword")
//    @ResponseBody
//    public ResponseDto.JwtDto updatePassword(@RequestBody UserDto.AccountInfo userDto){
//
//        User user = userService.findByPhone(userDto.getPhoneNum());
//        userService.updatePassword(user, userDto.getPassword());
//
//        String jwt = jwtProvider.createToken(user.getUid().toString(), user.getRoles());
//        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
//        jwtDto.setJwt(jwt);
//
//        return jwtDto;
//    }

//    @PostMapping("/findEmail")
//    @ResponseBody
//    public UserDto.AccountInfo findEmail(@RequestBody UserDto.AccountInfo userDto){
//        User user = userService.findByPhone(userDto.getPhoneNum());
//        userDto.setEmail(user.getEmail());
//        userDto.setName(user.getName());
//        return userDto;
//    }

//    @GetMapping("/getDefaultUser")
//    @ResponseBody
//    public ResponseDto.JwtDto getDefaultUser(){
//        User user = userService.getUserByPhone("010-0000-0000");
//        String jwt = jwtProvider.createToken(user.getUid().toString(), user.getRoles());
//        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
//        jwtDto.setJwt(jwt);
//
//        return jwtDto;
//    }
//
//    @GetMapping("/delete")
//    @ResponseBody
//    public void delete(@AuthenticationPrincipal UserDetails userDetails){
//        User user = userService.getUserByPhone(userDetails.getUsername());
//        userService.delete(user);
//    }

}
