# 🛠️ 팀별 협업 가이드

1. 중앙 레포지토리 Fork

+ 각 팀은 본 레포지토리를 Fork하여 팀별 저장소를 생성합니다.

2. 팀원 초대 및 협업 환경 구성

+ 팀장은 팀원들을 Fork한 저장소의 Collaborator로 초대합니다.

+ 팀원들은 팀 저장소를 Clone하여 개발 환경을 세팅합니다.

3. Git Flow 전략 적용

+ 팀 저장소에서 develop 브랜치를 기준으로, 각 조별 브렌치 네이밍에 맞게 생성하여 작업합니다.

4. 최종 결과물 PR 제출

+ 팀별로 모든 작업이 완료되면, 팀 저장소의 develop 브랜치에서 추후 생성될 본 레포지토리의 develop-조번호 브랜치로 PR(Pull Request)을 생성합니다.

+ 예시: develop-6 (6조의 경우)

5. 코드 리뷰 및 병합

+ 모든 팀은 다른 팀의 PR을 확인하고, 자유롭게 피드백을 남길 수 있습니다.

+ 최종적으로 관리자가 PR을 확인 후 병합합니다.

# 📌 브랜치 및 PR 네이밍 규칙

+ 팀별 PR 대상 브랜치:
  develop-조번호 (예: develop-3, develop-6)

+ 기능 개발 브랜치:
  각 조별 브렌치 네이밍 규칙 준수

+ PR 제목 예시:
  [6조] 최종 결과물 제출

# 🚨 유의사항

+ 모든 커밋 메시지는 명확하게 작성해 주세요.

+ 팀 저장소에서 PR 생성 시, 관련 이슈 번호를 반드시 연결해 주세요.

# 협업 가이드라인

---

## 📁 프로젝트 폴더 구조

```bash
src
└── main
    └── java
        └── cotato
            └── backend
                ├── api                   # API 엔드포인트(컨트롤러) 계층
                │   ├── weather           
                │   └── location          
                ├── domain                
                │   ├── weather           # 날씨 도메인
                │   │   ├── controller
                │   │   ├── entity
                │   │   ├── dto
                │   │   ├── error
                │   │   ├── enums
                │   │   ├── repository
                │   │   └── service
                │   │       ├── command
                │   │       └── query
                │   └── location          # 위치 도메인
                │       ├── controller
                │       ├── entity
                │       ├── dto
                │       ├── error
                │       ├── enums
                │       ├── repository
                │       └── service
                │           ├── command
                │           └── query
                ├── infra                 # 외부 시스템 연동, 인프라, 설정 등
                └── global                # 전역 공통 코드
                    ├── common
                    ├── config
                    ├── entity
                    ├── error
                    └── util
```

## 🏷️ 커밋 타입(Type) 및 gitmoji

| 타입       | gitmoji(option) | 설명     |
|----------|-----------------|--------|
| feat     | ✨               | 새로운 기능 |
| fix      | 🐛              | 버그 수정  |
| docs     | 📚              | 문서 변경  |
| style    | 🎨              | 스타일/포맷 |
| refactor | ♻️              | 리팩터링   |
| test     | ✅               | 테스트 코드 |
| chore    | 🔧              | 기타 작업  |

### ✏️ 사용 예시

```
✨ feat: 사용자 인증 API 추가 (#12)
```

or

```
feat: 사용자 인증 API 추가 (#12)
```

---

## 📝 Issue 생성 시 주의사항

- 제목과 내용을 명확하게 작성해주세요.
- 중복 이슈가 없는지 먼저 검색해주세요.
- Issue에 Assigned 진행시 자동으로 관련 브랜치가 생성됩니다
    - `feature/이슈번호` 해당 브랜치에서 작업해주세요

## 📝 PR 작성 시 주의사항

- 관련 이슈가 있다면 PR에 반드시 링크해주세요.
- 변경 내용을 간결하게 요약하고, 체크리스트를 확인해주세요.
- 불필요한 파일/코드는 포함하지 않도록 주의해주세요.
- Merge는 리뷰 진행 후에만 가능합니다 review를 요청해주세요
