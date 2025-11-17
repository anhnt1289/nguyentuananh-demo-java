package com.main.api.controller;

import com.base.common.constant.BusinessMessageCodeConst;
import com.base.common.constant.DefaultValue;
import com.base.common.model.Content;
import com.base.common.payload.AddContentRequest;
import com.base.common.payload.EditContentRequest;
import com.base.common.util.DateUtil;
import com.base.common.valid.ValidationException;
import com.main.api.payload.DataResponse;
import com.main.api.payload.ResponseData;
import com.main.api.pointcutadvice.annotations.Loggable;
import com.main.api.service.ContentService;
import com.main.api.util.Util;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api")
public class ContentController {
    private static final Logger logger = Logger.getLogger(ContentController.class.getName());

    @Autowired
    private ContentService contentService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/content/find")
    @Loggable
    public ResponseEntity loadAllContent(
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
            ResponseData responseData = ResponseData.builder().body(contentService.find(pageable)).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(logger.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(logger.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @PostMapping("/content/add")
    @Loggable
    public ResponseEntity addProductCategory(@Valid @RequestBody AddContentRequest contentRequest, BindingResult errors, HttpServletRequest request) {
        String path = Util.getFullURL(request);
        try {
            if (errors.hasErrors()) {
                throw new ValidationException(errors);
            }
            ResponseData responseData = ResponseData.builder().body(contentService.add(contentRequest)).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(logger.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(logger.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @PostMapping("/content/update")
    @Loggable
    public ResponseEntity updateProductCategory(@Valid @RequestBody EditContentRequest editContentRequest,
                                                @RequestHeader(name = "Accept-Language", required = false) String languageCode,
                                                BindingResult errors, HttpServletRequest request) {
        String path = Util.getFullURL(request);
        Locale locale = LocaleContextHolder.getLocale();
        try {
            if (errors.hasErrors()) {
                throw new ValidationException(errors);
            }
            locale = new Locale(StringUtils.isBlank(languageCode) ? languageCode : languageCode);
            Content content = contentService.edit(editContentRequest);
            if (com.base.common.util.Util.validate((content))) {
                String error = String.format(messageSource
                        .getMessage(BusinessMessageCodeConst.ID_NOT_EXISTS, new String[0],
                                locale), content.getId());
                ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(UserController.class.getName()).message(error).path(path).build();
                return DataResponse.error(responseData);
            }
            ResponseData responseData = ResponseData.builder().body(editContentRequest).timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.OK.value()).message(HttpStatus.OK.toString()).path(path).trace(logger.getName()).build();
            return DataResponse.success(responseData);
        } catch (Exception ex) {
            ResponseData responseData = ResponseData.builder().timestamp(DateUtil.getCurrentTimeStamp()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.toString()).trace(logger.getName()).message(ex.getMessage()).path(path).build();
            return DataResponse.error(responseData);
        }
    }

    @PostMapping("/content/delete/{id}")
    @Loggable
    public ResponseEntity deleteUser(@PathVariable("id") Long id,
                                     @RequestHeader(name = "Accept-Language", required = false) String languageCode,
                                     HttpServletRequest request) {
        String path = Util.getFullURL(request);
        Locale locale = LocaleContextHolder.getLocale();
        try {
            locale = new Locale(StringUtils.isBlank(languageCode) ? languageCode : languageCode);
            int result = contentService.delete(id);
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
