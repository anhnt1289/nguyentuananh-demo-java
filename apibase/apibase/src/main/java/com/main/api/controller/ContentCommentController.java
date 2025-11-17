package com.main.api.controller;

import com.base.common.constant.BusinessMessageCodeConst;
import com.base.common.constant.DefaultValue;
import com.base.common.model.ContentComment;
import com.base.common.payload.AddContentCommentRequest;
import com.base.common.payload.EditContentCommentRequest;
import com.base.common.util.DateUtil;
import com.base.common.valid.ValidationException;
import com.main.api.payload.DataResponse;
import com.main.api.payload.ResponseData;
import com.main.api.pointcutadvice.annotations.Loggable;
import com.main.api.service.ContentCommentService;
import com.main.api.util.Util;
import lombok.extern.slf4j.Slf4j;
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

@RestController
@Slf4j
@RequestMapping("/api/comment")
public class ContentCommentController {
    private static final Logger logger = Logger.getLogger(ContentCommentController.class.getName());

    @Autowired
    private ContentCommentService contentCommentService;

    @Autowired
    private MessageSource messageSource;


    @GetMapping("/find")
    @Loggable
    public ResponseEntity loadAllComment(
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
            ResponseData responseData = ResponseData.builder().body(contentCommentService.find(pageable)).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(logger.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(logger.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @PostMapping("/add")
    @Loggable
    public ResponseEntity addContentComment(@Valid @RequestBody AddContentCommentRequest contentCommentRequest, BindingResult errors, HttpServletRequest request) {
        String path = Util.getFullURL(request);
        try {
            if (errors.hasErrors()) {
                throw new ValidationException(errors);
            }
            ResponseData responseData = ResponseData.builder().body(contentCommentService.add(contentCommentRequest)).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(logger.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(logger.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @PostMapping("/update")
    @Loggable
    public ResponseEntity updateContentComment(@Valid @RequestBody EditContentCommentRequest editContentCommentRequest,
                                                @RequestHeader(name = "Accept-Language", required = false) String languageCode,
                                                BindingResult errors, HttpServletRequest request) {
        String path = Util.getFullURL(request);
        Locale locale = LocaleContextHolder.getLocale();
        try {
            if (errors.hasErrors()) {
                throw new ValidationException(errors);
            }
            locale = new Locale(StringUtils.isBlank(languageCode) ? languageCode : languageCode);
            ContentComment contentComment = contentCommentService.edit(editContentCommentRequest);
            if (com.base.common.util.Util.validate((contentComment))) {
                String error = String.format(messageSource
                        .getMessage(BusinessMessageCodeConst.ID_NOT_EXISTS, new String[0],
                                locale), contentComment.getId());
                ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(UserController.class.getName()).message(error).path(path).build();
                return DataResponse.error(responseData);
            }
            ResponseData responseData = ResponseData.builder().body(editContentCommentRequest).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(logger.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(logger.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @PostMapping("/delete/{id}")
    @Loggable
    public ResponseEntity deleteContentComment(@PathVariable("id") Long id,
                                     @RequestHeader(name = "Accept-Language", required = false) String languageCode,
                                     HttpServletRequest request) {
        String path = Util.getFullURL(request);
        Locale locale = LocaleContextHolder.getLocale();
        try {
            locale = new Locale(StringUtils.isBlank(languageCode) ? languageCode : languageCode);
            int result = contentCommentService.delete(id);
            if (result == DefaultValue.ZERO_INT) {
                String error = String.format(messageSource
                        .getMessage(BusinessMessageCodeConst.ID_NOT_EXISTS, new String[0],
                                locale), id);
                ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(UserController.class.getName()).message(error).path(path).build();
                return DataResponse.error(responseData);
            }
            ResponseData responseData = ResponseData.builder().body(result).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(log.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(log.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }
}
