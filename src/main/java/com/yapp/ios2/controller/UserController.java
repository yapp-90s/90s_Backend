package com.yapp.ios2.controller;

import com.yapp.ios2.dto.*;
//import com.yapp.ios2.service.AlbumService;
//import com.yapp.ios2.service.SnsService;
import com.yapp.ios2.config.JwtProvider;
import com.yapp.ios2.service.UserService;
import com.yapp.ios2.vo.User;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"1. User"})
@RestController
@RequestMapping("/user/*")
public class UserController {

    @Autowired
    UserService userService;
//    @Autowired
//    SnsService snsService;
//    @Autowired
//    AlbumService albumService;

//    KakaoService kakaoService;

    @Autowired
    JwtProvider jwtProvider;


//    @PostMapping(value = "/join")
//    @ResponseBody
//    public ResponseDto.JwtDto join(@RequestBody JoinDto joinInfo) {
//
//        String jwt;
//        User newUser;
//
//        if(!joinInfo.getEmailKakao().isBlank()){
//            newUser = userService.join(joinInfo.getEmailKakao(), null, null, joinInfo.getName(), joinInfo.getPhone());
//        }else if(!joinInfo.getEmailApple().isBlank()){
//            newUser = userService.join(null, joinInfo.getEmailApple(), null, joinInfo.getName(), joinInfo.getPhone());
//        }else{
//            newUser = userService.join(null, null, joinInfo.getEmailGoogle(), joinInfo.getName(), joinInfo.getPhone());
//        }
//
//        jwt = jwtProvider.createToken(newUser.getUid().toString(), newUser.getRoles());
//
//        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
//        jwtDto.setJwt(jwt);
//
//        return jwtDto;
//    }
    @GetMapping(value = "/")
    @ResponseBody
    public ResponseDto.DataDto home(){
        ResponseDto.DataDto dataDto = new ResponseDto.DataDto();
        dataDto.setData("Hello This url is about HOME");
        return dataDto;
    }


    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseDto.JwtDto login(@Valid @RequestBody LoginDto loginDto){


        User user = userService.login(loginDto.getEmailKakao(), loginDto.getEmailApple(), loginDto.getEmailGoogle());

        String jwt = jwtProvider.createToken(user.getUid().toString(), user.getRoles());

        ResponseDto.JwtDto jwtDto = new ResponseDto.JwtDto();
        jwtDto.setJwt(jwt);

        return jwtDto;
    }

//    @ApiOperation(value = "이메일 체크", notes = "" +
//            "이메일이 중복되는지 확인합니다." +
//            "<br>이메일이 중복된다면 true 를" +
//            "<br>이메일이 중복되지 않는다면 false를" +
//            "<br>리턴합니다.")
//    @PostMapping("/checkEmail")
//    @ResponseBody
//    public ResponseDto.BooleanDto duplicatedEmail(@RequestBody DuplicatedEmailDto duplicatedEmailDto){
//
//        ResponseDto.BooleanDto booleanResultDto = new ResponseDto.BooleanDto();
//
//        boolean duplicated = userService.checkEmail(duplicatedEmailDto.getEmail());
//
//        booleanResultDto.setResult(duplicated);
//
//        return booleanResultDto;
//    }

//    @PostMapping("/checkPhoneNum")
//    @ResponseBody
//    public SmsDto.SmsResponseDto sendSms(@RequestBody SmsDto.SmsRequestDto smsRequestDto){
//
//        String num = snsService.send(smsRequestDto.getPhoneNumber());
//        SmsDto.SmsResponseDto smsResponseDto = new SmsDto.SmsResponseDto();
//        smsResponseDto.setNum(num);
//
//        return smsResponseDto;
//
//    }

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
