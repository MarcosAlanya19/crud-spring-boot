package com.example.forge.auth;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class TokenJwtConfig {
  // public final static String SECRET_KEY = "algun_token_con_palabra_secreta";
  public final static Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  public final static String PREFIX_TOKEN = "Bearer ";
  public final static String HEADER_AUTHORIZATION = "Authorization";
  public final static String CONTENT_TYPE_JSON = "application/json";
}
