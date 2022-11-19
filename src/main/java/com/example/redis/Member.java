package com.example.redis;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String telephone;

  private Integer age;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  public static Member create(String username, String telephone, Integer age, Gender gender){
    Member member = new Member();
    member.setUsername(username);
    member.setTelephone(telephone);
    member.setAge(age);
    member.setGender(gender);
    return member;
  }

  public Member update(String username, String telephone, Integer age, Gender gender){
    this.setUsername(username);
    this.setTelephone(telephone);
    this.setAge(age);
    this.setGender(gender);
    return this;
  }

  public enum Gender {
    MALE, FEMALE
  }
}
