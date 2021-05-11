package de.dhbw.jwtdemo.auth;

import de.dhbw.jwtdemo.auth.dto.LoginRequest;
import de.dhbw.jwtdemo.auth.dto.LoginResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

@Api(tags = "auth")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    /**
     * This method has no actual implementation. It only exists to get the spring security login endpoint
     * documented by swagger
     */
    @ApiOperation(value = "Login", notes = "Login with the given credentials.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Login successful", response = LoginResponse.class),
            @ApiResponse(code = 403, message = "Invalid credentials")
    })
    @PostMapping
    public void login(@RequestBody() LoginRequest request) {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }
}