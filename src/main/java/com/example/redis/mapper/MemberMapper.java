package com.example.redis.mapper;

import com.example.redis.Member;
import com.example.redis.dto.MemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "username", source = "username")
  @Mapping(target = "telephone", source = "telephone")
  @Mapping(target = "age", source = "age")
  @Mapping(target = "gender", source = "gender")
  MemberDTO toDto(Member member);
}