package com.wasin.backend.controller;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend.domain.dto.CompanyRequest;
import com.wasin.backend.util.TestModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@DisplayName("회사 통합 테스트")
public class CompanyControllerTest extends TestModule {

    @Nested
    @DisplayName("Open API 내 회사 정보 조회")
    class FindOpenAPICompanyTest {

        @DisplayName("성공")
        @Test
        @WithUserDetails("leena0912@naver.com")
        public void success() throws Exception {
            // given
            String name = "삼성";
            long page = 1L;

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .get("/company/open-api?name=" + name + "&page=" + page)
            );
            logResult(result);

            // then
            result.andExpect(jsonPath("$.success").value("true"));
            result.andExpect(jsonPath("$.response.companyOpenAPIList[0].companyFssId").isString());
            result.andExpect(jsonPath("$.response.companyOpenAPIList[0].location").isString());
            result.andExpect(jsonPath("$.response.companyOpenAPIList[0].companyName").isString());
        }

        @DisplayName("실패 - 일반 관리자가 접근함")
        @Test
        @WithUserDetails("leena0913@naver.com")
        public void fail() throws Exception {
            // given
            String name = "삼성";
            long page = 1L;

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .get("/company/open-api?name=" + name + "&page=" + page)
            );
            logResult(result);

            // then
            BaseException exception = BaseException.AUTH_PERMISSION_DENIED;
            result.andExpect(jsonPath("$.success").value("false"));
            result.andExpect(jsonPath("$.error.status").value(exception.getStatus().value()));
            result.andExpect(jsonPath("$.error.message").value(exception.getMessage()));
        }

    }
    
    @Nested
    @DisplayName("Open API 내 회사 정보 등록")
    class SaveOpenAPICompanyTest {
    
        @DisplayName("성공")
        @Test
        @WithUserDetails("leena0912@naver.com")
        public void success() throws Exception {
            // given
            CompanyRequest.CompanyByOpenAPI requestDTO = new CompanyRequest.CompanyByOpenAPI(
                    "1234-5234-2634",
                    "부산광역시 수영구 민락동",
                    "원정이의 집 ><"
            );
            String requestBody = om.writeValueAsString(requestDTO);
            
            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/company/open-api")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );
            logResult(result);

            // then
            result.andExpect(jsonPath("$.success").value("true"));
        }

        @DisplayName("실패 - 이미 있는 회사")
        @Test
        @WithUserDetails("leena0912@naver.com")
        public void fail() throws Exception {
            // given
            CompanyRequest.CompanyByOpenAPI requestDTO = new CompanyRequest.CompanyByOpenAPI(
                    "12345-55555-3663",
                    "부산광역시 수영구 민락동",
                    "원정이의 집 ><"
            );
            String requestBody = om.writeValueAsString(requestDTO);

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders
                            .post("/company/open-api")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );
            logResult(result);

            // then
            BaseException exception = BaseException.COMPANY_ALREADY_EXIST;
            result.andExpect(jsonPath("$.success").value("false"));
            result.andExpect(jsonPath("$.error.status").value(exception.getStatus().value()));
            result.andExpect(jsonPath("$.error.message").value(exception.getMessage()));
        }
      
    }

    @Nested
    @DisplayName("DB 내 회사 목록 조회")
    class FindCompanyDBTest {

        @DisplayName("성공")
        @Test
        @WithUserDetails("leena0913@naver.com")
        public void success() throws Exception {
            // given

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get("/company/db")
            );
            logResult(result);

            // then
            result.andExpect(jsonPath("$.success").value("true"));
            result.andExpect(jsonPath("$.response.companyDBList[0].companyId").isNumber());
            result.andExpect(jsonPath("$.response.companyDBList[0].location").isString());
            result.andExpect(jsonPath("$.response.companyDBList[0].companyName").isString());
        }

        @DisplayName("실패 - 최종 관리자가 접근함")
        @Test
        @WithUserDetails("leena0912@naver.com")
        public void fail() throws Exception {
            // given
            

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.get("/company/db")
            );
            logResult(result);

            // then
            BaseException exception = BaseException.AUTH_PERMISSION_DENIED;
            result.andExpect(jsonPath("$.success").value("false"));
            result.andExpect(jsonPath("$.error.status").value(exception.getStatus().value()));
            result.andExpect(jsonPath("$.error.message").value(exception.getMessage()));
        }

    }

    @Nested
    @DisplayName("DB 내 회사 등록")
    class SaveCompanyDBTest {

        @DisplayName("성공")
        @Test
        @WithUserDetails("leena0913@naver.com")
        public void success() throws Exception {
            // given
            CompanyRequest.CompanyByDB requestDTO = new CompanyRequest.CompanyByDB(1L);
            String requestBody = om.writeValueAsString(requestDTO);

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post("/company/db")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );
            logResult(result);

            // then
            result.andExpect(jsonPath("$.success").value("true"));
        }

        @DisplayName("실패 - 존재하는 회사 없음")
        @Test
        @WithUserDetails("leena0913@naver.com")
        public void fail() throws Exception {
            // given
            CompanyRequest.CompanyByDB requestDTO = new CompanyRequest.CompanyByDB(100000L);
            String requestBody = om.writeValueAsString(requestDTO);

            // when
            ResultActions result = mvc.perform(
                    MockMvcRequestBuilders.post("/company/db")
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON)
            );
            logResult(result);

            // then
            BaseException exception = BaseException.COMPANY_NOT_FOUND;
            result.andExpect(jsonPath("$.success").value("false"));
            result.andExpect(jsonPath("$.error.status").value(exception.getStatus().value()));
            result.andExpect(jsonPath("$.error.message").value(exception.getMessage()));
        }

    }

}
