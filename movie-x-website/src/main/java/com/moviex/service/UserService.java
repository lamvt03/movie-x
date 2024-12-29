package com.moviex.service;

import static com.moviex.constant.SessionConstant.CURRENT_USER;
import static com.moviex.domain.user.UserRegistrationType.GOOGLE;
import static com.moviex.domain.user.UserRegistrationType.INTERNAL;
import static com.moviex.utils.AlertUtils.buildDialogErrorMessage;
import static com.moviex.utils.AlertUtils.buildDialogSuccessMessage;
import static com.moviex.utils.AlertUtils.buildToastErrorMessage;
import static com.moviex.utils.AlertUtils.buildToastSuccessMessage;
import static com.moviex.utils.AlertUtils.buildToastWarningMessage;
import static java.lang.Boolean.FALSE;

import com.moviex.constant.AppConstant;
import com.moviex.constant.CookieConstant;
import com.moviex.constant.SessionConstant;
import com.moviex.dao.OnboardingTokenDao;
import com.moviex.dao.UserBalanceTransactionDao;
import com.moviex.dao.UserDao;
import com.moviex.dao.VideoDao;
import com.moviex.domain.user.InternalRegistrationPayload;
import com.moviex.domain.user.UserRegistrationType;
import com.moviex.domain.user.UserTransactionType;
import com.moviex.dto.GoogleUser;
import com.moviex.dto.TopUserDto;
import com.moviex.dto.UserDto;
import com.moviex.entity.User;
import com.moviex.entity.UserBalanceTransaction;
import com.moviex.mapper.UserMapper;
import com.moviex.mapper.VideoMapper;
import com.moviex.utils.RandomUtils;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Models;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Arrays;
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
    
    @Inject
    private UserBalanceTransactionDao userBalanceTransactionDao;
    
    @Inject
    private VideoDao videoDao;
    
    @Inject
    private UserVideoPurchaseService userVideoPurchaseService;
    
    @Inject
    private NotificationService notificationService;
    
    @Inject
    private VideoMapper videoMapper;
    
    public UserDto authenticate(String email, String password) {
        User user = userDao.findByEmail(email);
        return Optional.ofNullable(user)
                .filter(u -> passwordEncodeService.verify(password, u.getPassword()))
                .map(userMapper::toDto)
                .orElse(null);
    }

    public UserDto findByEmail(String email) {
        return userMapper.toDto(userDao.findByEmail(email));
    }
    
    public UserDto findByEmailAndRegistrationType(String email, UserRegistrationType registrationType) {
        return userMapper.toDto(userDao.findByEmailAndRegistrationType(email, registrationType));
    }

    public UserDto findById(UUID id) {
        User user = userDao.findById(id);
        return userMapper.toDto(user);

    }
    
    public String handleInternalRegistration(HttpSession session, InternalRegistrationPayload payload) {
        if (userDao.existingByEmail(payload.getEmail())) {
            buildDialogErrorMessage(session, "Đăng ký thất bại", "Địa chỉ email này đã được đăng ký bởi một tài khoản khác");
            return "redirect:register";
        }
        
        if (userDao.existingByPhone(payload.getPhone())) {
            buildDialogErrorMessage(session, "Đăng ký thất bại", "Số điện thoại này đã được đăng ký bởi một tài khoản khác");
            return "redirect:register";
        }
        
        register(payload);
        buildDialogSuccessMessage(session, "Thành công", "Thông báo xác minh đã được gửi đến địa chỉ email của bạn.");
        return "redirect:login";
    }
    
    private UserDto register(InternalRegistrationPayload payload) {
        Integer avtId = randomUtils.randomAvtId(AppConstant.AVATAR_TOTAL);
        User user= User.builder()
                .email(payload.getEmail())
                .password(passwordEncodeService.encode(payload.getPassword()))
                .phone(payload.getPhone())
                .fullName(payload.getFullName())
                .isAdmin(FALSE)
                .registrationType(INTERNAL)
                .image(buildUserImageLink(avtId))
                .totalBalanceAmount(0L)
                .remainingBalanceAmount(0L)
                .build();
        
        User createdUser = userDao.create(user);
        
        onboardingTokenService.generateAndSendOnboardingToken(createdUser.getId());
        
        return userMapper.toDto(createdUser);
    }

    public UserDto register(GoogleUser user) {
        User userEntity = User.builder()
                .email(user.getEmail())
                .fullName(user.getName())
                .isAdmin(FALSE)
                .registrationType(GOOGLE)
                .emailVerifiedAt(LocalDateTime.now())
                .image(user.getPicture())
                .totalBalanceAmount(0L)
                .remainingBalanceAmount(0L)
                .build();
        return userMapper.toDto(
                userDao.create(userEntity)
        );
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
    
    public List<UserDto> findTopPaymentUsers(int page, int limit) {
        return userDao.findAllOrderByTotalBalanceAmountDesc(page, limit)
            .stream()
            .map(userMapper::toDto)
            .toList();
    }
    
    public String handleChangePassword(
        HttpSession session,
        HttpServletRequest request,
        HttpServletResponse response,
        Models models,
        UserDto userDto,
        String oldPassword,
        String newPassword) {
        
        if (!comparePassword(userDto.getEmail(), oldPassword)) {
            models.put("email", userDto.getEmail());
            models.put("phone", userDto.getPhone());
            models.put("fullName", userDto.getFullName());
            
            buildToastErrorMessage(session, "Mật khẩu cũ không chính xác");
            return "user/change-password.jsp";
        }
        
        var user = userDao.findByEmail(userDto.getEmail());
        user.setPassword(passwordEncodeService.encode(newPassword));
        var userUpdated = userDao.update(user);

        if (userUpdated != null) {
            logoutUser(session, request, response);
            
            buildDialogSuccessMessage(session, "Thành Công", "Thay đổi mật khẩu thành công");
            return "redirect:login";
        }
        
        return null;
    }
    
    public String handleDeleteMyAccount(
        HttpSession session,
        HttpServletRequest request,
        HttpServletResponse response,
        UserDto user) {
        var u = userDao.findById(user.getId());
        u.setDeletedAt(LocalDateTime.now());
        
        userDao.update(u);
        logoutUser(session, request, response);
        
        buildToastSuccessMessage(session, "Tài khoản của bạn đã bị xoá khỏi hệ thống");
        return "redirect:login";
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
    
    public String handleLogin(HttpSession session, HttpServletResponse response, String email, String password) {
        var userDto = authenticate(email, password);
        
        if (userDto == null) {
            buildToastErrorMessage(session, "Tên đăng nhập hoặc mật khẩu không đúng");
            return "user/login.jsp";
        }
        
        if (userDto.getEmailVerifiedAt() == null) {
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
            buildDialogErrorMessage(session, "Thất bại", "Email không tồn tại trong cơ sở dữ liệu");
            return "redirect:password/forgot";
        }
        
        if (userDto.getIsActive()) {
            otpService.generateAndSendOtpCode(userDto);
            session.setAttribute("email", userDto.getEmail());
            return "redirect:otp/enter";
        }
        
        // Account not active
        buildDialogErrorMessage(session, "Thất bại", "Tài khoản với email này chưa được kích hoạt");
        return "redirect:password/forgot";
    }
    
    public String handleResendVerificationEmail(HttpSession session) {
        String verifiedEmail = session.getAttribute(SessionConstant.VERIFIED_EMAIL).toString();
        
        UserDto userDto = findByEmail(verifiedEmail);
        
        onboardingTokenService.generateAndSendOnboardingToken(userDto.getId());
        
        session.removeAttribute(SessionConstant.VERIFIED_EMAIL);
        return "redirect:verify/notify";
    }
    
    public void logoutUser(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        session.removeAttribute(CURRENT_USER);
        
        if (request.getCookies() == null) {
            return;
        }
        
        Arrays.stream(request.getCookies())
            .filter(cookie -> cookie.getName().equals(CookieConstant.REMEMBER_TOKEN))
            .filter(cookie -> cookie.getMaxAge() > 0)
            .findFirst()
            .ifPresent(cookie -> {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            });
    }
    
    public void topUpUserBalance(UUID id, Long amount) {
        var user = userDao.findById(id);
        
        user.setTotalBalanceAmount(user.getTotalBalanceAmount() + amount);
        user.setRemainingBalanceAmount(user.getRemainingBalanceAmount() + amount);
        
        userDao.update(user);
    }
    
    public void createNewUserBalanceTransaction(UUID userId, Long amount, UserTransactionType transactionType) {
        var user = userDao.findById(userId);
        
        userBalanceTransactionDao.create(
            UserBalanceTransaction.builder()
                .amount(amount)
                .transactionType(transactionType)
                .user(user)
                .build()
        );
    }
    
    public String handlePurchaseVideo(HttpSession session, UUID userId, String videoSlug) {
        var user = userDao.findById(userId);
        var video = videoDao.findBySlug(videoSlug);
        
        if (userVideoPurchaseService.checkUserVideoPurchase(user.getId(), video.getId())) {
            buildToastWarningMessage(session, "Bạn đã mua phim này rồi");
            return "redirect:v/detail/" + videoSlug;
        }
        
        if (user.getRemainingBalanceAmount() < video.getPrice()) {
            buildToastErrorMessage(session, "Số dư trong tài khoản không đủ, vui lòng nạp thêm");
            return "redirect:v/detail/" + videoSlug;
        }
        
        userVideoPurchaseService.createUserVideoPurchase(user, video, video.getPrice());
        
        user.setRemainingBalanceAmount(user.getRemainingBalanceAmount() - video.getPrice());
        userDao.update(user);
        
        userBalanceTransactionDao.create(
            UserBalanceTransaction.builder()
                .amount(-1 * video.getPrice())
                .transactionType(UserTransactionType.MOVIE_PURCHASE)
                .user(user)
                .build()
        );
        
        notificationService.sendVideoPurchasedEmail(userMapper.toDto(user), videoMapper.toDto(video));
        
        buildDialogSuccessMessage(session, "Thông báo", "Mua phim thành công");
        return "redirect:v/detail/" + videoSlug;
    }
    
    private String buildUserImageLink(int avtId) {
        return IMAGE_PREFIX + avtId + IMAGE_SUFFIX;
    }
}
