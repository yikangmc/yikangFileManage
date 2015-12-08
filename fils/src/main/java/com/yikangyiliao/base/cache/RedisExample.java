package com.yikangyiliao.base.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisExample
{

  @Autowired
  private RedisTemplate<String, String> template;

  public void save(String key, String value)
  {
    this.template.opsForList().leftPush(key, value);
  }

  public String get(String key)
  {
    return (String)this.template.opsForValue().get(key);
  }
}