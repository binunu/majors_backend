package com.binunu.majors.membership.controller;

import com.binunu.majors.membership.dto.Member;
import com.binunu.majors.membership.dto.MemberInfoDto;
import com.binunu.majors.membership.service.MemberService;
import com.binunu.majors.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @PostMapping("/email/exists") //이메일인증
    public ResponseEntity<Boolean> existsEmail(@RequestBody Map<String,String> body) {
        try {
            String email = body.get("email");
            Member member = memberService.getMemberByEmail(email);
            if (member==null) { //중복확인 통과
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/nickname/exist") //닉네임인증
    public ResponseEntity<Boolean> existNickname(@RequestParam("nickname") String nickname) {
        try {
            Member member = memberService.getMemberByNickname(nickname);
            if (member==null) { //중복확인 통과
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@RequestBody Member member, BindingResult bindingResult) {
        try {
            memberService.join(member);
            if (bindingResult.hasErrors()) {
                String errorMessage = bindingResult.getFieldErrors().stream()
                        .map(error -> error.getDefaultMessage())
                        .reduce("", (accumulator, errorMessageItem) -> accumulator + " " + errorMessageItem);
                throw new Exception(errorMessage);
            }
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> body){
        try{
            String email = body.get("email");
            String password = body.get("password");
            String token = memberService.login(email,password);
            return new ResponseEntity<String>(token, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    //멤버정보받아오기
    @GetMapping("/info")
    public ResponseEntity<Member> getMemberInfo(){
        try{
            String email = JwtUtil.getCurrentMemberEmail();
            Member member = memberService.getMemberByEmail(email);
            return new ResponseEntity<Member>(member, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<Member>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/info/simple")
    public ResponseEntity<MemberInfoDto> getMemberEmail(){
        try{
            Member member = memberService.getCurrentMember();
            MemberInfoDto memberInfo = modelMapper.map(member,MemberInfoDto.class);
            return new ResponseEntity<MemberInfoDto>(memberInfo, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<MemberInfoDto>(HttpStatus.BAD_REQUEST);
        }
    }

}
