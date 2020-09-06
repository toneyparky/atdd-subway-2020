package wooteco.subway.maps.map.documentation;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;

import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.map.ui.MapController;

@WebMvcTest(controllers = {MapController.class})
public class PathDocumentation extends Documentation {

	@DisplayName("경로를 찾는다.")
	@Test
	public static RestDocumentationResultHandler findPath() {
		return document("paths/find",
			getDocumentRequest(),
			getDocumentResponse(),
			requestHeaders(
				headerWithName("Authorization").description("사용자 토큰 정보")),
			requestParameters(
				parameterWithName("source").description("출발역 아이디"),
				parameterWithName("target").description("도착역 아이디"),
				parameterWithName("type").description("경로 검색 기준")),
			responseFields(
				fieldWithPath("stations.[]").type(JsonFieldType.ARRAY).description("경로에 해당 되는 역 목록"),
				fieldWithPath("stations.[].id").type(JsonFieldType.NUMBER).description("경로에 해당 되는 역 ID"),
				fieldWithPath("stations.[].name").type(JsonFieldType.STRING).description("경로에 해당 되는 역 이름"),
				fieldWithPath("duration").type(JsonFieldType.NUMBER).description("경로 예상 시간(분)"),
				fieldWithPath("distance").type(JsonFieldType.NUMBER).description("경로 예상 거리(KM)"),
				fieldWithPath("fare").type(JsonFieldType.NUMBER).description("요금")
			)
		);
	}
}
