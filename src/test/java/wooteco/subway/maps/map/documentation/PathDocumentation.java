package wooteco.subway.maps.map.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import wooteco.security.core.TokenResponse;
import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.application.PathService;
import wooteco.subway.maps.map.ui.MapController;

@WebMvcTest(controllers = {MapController.class})
public class PathDocumentation extends Documentation {
	@Autowired
	private MapController mapController;

	@MockBean
	private MapService mapService;
	@MockBean
	private PathService pathService;

	protected TokenResponse tokenResponse;

	@BeforeEach
	public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
		super.setUp(context, restDocumentation);
		tokenResponse = new TokenResponse("token");
	}

	@DisplayName("경로를 찾는다.")
	@Test
	void findPath() {

		Map<String, Object> params = new HashMap<>();
		params.put("source", 1L);
		params.put("target", 2L);
		given().log().all().
			header("Authorization", "Bearer " + tokenResponse.getAccessToken()).
			contentType(MediaType.APPLICATION_JSON_VALUE).
			body(params).
			param("source", "1").
			param("target", "3").
			param("type", "DISTANCE").
			when().
			get("/paths").
			then().
			log().all().
			apply(document("paths/find",
				getDocumentRequest(),
				getDocumentResponse(),
				requestHeaders(
					headerWithName("Authorization").description("Bearer auth credentials")),
				requestFields(
					fieldWithPath("source").type(JsonFieldType.NUMBER).description("출발역 아이디"),
					fieldWithPath("target").type(JsonFieldType.NUMBER).description("도착역 아이디")))).
			extract();
	}
}
