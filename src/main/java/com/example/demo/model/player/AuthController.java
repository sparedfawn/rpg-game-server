package com.example.demo.model.player;

import com.example.demo.configuration.security.PlayerDetails;
import com.example.demo.configuration.security.jwt.JwtUtils;
import com.example.demo.common.auth.refreshToken.RefreshToken;
import com.example.demo.common.auth.refreshToken.RefreshTokenService;
import com.example.demo.common.auth.refreshToken.TokenRefreshException;
import com.example.demo.common.auth.request.LoginRequest;
import com.example.demo.common.auth.request.SignupRequest;
import com.example.demo.common.auth.request.TokenRefreshRequest;
import com.example.demo.common.auth.response.JwtResponse;
import com.example.demo.common.auth.response.MessageResponse;
import com.example.demo.common.auth.response.TokenRefreshResponse;
import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final PlayerService playerService;

  private final PasswordEncoder encoder;

  private final JwtUtils jwtUtils;

  private final RefreshTokenService refreshTokenService;

  @PostMapping("/sign-in")
  @Operation(summary = "Login to the site",
      description = "Authentication process", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    PlayerDetails playerDetails = (PlayerDetails) authentication.getPrincipal();

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(playerDetails.getId());

    return ResponseEntity.ok(new JwtResponse(jwt,
        refreshToken.getToken(),
        playerDetails.getId(),
        playerDetails.getUsername(),
        playerDetails.getEmail()));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();

    return refreshTokenService.findByToken(requestRefreshToken)
        .map(refreshTokenService::verifyExpiration)
        .map(RefreshToken::getPlayer)
        .map(user -> {
          String token = jwtUtils.generateTokenFromUsername(user.getUsername());
          return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        })
        .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
            "Refresh token is not in database!"));
  }

  @PostMapping("/sign-up")
  @Operation(summary = "Register to the site",
      description = "Registration process", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (playerService.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (playerService.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new player's account
    Player player = new Player(signUpRequest.getUsername(),
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

    playerService.savePlayer(player);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }
}