package com.main.api.controller;

import com.base.common.constant.BusinessMessageCodeConst;
import com.base.common.constant.DefaultValue;
import com.base.common.constant.UserUpdateEnum;
import com.base.common.payload.UserRequest;
import com.base.common.service.UserService;
import com.base.common.util.DateUtil;
import com.base.common.valid.ValidationException;
import com.main.api.payload.DataResponse;
import com.main.api.payload.ResponseData;
import com.main.api.pointcutadvice.annotations.Loggable;
import com.main.api.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
@RestController
@RequestMapping("/api")
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/user/find")
    @Loggable
    public ResponseEntity loadAllUser(
            @RequestHeader(name = "Accept-Language", required = false) String languageCode,
            @RequestParam(name = "limit", defaultValue = DefaultValue.LIMIT,required = false) Integer limit,
            @RequestParam(name = "page", defaultValue = DefaultValue.PAGE, required = false) Integer page,
            @RequestParam(name = "sortBy", defaultValue = DefaultValue.SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortType", defaultValue = DefaultValue.SORT_TYPE_ASC, required = false) String sortType,
            @RequestParam(name = "keyword", required = false) String keyword,
            HttpServletRequest request) {
        String path = Util.getFullURL(request);
        try {
            Sort sortHandle = Sort.by(sortBy);
            sortHandle = sortType.equals(DefaultValue.SORT_TYPE_ASC) ? sortHandle : sortHandle.descending();

            Pageable pageable = PageRequest.of(page, limit, sortHandle);
            ResponseData responseData = ResponseData.builder().body(userService.find(pageable)).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(log.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(log.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @GetMapping("/user/me/{id}")
    @Loggable
    public ResponseEntity getCurrentUser(@PathVariable("id") Long id, HttpServletRequest request) {
        String path = Util.getFullURL(request);
        try {
            ResponseData responseData = ResponseData.builder().body(userService.getCurrentUser(id)).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(log.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(log.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @PostMapping("/user/delete/{id}")
    @Loggable
    public ResponseEntity deleteUser(@PathVariable("id") Long id,
                                     @RequestHeader(name = "Accept-Language", required = false) String languageCode,
                                     HttpServletRequest request) {
        String path = Util.getFullURL(request);
        Locale locale = LocaleContextHolder.getLocale();
        try {
            locale = new Locale(StringUtils.isBlank(languageCode) ? languageCode : languageCode);
            int result = userService.deleteUser(id);
            if (result == DefaultValue.ZERO_INT) {
                String error = messageSource
                        .getMessage(BusinessMessageCodeConst.USER_NOT_EXISTS, new String[0],
                                locale);
                ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(UserController.class.getName()).message(error).path(path).build();
                return DataResponse.error(responseData);
            } else if (result == DefaultValue.NEGATIVE_INT) {
                String error = messageSource
                        .getMessage(BusinessMessageCodeConst.UN_AUTHORIZE_PERMISSION, new String[0],
                                locale);
                ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(UserController.class.getName()).message(error).path(path).build();
                return DataResponse.error(responseData);
            }
            ResponseData responseData = ResponseData.builder().body(userService.getCurrentUser(id)).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(log.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(log.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @PostMapping("/user/update")
    @Loggable
    public ResponseEntity updateUser(@Valid @RequestBody UserRequest userRequest,
                                     @RequestHeader(name = "Accept-Language", required = false) String languageCode,
                                     BindingResult errors, HttpServletRequest request) {
        String path = Util.getFullURL(request);
        Locale locale = LocaleContextHolder.getLocale();
        try {
            if (errors.hasErrors()) {
                throw new ValidationException(errors);
            }
            locale = new Locale(StringUtils.isBlank(languageCode) ? languageCode : languageCode);
            int result = userService.updateUser(userRequest);
            if (result == UserUpdateEnum.EMAIL_EXIST.intValue()) {
                String error = messageSource
                        .getMessage(BusinessMessageCodeConst.USER_EMAIL_EXISTS, new String[0],
                                locale);
                ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(UserController.class.getName()).message(error).path(path).build();
                return DataResponse.error(responseData);
            }
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(log.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(log.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }
}
