# SpringSecurityDemo
This project will show how to use spring security in a project.

##Authentication using credentials
```aidl
commit-id for reference: 77c157f304d3dcc77e7ff8afe7d5a1d218d655e8
```
[Link to commit](https://github.com/snehalwagh2121/SpringSecurityDemo/commit/77c157f304d3dcc77e7ff8afe7d5a1d218d655e8)

1. **ProjectConfig** class is the class which extends WebSecurityConfigurer adapter provided by spring security.
It contains all the configurations required for the project. 

2. *configure(AuthenticationManagerBuilder auth)* will specify the authentication provider details. If we don't have any authentication provider, we can specify the UserDetials and password encoder details. Also, instead of this we can InMemoryUserDetailsManager and provide usernames and passwords in memory instead of fetching from DB.
<br>
   e.g
   <br>
   When we have *custom AuthenticationProvider*
   ```java
   public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider)
                .authenticationProvider(otpAuthenticationProvider);
    }
   ```
   When we dont want to use any *AuthenticationProvider*
   ```java 
   public void configure(AuthenticationManagerBuilder auth) {
       DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       authProvider.setUserDetailsService(userDetailsService());
       authProvider.setPasswordEncoder(passwordEncoder());
       auth.authenticationProvider(authProvider);
   }
   ```
   When we want to user *inMemoryUserDetailsService*.
    ```java
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
       List<UserDetails> userDetailsList = new ArrayList<>();
	   userDetailsList.add(User.withUsername("employee").password(passwordEncoder().encode("password"))
			.roles("EMPLOYEE", "USER").build());
	   userDetailsList.add(User.withUsername("manager").password(passwordEncoder().encode("password"))
			.roles("MANAGER", "USER").build());
	   auth.userDetailsService(InMemoryUserDetailsManager(userDetailsList));
	}
   ```
3. In *public void configure(HttpSecurity http)* we have specified the authorization roles required for particular endpoints.

4. The *AuthenticationProvider* will contain the logic to authenticate a user.
Steps involved in it are:
   1. loading the User from DB for the username we get from Authentication object.
   2. Matching the password we get in Authentication Object with the one we fetched from DB.
    
##2-FA

[Link to commit](https://github.com/snehalwagh2121/SpringSecurityDemo/commit/4ae56dace18a1429fc650cf8505f058d029aa732)


For 2 factor authentication we have created a filter which will have the logic to check for credentials or OTP authentication and then send it to specific Authentication Provider for authentication.
This filter is specified in the cofigure(HttpSecurity http) method of ProjectConfig class.

<br>
We have created 2 Authentication Providers, one for Username Password authentication and one for Password authentication.

<br>
<br>
The Authentication Provider will be picked based on the tpe of authentication object. We can specify it in the support() provided by the AuthenticationProvider.
<br>
<br>
We have to include these providers as Authentication Provider while creating the AuthenticationManager bean.

```java
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList((AuthenticationProvider) otpAuthenticationProvider,
        (AuthenticationProvider) authProvider));
    }
```

Also 

``` 
There was an error which took pretty much time, error was coming in the AuthenticationManager. It was not getting initilized in the filter even when autowired and bean definition provided. 
The solution was that we need to embed it in the filter in constructor:
```
```java
    public OtpFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
```