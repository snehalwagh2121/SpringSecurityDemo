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

#Send SMS through mobile using TWILIO

We need to add twilio dependency first. Then add mobile number to the columns of Users. 
Previously we were sending otp in header, now we wil send it to the mobile number.

To send msgs via twilio, we need to create a new account, and get one number. Then since i used a trial account, I got a US based number with the help of which i can send msgs. 
And I have to register each mobile number to which i will be sending the msg.

The code is pretty much simple. For reference follow below commit. I have not added the ProjectConstants file becasue of security, but I have defines the following constants there:
```java
    public static String TWILIO_SID="#######################";
    public static String TWILIO_TOKEN="##############################";
    public static String TWILIO_REGISTERED_NO="##########";
```
[commit id](https://github.com/snehalwagh2121/SpringSecurityDemo/commit/d2755f23e49b5c4ec6c71d9b576b793a7fbe9778)

#Authenticate using JWT token

We need JWT dependency to create the jwt token. 
The JWTUtil will have all the methods required for the authentication.

The JWT token will have following methods:
1. Generate token: This method will generate the JWT token with subject as username, we can set expiry date, creation date. We need a signWith() and provide the hashing algo, i've used HS512. We also need secret so that way our token will be secure I guess. 
2. validate OTP - this will validate user. It will take 2 parameters, 1 will have the token, and other one will be having UserDetails obj of the user.
The username can be extracted from the jwt token, and can be matched with the one provided in the request. 

A new filter JWTFilter will be responsible to call the JwtAuthorizationProvider. Which will call the verifyToken from JWTUtils class.  

[commit id](https://github.com/snehalwagh2121/SpringSecurityDemo/commit/9e14c48569921f1c109b834175ad0ab6b62e4a94)