package com.example.redis.dto;

import com.example.redis.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

  @Id
  private Long id;

  private String username;

  private String telephone;

  private Integer age;

  @Enumerated(EnumType.STRING)
  private Member.Gender gender;
}
