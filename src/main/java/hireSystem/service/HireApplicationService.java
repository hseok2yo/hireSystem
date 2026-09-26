package hireSystem.service;

/**
 * 채용공고 지원(지원하기) 서비스
 */
public interface HireApplicationService {

	/**
	 * 채용공고에 지원한다.
	 * - resumeId를 지정하면(지원 모달에서 "이력서 변경"으로 고른 이력서) 해당 이력서로 지원하며,
	 *   본인 소유의 이력서인지 검증한다.
	 * - resumeId가 null이면 기존 동작대로 대표이력서(IS_MAIN='Y')로 자동 지원한다.
	 * - 중복 지원 체크 → 지원내역 저장 → 공고의 APPLY_CNT +1
	 *
	 * @param jobPostingId  지원할 공고 ID
	 * @param resumeId      지원 모달에서 선택한 이력서 ID (없으면 null → 대표이력서로 대체)
	 * @param loginUserNum  로그인한 회원 번호 (세션값)
	 * @return 실제로 지원에 사용된 resumeId
	 * @throws IllegalStateException 이미 지원한 공고이거나, 대표이력서가 없거나, 본인 소유가 아닌 이력서를 선택한 경우
	 */
	int applyJob(int jobPostingId, Integer resumeId, int loginUserNum);
}
