package com.tuan.hotelbooking.common.constant;

import java.time.Duration;
import java.util.Map;

public class Constant {
    // Regex patterns
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMEZONE_VIETNAM = "Asia/Ho_Chi_Minh";
    // User authentication patterns
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[.@#$%^&+=])(?=\\S+$).{8,20}$";
    public static final String OTP_PATTERN = "^\\d{6}$";
    // Profile constants
    public static final String PROFILE_FULLNAME_PATTERN = "^[\\p{L} ]+$";
    public static final int PROFILE_AVATAR_MAX_SIZE = 2 * 1024 * 1024; // 2MB
    // User constants
    public static final String FULLNAME_PATTERN = "^[\\p{L} ]+$";
    public static final int AVATAR_MAX_SIZE = 2 * 1024 * 1024; // 2MB
    // Authentication constants
    public static final int LOCK_DURATION_MINUTES = 30;
    public static final int MAX_VERIFY_OTP_TIMES = 5;
    public static final int MAX_RESEND_OTP_ATTEMPTS = 5;
    public static final int OTP_EXPIRATION_MINUTES = 5;
    public static final int MAX_VERIFY_LOGIN_TIMES = 5;
}