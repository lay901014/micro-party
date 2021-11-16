package edu.sjtu.party.utils;

import javax.servlet.http.HttpServletResponse;


public enum APIErrors {

    EXCEPTION(-2, HttpServletResponse.SC_INTERNAL_SERVER_ERROR),
    BAD_PARAMETERS(10001, HttpServletResponse.SC_BAD_REQUEST),
    AUTHENTICATION_FAILED(10002, HttpServletResponse.SC_FORBIDDEN),
    UNAUTHORIZED(10003, HttpServletResponse.SC_UNAUTHORIZED),

    BRANCH_ADMIN_EXISTED(100),
    BRANCH_ADMIN_NOT_EXISTED(101),

    BRANCH_DUTY_EXISTED(200),
    BRANCH_DUTY_NOT_EXISTED(201),

    PERSON_NOT_MEMBER(300),
    PERSON_NOT_EXISTED(301),

    BRANCH_CODE_GENERATE_FAILED(400),
    BRANCH_NAME_DUPLICATED(401),
    BRANCH_NAME_INVALID(402),
    BRANCH_TYPE_INVALID(403),
    BRANCH_CANNOT_CREATE_ROOT(404),
    BRANCH_CANNOT_MOVE_ROOT(405),
    BRANCH_CANNOT_MOVE_TO_CHILD(406),
    BRANCH_NOT_EXISTED(407),
    BRANCH_PARENT_NOT_EXISTED(408),

    UPLOAD_FILE_FAILED(500),
    UPLOAD_FILE_EXTENSION_NOT_ALLOWED(501),
    UPLOAD_FILE_NAME_EMPTY(502),

    MEMBER_NOT_EXISTED(600),
    MEMBER_ALREADY_EXISTED(601),
    USER_NOT_EXISTED(602),

    USER_NOT_MEMBER(700)
    ;

    int errno;
    int sc = 0;

    APIErrors(int errno) {
        this.errno = errno;
    }

    APIErrors(int errno, int sc) {
        this.errno = errno;
        this.sc = sc;
    }

    @Override
    public String toString(){
        return LanguagePack.get(this);
    }

    public int getHTTPStatusCode() {
        return sc == 0 ? HttpServletResponse.SC_INTERNAL_SERVER_ERROR : sc;
    }
}
