package com.wasin.backend.controller;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend.domain.dto.BackOfficeRequest;
import com.wasin.backend.util.TestModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("백오피스 통합 테스트")
public class BackOfficeControllerTest extends TestModule {

    @Nested
    @DisplayName("백오피스 승낙")
    class AcceptTest {

        @DisplayName("성공")
        @Test
        @WithUserDetails(existEmail)
        public void success() throws Exception {
            // given
            BackOfficeRequest.AcceptDTO requestDTO = new BackOfficeRequest.AcceptDTO(2L);
            String requestBody = om.writeValueAsString(requestDTO);

            // then
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/backoffice/accept")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            result.andExpect(jsonPath("$.success").value("true"));
        }

        @DisplayName("실패 - 일반 관리자가 아님")
        @Test
        @WithUserDetails(existEmail)
        public void fail() throws Exception {
            // given
            BackOfficeRequest.AcceptDTO requestDTO = new BackOfficeRequest.AcceptDTO(3L);
            String requestBody = om.writeValueAsString(requestDTO);

            // then
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/backoffice/accept")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );

            // then
            BaseException exception = BaseException.ROLE_NOT_ADMIN;
            result.andExpect(jsonPath("$.success").value("false"));
            result.andExpect(jsonPath("$.error.status").value(exception.getStatus().value()));
            result.andExpect(jsonPath("$.error.message").value(exception.getMessage()));
        }

    }

    @Nested
    @DisplayName("대기 중인 일반 관리자 리스트 출력")
    class WaitingListTest {

        @DisplayName("성공")
        @Test
        @WithUserDetails(existEmail)
        public void success() throws Exception {
            // given

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get("/backoffice")
            );

            // then
            result.andExpect(jsonPath("$.success").value("true"));
        }

    }


}
