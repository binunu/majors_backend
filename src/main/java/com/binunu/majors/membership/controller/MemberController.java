package com.binunu.majors.membership.controller;

import com.binunu.majors.contents.dto.MajorDto;
import com.binunu.majors.membership.dto.MemberDto;
import com.binunu.majors.membership.repository.MemberRepository;
import com.binunu.majors.membership.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.PasswordView;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            Optional<MemberDto> member = memberService.getMemberByEmail(email);
            if (member.isEmpty()) { //중복확인 통과
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
            Optional<MemberDto> member = memberService.getMemberByNickname(nickname);
            if (member.isEmpty()) { //중복확인 통과
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<Boolean>(false, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/join")
    public ResponseEntity<Boolean> existNickname(@RequestBody MemberDto memberDto) {
        System.out.println(memberDto.toString());
        try {
            //비밀번호암호화로직추가
            memberService.join(memberDto);
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Boolean>(HttpStatus.BAD_REQUEST);
        }
    }

}
