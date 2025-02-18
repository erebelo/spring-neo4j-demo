package com.erebelo.springneo4jdemo.controller;

import static com.erebelo.springneo4jdemo.constant.BusinessConstant.USERS_FOLLOW_PATH;
import static com.erebelo.springneo4jdemo.constant.BusinessConstant.USERS_PATH;
import static com.erebelo.springneo4jdemo.constant.BusinessConstant.USERS_UNFOLLOW_PATH;

import com.erebelo.springneo4jdemo.domain.request.UserRequest;
import com.erebelo.springneo4jdemo.domain.response.UserLazyResponse;
import com.erebelo.springneo4jdemo.domain.response.UserResponse;
import com.erebelo.springneo4jdemo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping(USERS_PATH)
@RequiredArgsConstructor
@Tag(name = "Users API")
public class UserController {

    private final UserService service;

    @Operation(summary = "GET Users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserLazyResponse> findAll() {
        log.info("GET {}", USERS_PATH);
        return service.findAll();
    }

    @Operation(summary = "GET User by Id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse findById(@PathVariable String id) {
        log.info("GET {}/{}", USERS_PATH, id);
        return service.findById(id);
    }

    @Operation(summary = "POST Users")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLazyResponse insert(@Valid @RequestBody UserRequest request, HttpServletResponse httpServletResponse) {
        log.info("POST {}", USERS_PATH);
        UserLazyResponse response = service.insert(request);
        httpServletResponse.setHeader(HttpHeaders.LOCATION, ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri().toString());
        return response;
    }

    @Operation(summary = "PUT Users")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLazyResponse update(@PathVariable String id, @Valid @RequestBody UserRequest request) {
        log.info("PUT {}/{}", USERS_PATH, id);
        return service.update(id, request);
    }

    @Operation(summary = "DELETE Users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable String id) {
        log.info("DELETE {}/{}", USERS_PATH, id);
        service.delete(id);
    }

    @Operation(summary = "POST Follow Users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(USERS_FOLLOW_PATH)
    public void followUser(@PathVariable String fromId, @PathVariable String toId) {
        log.info("POST {}{}", USERS_PATH, USERS_FOLLOW_PATH.replace("{fromId}", fromId).replace("{toId}", toId));
        service.followUser(fromId, toId);
    }

    @Operation(summary = "POST Unfollow Users")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(USERS_UNFOLLOW_PATH)
    public void unfollowUser(@PathVariable String fromId, @PathVariable String toId) {
        log.info("DELETE {}{}", USERS_PATH, USERS_UNFOLLOW_PATH.replace("{fromId}", fromId).replace("{toId}", toId));
        service.unfollowUser(fromId, toId);
    }
}
