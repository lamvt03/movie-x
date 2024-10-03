package com.filmweb.service;

import static com.filmweb.constant.SessionConstant.CURRENT_USER;
import static com.filmweb.utils.AlertUtils.buildToastErrorMessage;
import static com.filmweb.utils.AlertUtils.buildToastSuccessMessage;
import static com.filmweb.utils.AlertUtils.buildToastWarningMessage;

import com.filmweb.constant.AppConstant;
import com.filmweb.constant.CookieConstant;
import com.filmweb.constant.SessionConstant;
import com.filmweb.dao.OnboardingTokenDao;
import com.filmweb.dao.UserDao;
import com.filmweb.dto.GoogleUser;
import com.filmweb.dto.TopUserDto;
import com.filmweb.dto.UserDto;
import com.filmweb.entity.User;
import com.filmweb.mapper.UserMapper;
import com.filmweb.utils.RandomUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.jbosslog.JBossLog;

@ApplicationScoped
@JBossLog
public class UserService {
    
    public static final String IMAGE_PREFIX = "/views/user/assets/img/avt/avt-";
    public static final String IMAGE_SUFFIX = ".jpg";
    
    @Inject
    private RandomUtils randomUtils;

    @Inject
    private PasswordEncodeService passwordEncodeService;

    @Inject
    private UserDao userDao;

    @Inject
    private UserMapper userMapper;
    
    @Inject
    private OnboardingTokenService onboardingTokenService;
    
    @Inject
    private OtpService otpService;
    
    @Inject
    private OnboardingTokenDao onboardingTokenDao;
    
    @Inject
    private JwtService jwtService;

    public UserDto authenticate(String email, String password) {
        User user = userDao.findByEmail(email);
        return Optional.ofNullable(user)
                .filter(u -> passwordEncodeService.verify(password, u.getPassword()))
                .map(userMapper::toDto)
                .orElse(null);
    }

    public boolean existsByPhone(String phone) {
        User user = userDao.findByPhone(phone);
        return Optional.ofNullable(user).isPresent();
    }

    public boolean existByEmail(String email) {
        User user = userDao.findByEmail(email);
        return Optional.ofNullable(user).isPresent();
    }

    public UserDto findByEmail(String email) {
        return userMapper.toDto(userDao.findByEmail(email));
    }

    public UserDto findById(UUID id) {
        User user = userDao.findById(id);
        return userMapper.toDto(user);

    }

    @Transactional
    public UserDto register(String email, String password, String phone, String fullName) {
        Integer avtId = randomUtils.randomAvtId(AppConstant.AVATAR_TOTAL);
        User user= User.builder()
                .email(email)
                .password(passwordEncodeService.encode(password))
                .phone(phone)
                .fullName(fullName)
                .isAdmin(Boolean.FALSE)
                .isActive(Boolean.FALSE)
                .image(buildUserImageLink(avtId))
                .build();
        
        User createdUser = userDao.create(user);
        
        onboardingTokenService.generateAndSendOnboardingToken(createdUser.getId());
        
        return userMapper.toDto(createdUser);
    }

    public UserDto register(GoogleUser user) {
        User userEntity = User.builder()
                .email(user.getEmail())
                .fullName(user.getName())
                .isAdmin(Boolean.FALSE)
                .isActive(Boolean.TRUE)
                .image(user.getId())
                .build();
        return userMapper.toDto(
                userDao.create(userEntity)
        );
    }

    public UserDto verify(UUID id) {
        User user = userDao.findById(id);
        user.setIsActive(Boolean.TRUE);
        return userMapper.toDto(userDao.update(user));
    }

    public UserDto changePassword(String email, String password) {
        User user = userDao.findByEmail(email);
        if(user != null){
            user.setPassword(passwordEncodeService.encode(password));
            return userMapper.toDto(userDao.update(user));
        }
        return null;
    }

    public UserDto editProfile(String email, String fullName, String phone) {
        User user = userDao.findByEmail(email);
        if (user != null){
            user.setFullName(fullName);
            user.setPhone(phone);
            return userMapper.toDto(userDao.update(user));
        }
        return null;
    }

    public boolean comparePassword(String email, String oldPassword) {
        User user = userDao.findByEmail(email);
        return passwordEncodeService.verify(oldPassword, user.getPassword());
    }

    public List<UserDto> findAll() {
        List<User> users = userDao.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    public List<UserDto> findAll(int page, int limit) {
        List<User> users = userDao.findAll(page, limit);
        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    public List<TopUserDto> findTopUsers(int page, int limit) {
        return userDao.findTopUsersAndTotal(page, limit).stream()
            .map(item -> {
                TopUserDto topUserDto = userMapper.toTopUserDto((User) item[0]);
                topUserDto.setTotal((Long) item[1]);
                return topUserDto;
                })
            .toList();
    }
    
    public String handleVerifyOnboardingToken(String token, HttpSession session) {
        var onboardingToken = onboardingTokenDao.findByToken(token);
        
        if (onboardingToken.getUser().getEmailVerifiedAt() != null) {
            session.setAttribute("alreadyVerified", true);
            return "redirect:login";
        }
        
        var now = LocalDateTime.now();
        var user = onboardingToken.getUser();
        
        if (onboardingToken.getExpiredAt().isAfter(now)) {
            user.setEmailVerifiedAt(now);
            userDao.update(user);
            
            session.setAttribute("email", user.getEmail());
            return "redirect:verify/success";
        }
        
        session.setAttribute(SessionConstant.VERIFIED_EMAIL, user.getEmail());
        return "redirect:verify/error";
    }
    
    @Transactional
    public String handleLogin(HttpSession session, HttpServletResponse response, String email, String password) {
        var userDto = authenticate(email, password);
        
        if (userDto == null) {
            buildToastErrorMessage(session, "Tên đăng nhập hoặc mật khẩu không đúng");
            return "user/login.jsp";
        }
        
        if (!userDto.getIsActive()) {
            buildToastWarningMessage(session, "Vui lòng xác thực email trước khi đăng nhập");
            return "user/login.jsp";
        }
        
        if (!userDto.getIsAdmin()) {
            session.setAttribute(CURRENT_USER, userDto);
            
            String rememberToken = jwtService.generateRememberToken(userDto);
            Cookie loginCookie = new Cookie(CookieConstant.REMEMBER_TOKEN, rememberToken);
            loginCookie.setMaxAge(CookieConstant.LOGIN_DURATION);
            response.addCookie(loginCookie);
            
            String prevUrl = session.getAttribute(SessionConstant.PREV_PAGE_URL).toString();
            
            buildToastSuccessMessage(session, "Đăng nhập thành công");
            return "redirect:" + prevUrl;
        }
        
        return null;
    }
    
    public String handleForgotPassword(String email, HttpSession session) {
        UserDto userDto = findByEmail(email);
        
        if (userDto == null) {
            session.setAttribute("existEmail", true);
            return "redirect:password/forgot";
        }
        
        if (userDto.getIsActive()) {
            otpService.generateAndSendOtpCode(userDto);
            session.setAttribute("email", userDto.getEmail());
            return "redirect:otp/enter";
        }
        
        session.setAttribute("userFalse", true);
        return "redirect:password/forgot";
    }
    
    private String buildUserImageLink(int avtId) {
        return IMAGE_PREFIX + avtId + IMAGE_SUFFIX;
    }
}
