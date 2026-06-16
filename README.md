# 🎬 엔분의일 (One Over N) Backend

<div align="center">
  <img src="https://img.shields.io/badge/Java-17-007396?style=for-the-badge&logo=Java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-🐳-2496ED?style=for-the-badge&logo=Docker&logoColor=white"/>
</div>

<br>

> **"OTT 구독료 분할 및 파티 매칭을 스마트하게"**
> 넷플릭스, 티빙, 디즈니플러스 등 다양한 OTT 플랫폼의 계정을 안전하게 공유하고, 정산 내역 및 사용자 신뢰도를 투명하게 관리하는 **1/N 정산 플랫폼** 백엔드 애플리케이션 저장소입니다.

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

---

## 🍿 서비스 소개

**엔분의일 (One Over N)**은 매달 나가는 OTT 구독료 부담을 줄이기 위해 파티원을 모집하고 정산 과정을 자동화 및 시각화해 주는 효율적인 **구독 공유 관리 솔루션**입니다.

- **파티 매칭 시스템**: 원하는 OTT 플랫폼과 요금제를 선택해 파티를 생성하거나, 활성화된 파티에 가입 요청을 보낼 수 있습니다.
- **투명한 정산 관리**: 매달 발생하는 결제 금액과 파티원별 정산 여부(`PaymentStatus`)를 한눈에 모니터링합니다.
- **신뢰도 시스템 (`Reliability`)**: 파티원의 정산 이행률을 기반으로 신뢰 지수 및 히스토리를 관리하여 악성 유저를 방지합니다.

---

## 🛠️ 기술 스택 (Tech Stacks)

### 💻 Core Framework & Language
- **Language**: Java 17
- **Framework**: Spring Boot 3.x
- **Build Tool**: Gradle (Groovy DSL)

### 🔐 Security & Authentication
- **Security**: Spring Security
- **Authentication**: JWT (Json Web Token) 아키텍처 기반의 필터(`JwtAuthFilter`) 인증 체계 구축
- **Context Handling**: `@AuthUser` 커스텀 어노테이션을 구현하여 Spring Security Context 내 인증 객체(`AuthMember`) 동적 주입 구조화

### ⚡ Memory Cache & Storage (Redis)
- **Session & Token Cache**: **Spring Data Redis** 연동 인프라 구축
- **Token Management**: 보안성 강화를 위해 `Refresh Token`을 인메모리 NoSQL **Redis**에 Key-Value 쌍으로 TTL(Time-To-Live) 설정을 주어 자동 만료 및 블랙리스트 캐싱 제어
  
### 🗄️ Database & ORM
- **Main Database**: MySQL 8.0 / H2 (Test)
- **ORM / Data**: Spring Data JPA (Hibernate)
- **Auditing**: `BaseEntity` 상속을 통한 엔티티 생성·수정 시간(`CreatedAt`, `UpdatedAt`) 추적 자동화

### ⚙️ DevOps & API Documentation
- **Containerization**: Docker를 통한 컨테이너화 인프라 구성
- **API Documentation**: OpenAPI v3 (Swagger UI) 통합 설정을 통한 API 명세 자동화

---

## 📁 디렉토리 구조 (Directory Structure)

도메인 기반 아키텍처(Domain-Driven Package Structure)를 채택하여, 각 도메인 단위로 Controller, Service, Repository, Entity, DTO, Exception 레이어를 수평적으로 캡슐화했습니다.

```text
src/main/java/com/oneovern/
├── domain/                         # 비즈니스 로직 중심의 핵심 도메인 패키지 집합
│   ├── member/                     # 회원 관리 및 자체 인증 도메인
│   │   ├── controller/             # AuthController, MemberController
│   │   ├── dto/                    # MemberReqDto, MemberResDto
│   │   ├── entity/                 # Member, ReliabilityHistory (신뢰도 내역)
│   │   └── repository/             # MemberRepository, ReliabilityHistoryRepository
│   ├── notification/               # 실시간 시스템 및 서비스 알림 도메인
│   ├── ott/                        # OTT 플랫폼 정보 및 요금제(OttPlan) 관리 도메인
│   ├── party/                      # 파티 모집, 매칭 및 가입 요청 매핑 도메인
│   │   └── entity/mapping/         # JoinRequest(가입 요청), PartyMember(중간 매핑)
│   └── settlement/                 # 정산 요청 및 멤버별 결제 관리 도메인
│       └── entity/                 # PartySettlement, MemberPayment
├── global/                         # 애플리케이션 전역에 가동되는 공통 인프라 레이어
│   ├── apiPayload/                 # 일관된 공통 응답 포맷(ApiResponse) 및 에러 핸들링
│   │   ├── code/                   # BaseErrorCode, GeneralSuccessCode 등
│   │   ├── exception/              # ProjectException, GeneralExceptionHandler
│   └── config/                     # CorsConfig, JpaAuditConfig, SecurityConfig, SwaggerConfig
└── security/                       # Security 인프라, JwtAuthFilter, JwtUtil, CustomEntryPoint
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

  # Redis 인프라 설정 연동 파트
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
가동이 성공하면 http://localhost:8080/swagger-ui/index.html 을 통해 인터랙티브 API 명세서를 확인할 수 있습니다.
