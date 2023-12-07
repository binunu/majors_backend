package com.binunu.majors.membership.controller;

import com.binunu.majors.membership.dto.MemberDto;
import com.binunu.majors.membership.service.MemberService;
import com.binunu.majors.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/email/exists") //이메일인증
    public ResponseEntity<Boolean> existsEmail(@RequestBody Map<String,String> body) {
        try {
            String email = body.get("email");
            MemberDto member = memberService.getMemberByEmail(email);
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
            MemberDto member = memberService.getMemberByNickname(nickname);
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
    public ResponseEntity<Boolean> join(@RequestBody MemberDto memberDto, BindingResult bindingResult) {
        try {
            memberService.join(memberDto);
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
    public ResponseEntity<MemberDto> getMemberInfo(){
        try{
            String email = JwtUtil.getCurrentMemberEmail();
            MemberDto memberDto = memberService.getMemberByEmail(email);
            return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<MemberDto>(HttpStatus.BAD_REQUEST);
        }
    }


}
