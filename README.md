# 🎬 엔분의일 (One Over N) - Backend

<div align="center">
  <img src="https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=Java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/>
</div>

<br>

> **"OTT 구독료 분할 및 파티 매칭을 스마트하게"**
> 넷플릭스, 티빙, 디즈니플러스 등 다양한 OTT 플랫폼의 계정을 안전하게 공유하고, 정산 내역 및 사용자 신뢰도를 투명하게 관리하는 **1/N 정산 플랫폼** 백엔드 애플리케이션 저장소입니다.  
> 본 프로젝트는 **2026-1 이화여자대학교 데이터베이스 수업 프로젝트 11조** 결과물입니다.

---

## 🗓️ 개발 기간 (Project Period)
- **총 개발 기간**: 2026년 5월 ~ 2026년 6월

---

## 👥 팀원 소개

| 이름 | 역할 | GitHub |
| :---: | :---: | :---: |
| **고예빈** | Back-End | [@KoYebin](https://github.com/KoYebin) |
| **김나성** | Back-End | [@na0302](https://github.com/na0302) |
| **김서현** | Front-End / Back-End | [@seohyeonS2](https://github.com/seohyeonS2) |
| **박선영** | Front-End / Back-End | [@kakao3838](https://github.com/kakao3838) |

- **배포 주소 (Backend):** `https://one-over-n.onrender.com`

---

## 🍿 주요 백엔드 API 기능

1. **회원 인증 및 Context 인프라**
   - JWT(Json Web Token) 인증 필터(`JwtAuthFilter`) 및 검증 체계 구축
   - `@AuthUser` 커스텀 어노테이션을 통해 Security Context 내 인증 유저 객체(`AuthMember`) 동적 주입
2. **인메모리 캐시 관리 (Redis)**
   - 보안 강화를 위해 `Refresh Token`을 Redis NoSQL 계층에 TTL(Time-To-Live) 알고리즘으로 적재하여 자동 만료 제어
3. **OTT 파티 매칭 인프라**
   - OTT 플랫폼 사양별 요금제 매핑, 실시간 파티 생성 및 가입 요청(`JoinRequest`) 생명주기 제어
4. **정산서 자동 갱신 및 스케줄링 처리**
   - 결제일에 맞춘 파티원별 1/N 청구 금액 산출 및 실시간 수납 데이터 처리 (`PaymentStatus`)

---

## 📁 디렉토리 구조 (Directory Structure)

```text
src/main/java/com/oneovern/
├── domain/                         # 도메인 중심의 비즈니스 캡슐화 레이어
│   ├── member/                     # 회원 및 신뢰도 관리 (Controller, Service, Repository, Entity)
│   ├── notification/               # 시스템 및 매칭 알림 관리
│   ├── ott/                        # OTT 및 플랫폼 요금제 정보 관리
│   ├── party/                      # 파티 모집, 매칭 및 매핑 관계 관리
│   └── settlement/                 # 1/N 정산 및 결제 처리 도메인
├── global/                         # 전역 인프라 설정 레이어
│   ├── apiPayload/                 # 공통 응답 규격(ApiResponse) 및 중앙 예외 핸들러
│   └── config/                     # CorsConfig, SecurityConfig, SwaggerConfig
└── security/                       # Security 인프라, 인증 필터 및 JWT 유틸리티

```

## 🗺️ 도메인 모델 및 아키텍처 (ERD)
```
==========================================================================================
                                 [ 1/N SYSTEM ERD STRUCTURE ]
==========================================================================================

  [1. 회원 및 신뢰도 시스템]
  - Member             : 유저 고유 식별 정보, 패스워드, 이메일, 계좌 정보 및 현재 신뢰도 지수
  - ReliabilityHistory : 정산 연체/완료 등에 따른 유저별 신뢰 지수 변동 이력 및 사유 관리

  [2. OTT 플랫폼 기반 데이터]
  - Ott                : 서비스하는 OTT 종류 (Netflix, Tving, Disney+, Watcha, Wavve 등)
  - OttPlan            : 각 OTT 플랫폼별 커스텀 요금제 메타데이터 (이름, 월 금액, 최대 동시 접속자 수)

  [3. 파티 모집 및 매칭 가동]
  - Party              : OTT 플랫폼 요금제 기반 개설된 방 정보, 파티장 ID, 매칭 상태(PartyStatus)
  - JoinRequest        : 일반 유저가 파티에 가입 신청을 넣은 상태 제어 (PENDING, ACCEPTED, REJECTED)
  - PartyMember        : 파티에 소속 완료된 멤버 매핑 데이터 테이블

  [4. 정산 및 대금 수납 시스템]
  - PartySettlement    : 특정 파티에서 매달 생성되는 총액 정산 단위 데이터 (정산 상태 관리)
  - MemberPayment      : 해당 정산 회차에 파티원 개개인이 납부해야 하는 상태 제어 (PAID, UNPAID)

  [5. 실시간 정보 알림]
  - Notification       : 가입 수락, 정산 요청 등 다양한 트리거에 의해 쌓이는 유저 알림 아카이브

------------------------------------------------------------------------------------------

                         [ 데이터베이스 테이블 연관 관계도 ]

    [ReliabilityHistory] (N) ─── (1) [ Member ] (1) ───┐
                                                        │
    [Ott] (1) ─── (N) [OttPlan] (1) ─── (N) [ Party ] (1) ───┼─── (N) [ JoinRequest ]
                                              │         │
                                              │         └─── (N) [ PartyMember ]
                                              │
                                              └─── (1) [PartySettlement] (1) ─── (N) [MemberPayment]

==========================================================================================
```
> [!NOTE]
> 아래 배지나 링크를 클릭하시면 ERDCloud 공식 사이트에서 테이블 구조 및 실시간 컬럼 명세를 상세히 확인하실 수 있습니다.

<a href="https://www.erdcloud.com/d/hbmxmcu3qM9aJjbyt" target="_blank">
  <img src="https://img.shields.io/badge/ERDCloud-실시간%20ERD%20확인하기-0078D4?style=for-the-badge&logo=databricks&logoColor=white"/>
</a>

---

## ⚙️ 시작 가이드 (How to Run)
### 1. Prerequisites (사전 요구사항)
- Java 17 JDK 설치가 필요합니다.
- MySQL 8.0 이상의 데이터베이스 인스턴스 가동이 필요합니다.
- Redis Server 가동이 필수적으로 필요합니다.

### 2. 환경 변수 및 DB 설정 (application.yml)
`src/main/resources/application.yml` 설정 파일에서 활성화할 데이터베이스 프로파일 주소 및 JWT 시크릿 정보를 입력해 주세요.

```YAML
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/oneovern?useSSL=false&serverTimezone=Asia/Seoul
    username: YOUR_MYSQL_USERNAME
    password: YOUR_MYSQL_PASSWORD
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        show_sql: true
        format_sql: true

  # Redis
  data:
    redis:
      host: localhost
      port: 6377

jwt:
  secret: YOUR_JWT_MANAGEMENT_SECRET_KEY_HERE
```

### 3. 빌드 및 실행 (Local 환경)
``` Bash
# 저장소 복사 (Clone)
$git clone [https://github.com/ecc-mutle/one-over-n-be.git$](https://github.com/ecc-mutle/one-over-n-be.git$) cd one-over-n-be

# Gradle 컴파일 및 서버 실행
$./gradlew clean build -x test$ java -jar build/libs/one-over-n-0.0.1-SNAPSHOT.jar
```
가동이 성공하면 http://localhost:8080/swagger-ui/index.html 을 통해 API 명세서를 확인할 수 있습니다.
