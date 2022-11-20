package com.example.redis.controller;

import com.example.redis.dto.MemberDTO;
import com.example.redis.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/member/list")
  public List<MemberDTO> getMemberList() {
    return memberService.getMemberList();
  }

  @GetMapping("/member/list/v2")
  public List<MemberDTO> getMemberListByRedisTemplate() {
    return memberService.getMemberListByRedisTemplate();
  }

  @GetMapping("/member")
  public MemberDTO getMember(@RequestParam(name = "id") Long id) {
    return memberService.getMemberByProxy(id);
  }

  @GetMapping("/member/redis")
  public MemberDTO getMemberFromRedis(@RequestParam(name = "id") Long id) {
    return memberService.getMemberFromRedis(id);
  }

  @GetMapping("/member/keys")
  public Set<String> getKeys(){
    return memberService.showAllKeysByScanning();
  }

  /**
   * 여기서 문제는 보통 운영상에서 Redis 설정을 할때 allEntries 같은 키워드를 쓰게되면 Redis 설정에서 해당 Command 를 실행하게 되는데
   * 실제 운영을 할 때는 allEntries에 해당하는 Command는 막아 놓고 있다.
   * 이유는 Redis에 저장된 무수히 많은 Key 와 Data 가 있는데 이중에서 내가 원하는 (simpleData) 를 찾기 위해
   * 전체 key를 full scan 하면서 성능에 문제를 일으킨다고 한다.
   *
   * @param memberDTO
   * @return
   */
//  @CacheEvict(allEntries = true)
  @PostMapping("/member")
  public MemberDTO createMember(@RequestBody MemberDTO memberDTO) {
    return memberService.createMember(memberDTO);
  }

  @CachePut(key = "#id", value = "member", unless = "#result == null", cacheManager = "cacheManager")
  @PutMapping("/member")
  public MemberDTO updateMember(@RequestParam(name = "id") Long id,
                                @RequestBody MemberDTO memberDTO) {
    return memberService.updateMember(id, memberDTO);
  }

  @CacheEvict(key = "#id", value = "member")
  @DeleteMapping("/member")
  public Long deleteMember(@RequestParam(name = "id") Long id) {
    return memberService.deleteMember(id);
  }

  @DeleteMapping("/member/reset")
  public void redisCacheReset(){
    memberService.cacheReset();
  }

  @GetMapping("/member/renewal")
  public List<MemberDTO> redisCacheRenewal(){
    return memberService.cacheRenewal();
  }
}
