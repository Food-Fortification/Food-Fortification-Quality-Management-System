# How to configure security filter chain

This guide provides an overview of configuring security in FFQMS application using Keycloak for authentication. The `IamSecurityConfig` class extends Keycloak’s security adapter and sets up access controls for various endpoints in the application, while also managing session strategies and password encoding.

Let's take an example from the security config class in IAM microservice.The `IamSecurityConfig` class configures web security, allowing or restricting access to specific URLs based on the authentication status. It leverages Keycloak for security and customizes session management and password encoding.

**Key Annotations:**

* `@KeycloakConfiguration`: Custom annotation for enabling Keycloak configuration. It integrates Keycloak into the Spring Security framework.
* `@EnableWebSecurity`: Enables Spring Security’s web security support.
* `@Order(2)`: Specifies the order in which security configurations are applied, allowing for multiple security configurations with different priorities.
* `@EnableGlobalMethodSecurity`: Enables method-level security annotations (`@PreAuthorize`, `@RolesAllowed`, etc.), with support for both JSR-250 annotations and Spring’s `prePostEnabled`.

```java
@KeycloakConfiguration
@EnableWebSecurity
@Order(2)
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class IamSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests()
                // Allow open access to specific endpoints
                .antMatchers("/login").permitAll()
                .antMatchers("/refreshToken").permitAll()
                .antMatchers("/user").permitAll()
                .antMatchers("/user/*").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/user/storage/get-meta-file").permitAll()
                // All other requests require authentication
                .anyRequest().authenticated();
        
        // Disable Cross-Site Request Forgery (CSRF) and enable Cross-Origin Resource Sharing (CORS)
        http.cors().and().csrf().disable();
    }

    // Defines the session authentication strategy to use NullAuthenticatedSessionStrategy
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    // Defines BCrypt as the password encoding mechanism
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**Key Features**

1. **Endpoint Security**:
   * The `HttpSecurity` configuration ensures that certain endpoints, such as `/login`, `/refreshToken`, `/user`, and `/actuator/health`, are accessible without authentication using the `permitAll()` method.
   * All other requests are authenticated using `anyRequest().authenticated()`.
2. **CORS and CSRF**:
   * CORS (Cross-Origin Resource Sharing) is enabled to allow requests from different origins.
   * CSRF (Cross-Site Request Forgery) protection is explicitly disabled using `http.csrf().disable()`. This is common in APIs or stateless applications.
3. **Session Management**:
   * The session management strategy is defined using the `NullAuthenticatedSessionStrategy`, which disables session creation for authentication. This is useful for stateless REST APIs where session management is unnecessary.
4. **Password Encoding**:
   * A `BCryptPasswordEncoder` bean is provided for encoding passwords securely. BCrypt is a widely-used password hashing function that is resistant to brute-force attacks.

**Annotations in Detail**

* **@KeycloakConfiguration**: Custom annotation that combines `@Configuration` and `@EnableKeycloak` to configure Keycloak integration for the application.
* **@EnableWebSecurity**: This annotation activates Spring Security’s web security features, allowing you to define security policies for HTTP requests.
* **@Order(2)**: Specifies the order in which this security configuration is applied. Lower values have higher priority, and in this case, the order is set to 2 to allow the configuration to coexist with other security configurations.
* **@EnableGlobalMethodSecurity**: Enables method-level security annotations like `@RolesAllowed`, `@PreAuthorize`, and `@Secured`. With `jsr250Enabled` set to `true`, JSR-250 annotations such as `@RolesAllowed` are enabled. The `prePostEnabled` setting allows Spring’s `@PreAuthorize` and `@PostAuthorize` annotations.

**Best Practices**

* **Endpoint Security**: Make sure to restrict access to sensitive endpoints by using the `.authenticated()` method for endpoints that require a logged-in user.
* **Password Security**: Always use a strong password encoder like BCrypt for password hashing. Avoid storing plain-text passwords in your database.
* **Stateless Authentication**: Use `NullAuthenticatedSessionStrategy` for APIs or stateless applications where session-based authentication is not needed.
* **CORS and CSRF**: When building APIs or microservices, CORS needs to be enabled to allow cross-origin requests. Disabling CSRF is common for stateless applications that don't maintain user sessions.

**Conclusion**

The security config class is a robust security configuration for a Spring Boot application that integrates with Keycloak for authentication. It manages which endpoints require authentication, disables CSRF for APIs, and uses BCrypt for secure password encoding. This approach is well-suited for modern applications, particularly those using Keycloak as an Identity and Access Management (IAM) solution.
