package com.oneovern.domain.member.dto;

import jakarta.validation.constraints.*;

public class MemberReqDto {

    //회원가입
    public record Join(
            @NotBlank(message = "이메일은 필수 입력 항목입니다.")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            @Size(max = 100, message = "이메일은 최대 100자까지 입력 가능합니다.")
            String email,

            @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
            @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하로 입력해주세요.")
            @Pattern(regexp = "^[가-힣a-zA-Z0-9]*$", message = "닉네임에 특수문자는 사용할 수 없습니다.")
            String nickname,

            @NotNull(message = "이메일은 필수입니다.")
            @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).*$", message = "비밀번호에 영문, 숫자, 특수문자를 최소 하나씩 포함해주세요.")
            @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
            String password
    ){}
}
