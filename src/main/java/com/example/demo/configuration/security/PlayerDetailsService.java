package com.example.demo.configuration.security;

import com.example.demo.model.player.Player;
import com.example.demo.model.player.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Player user = playerRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found with username: " + username));

        log.info("method: loadUserByUsername(): finding user details based on username");
        return PlayerDetails.build(user);
    }

    public PlayerDetails getCurrentlyLoggedPlayer() {

        PlayerDetails playerDetails = (PlayerDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("method: getCurrentlyLoggedPlayer(): currently logged player is " + playerDetails.getId());

        return playerDetails;
    }

}