package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_USER_EMAIL(false, 2001, "이메일을 입력해주세요"),
    EMPTY_USER_PASSWD(false, 2002, "비밀번호를 입력해주세요"),
    INVALID_USER_EMAIL(false, 2003, "이메일 형식을 확인해주세요"),
    INVALID_USER_PASSWD(false, 2004, "비밀번호 형식이 옳바르지 않습니다.(영문, 숫자를 포함하고 8자리 이상이어야 합니다.)"),
    EMPTY_USER_IDX(false, 2005, "유저 인덱스 값을 입력해주세요"),
    DELETED_TOKEN(false, 2006, "삭제된 토큰으로, 재 로그인 해야합니다."),
    INVALID_USER_JWT(false,2007,"권한이 없는 유저의 접근입니다."),
    EMPTY_JWT(false, 2008, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2009, "유효하지 않은 JWT입니다."),
    EMPTY_SEARCH_IDX(false, 2010, "검색어 인덱스 값을 입력해주세요"),
    EMPTY_PRODUCT_ARTICLE_IDX(false, 2011, "상품 게시글 인덱스 값을 입력해주세요"),
    EMPTY_OPTION_ONE_IDX(false, 2012, "상품의 옵션1을 입력해주세요"),
    EMPTY_PRODUCT_NUM(false, 2013, "상품의 숫자를 입력해주세요"),
    EMPTY_BUY_PRODUCT_IDX(false, 2014, "구매 상품 인덱스를 입력해주세요"),
    EMPTY_ARTICLE_IDX(false, 2015, "게시글 인덱스 값을 입력해주세요"),

    // [0]
    EMPTY_EMAIL(false, 2016, "이메일을 입력해주세요"),
    EMPTY_AUTH_CODE_IDX(false, 2041, "인증 코드 인덱스를 입력해주세요"),
    EMPTY_CODE(false, 2017, "인증코드를 입력해주세요"),
    // [1]
    EMPTY_USER_NICKNAME(false, 2018, "별명을 입력해주세요"),
    // [2]
    INVALID_PRODUCT_CATEGORY(false, 2019, "카테고리 입력을 확인해주세요"),
    // [13]
    EMPTY_SEARCH_KEYWORD(false, 2020, "검색어를 입력해주세요."),
    // [20]
    EMPTY_BASKET_IDX(false, 2021, "장바구니 인덱스를 입력해주세요"),
    // [21]
    EMPTY_ORDER_NAME(false, 2022, "주문자 이름을 입력해주세요"),
    EMPTY_ORDER_EMAIL(false, 2023, "주문자 이메일을 입력해주세요"),
    EMPTY_ORDER_PHONE(false, 2024, "주문자 핸드폰을 입력해주세요"),
    EMPTY_DELIVER_NAME(false, 2025, "피배송자 이름을 입력해주세요"),
    EMPTY_DELIVER_PHONE(false, 2026, "피배송자 핸드폰을 입력해주세요"),
    EMPTY_DELIVER_ADDRESS(false, 2027, "피배송자 주소를 입력해주세요"),
    EMPTY_ARTICLE_MEDIA_IDX(false, 2028, "게시글 안의 미디어 인덱스 값을 입력해주세요"),
    // [28]
    EMPTY_CATEGORY(false, 2029, "카테고리 값을 입력해주세요"),
    // [30]
    CHOOSE_RIGHT_OPTION(false, 2030, "3개의 인덱스 중 하나만을 입력하여야 합니다."),
    // [41]
    EMPTY_KIND_OF_ARTICLE(false, 2031, "게시글의 종류(PHOTO, PHOTOS, VIDEO)를 입력해주세요"),
    INVALID_SIZE(false, 2032, "평수의 입력이 잘못되었습니다."),
    INVALID_STYLE(false, 2033, "스타일의 입력이 잘못되었습니다."),
    INVALID_HOUSING_TYPE(false, 2034, "스타일의 입력이 잘못되었습니다."),
    INVALID_KINDS_OF_ARTICLE(false, 2035, "게시글 종류의 입력이 잘못되었습니다."),
    // [42]
    EMPTY_MEDIA_URL(false, 2036, "미디어 URL을 입력해주세요"),
    EMPTY_SPACE_TYPE(false, 2037, "공간에 대한 타입을 입력해주세요"),
    INVALID_SPACE_TYPE(false, 2038, "공간에 대한 타입을 확인해주세요"),
    // [43]
    INVALID_XAXIS(false, 2039, "상품태그의 x좌표를 확인해주세요"),
    INVALID_YAXIS(false, 2040, "상품태그의 y좌표를 확인해주세요"),
    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3001, "값을 불러오는데 실패하였습니다."),
    USER_IDX_NOT_EXISTS(false, 3002, "존재하지 않는 유저 인덱스 값입니다."),
    DUPLICATED_USER_NICKNAME(false, 3003, "중복되는 닉네임 입니다."),
    SEARCH_IDX_NOT_EXISTS(false, 3004, "존재하지 않는 검색어 인덱스 값입니다."),
    PRODUCT_ARTICLE_IDX_NOT_EXISTS(false, 3005, "존재하지 않는 상품 게시글 인덱스 값입니다."),
    OPTION_ONE_IDX_NOT_EXISTS(false, 3006, "존재하지 않는 상품 옵션1 인덱스 입니다."),
    OPTION_TWO_IDX_NOT_EXISTS(false, 3007, "존재하지 않는 상품 옵션2 인덱스 입니다."),
    OPTION_EXTRA_IDX_NOT_EXISTS(false, 3008, "존재하지 않는 상품 추가옵션 인덱스 입니다."),
    ARTICLE_IDX_NOT_EXISTS(false, 3009, "존재하지 않는 커뮤니티 게시글 인덱스입니다."),

    // [0]
    CODE_AUTH_IDX_NOT_EXISTS(false, 3018, "존재하지 않는 인증코디 인덱스입니다."),
    // [1]
    DUPLICATED_USER_EMAIL(false, 3010, "중복되는 이메일 입니다."),
    // [2]
    USER_EMAIL_NOT_EXISTS(false, 3011, "회원가입하지 않은 이메일입니다."),
    FAILED_TO_LOGIN_BY_PASSWD(false, 3012, "비밀번호가 옳바르지 않습니다."),
    // [20]
    BASKET_IDX_NOT_EXISTS(false, 3013, "존재하지 않는 장바구니 인덱스 입니다."),
    // [21]
    BUY_PRODUCT_IDX_NOT_EXISTS(false, 3014, "존재하지 않는 상품 구매 인덱스 입니다."),
    // [30]
    ALREADY_SCRAPED_PRODUCT(false, 3015, "이미 스크랩된 상품 게시글 인덱스입니다."),
    // [39]
    ARTICLE_MEDIA_IDX_NOT_EXISTS(false, 30016, "존재하지 않는 게시글 안의 미디어 인덱스입니다."),
    // [44]
    ARTICLE_IS_NOT_USERS(false, 3017, "해당 게시글이, 유저가 생성한 것이 아닙니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, 4002, "비밀번호 암호화에 실패하였습니다."),
    // [2]
    PASSWORD_DECRYPTION_ERROR(false, 4003, "비밀번호 복호화에 실패하였습니다."),
    // [5]
    MODIFY_FAIL_USERS_PASSWD(false, 4004, "유저 비밀번호 변경에 실패하였습니다."),
    // [6]
    MODIFY_FAIL_USERS_PROFILE(false, 4005, "유저 프로필 변경에 실패하였습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
