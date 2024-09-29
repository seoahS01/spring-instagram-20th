# spring-instagram-20th
CEOS 20th BE study - instagram clone coding

## 🪴 데이터 모델링 개념 공부

### 🌱 데이터 모델링

**데이터 모델링이란**

업무 내용을 분석하여 이해하고, 약속된 표기법에 의해 표현하는 것

**데이터 모델링 순서**

1. 업무 파악 (요구사항 수집 및 분석)
2. 개념적 데이터 모델링
    
    하고자 하는 일의 데이터 간의 관계를 구상
    
    e.g. 피터 첸 표기법으로 ERD 다이어그램 구성
    
    ![image](https://github.com/user-attachments/assets/75824021-4bb3-474f-a6ca-88347b2c5831)

    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/1f9de6d4-5bcb-4f8f-b058-ab2c24b25167/image.png)
    
3. 논리적 데이터 모델링
    
    데이터 타입 명시, 데이터 간의 관계 정립, 테이블의 키 지정
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/6a5059ce-f385-4569-a065-49bc8589e896/image.png)
    
4. 물리적 데이터 모델링
    
    최종적으로 데이터를 관리할 데이터베이스를 선택하고, 선택한 데이터 베이스에 실제 테이블을 만드는 작업
    
    즉, 만든 시각적인 구조를 실제 SQL 코딩을 통해 완성하는 단계
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/14b4ff5f-106a-4005-8f7a-f32f1c3b018c/image.png)
    
    - 코드
        
        ```java
        /* 테이블 생성 */
        
        -- 회원정보
        create table member_tbl ( 
          member_uid bigint primary key auto_increment,
          member_name varchar(45) unique not null,
          member_pwd varchar(45) not null,
          member_status boolean not null
        );
        
        -- 로그인기록정보
        create table login_info_tbl( 
          member_name varchar(45) not null,
          info_ip varchar(45) not null,
          info_date datetime not null,
          constraint fk_member_name foreign key (member_name) references member_tbl (member_name)
        );
        
        -- 게시판
        create table board_tbl ( 
          board_uid bigint primary key auto_increment,
          member_name varchar(45) not null,
          board_title varchar(45) not null,
          board_date datetime not null,
          board_hit int not null,
          board_post varchar(5000) not null,
          constraint fk_member_name foreign key(member_name) references member_tbl(member_name)
        );
        
        -- 게시판 풀텍스트 인덱스 생성
        create Fulltext index idx_title on board_tbl ( board_title );
        create Fulltext index idx_post on board_tbl ( board_post );
        -- show index from board_tbl ;
        
        -- 댓글
        create table reply_tbl ( 
          reply_uid bigint primary key auto_increment,
          board_uid bigint not null,
          member_name varchar(45) not null,
          reply_date datetime not null,
          reply_post varchar(1000) not null,
          foreign key(board_uid) references board_tbl(board_uid),
          foreign key(member_name) references member_tbl(member_name)
        );
        
        -- 댓글 풀텍스트 인덱스 생성
        create Fulltext index idx_reply on reply_tbl ( reply_post );
        ```
        
    

### 🌱 ERD 그리기

ERD (Entity Relationship Diagram): Entity 개체 Relationship을 중점적으로 표시하는 데이터베이스 구조를 한 눈에 알아보기 위해 그려놓는 다이어그램

**엔티티 Entity**

정의 가능한 사물 또는 개념 e.g. 학생

데이터베이스의 테이블이 엔티티로 표현된다고 보면 됨

**엔티티 속성 Attribute**

엔티티에는 개체가 갖고 있는 속성을 포함함 e.g. 학생 엔티티의 학번, 이름, 주소, 전공 

데이터베이스의 테이블의 각 필드(컬럼)들이 엔티티 속성

**엔티티 도메인 Domain**

속성의 값, 타입, 제약사항 등에 대한 값의 범위를 표현하는 것

사용자의 기호에 따라 속성 타입만 그릴 수도 있고, 가독성을 위해 생략할 수도 있음

데이터 타입을 명시할 때, 데이터베이스가 지원하는 타입에 맞게 해야됨

### 🌱 ERD 키와 제약 조건 표기법

**Primary Key(PK / 주 식별자)**

중복이 없고 NULL 값이 없는 유일한 값에 지정하는 식별자

**NOT NULL** 

N 혹은 NN으로 표기

**Foreign Key(FK / 외래 식별자)**

외래 식별자를 표시할 때에는 선을 이어주는데, 개체와 관계를 따려 표시

### 🌱 ERD 엔티티 관계 표기법

| 항목 | 식별자 관계 | 비식별자 관계 |
| --- | --- | --- |
| 목적 | 강한 연결관계 표현 | 약한 연결관계 표현 |
| 자식 주 식별자 영향 | 자식 주식별자의 구성에 포함 | 자식 일반 속성에 포함됨 |
| 표기법 | 실선 표현 | 점선 표현 |
| 연결 고려 사항 | - 반드시 부모 엔티티 종속 | - 자식 주 식별자 구성을 독립적으로 구성
- 상속받은 주식별자 속성을 타 엔터티에 차단 필요
- 부모 쪽의 관계 참여가 선택 관계 |

**식별자 관계**

- 자식이 부모의 PK를 FK로 참조해서 자신의 PK로 설정

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/6fd39ae0-2568-4550-a69d-d8279271a322/image.png)

**비식별자 관계**

- 부모의 PK를 FK로 참조해서 일반 속성으로 사용

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/7636b525-6f9a-43b2-b411-8848ea564b65/image.png)

### 🌱 ERD 관계의 카디널리티

카디널리티: 한 개체에서 발생할 수 있는 발생 횟수. 다른 개체에서 발생할 수 있는 발생 횟수와 연관됨

**One to One Cardinality (1:1 관계)**

e.g. 학생과 신체 정보 → 한 명의 학생은 하나의 신체 정보만 갖기 때문에

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/299dd0e5-8273-4472-a32c-f50628b0f0b1/image.png)

**One to Many Cardinality (1:N 관계)**

e.g. 한 명의 학생은 여러 개의 취미를 가질 수도 있음

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/f4d472e4-1fc1-4618-8d2f-93cf3fea5927/image.png)

**Many to Many Cardinality(M:N 관계)**

e.g. 제품 엔티티 입장에서,TV 제품은 삼성 티비, 애플 티비, lg 티비 등 여러 제조업체 제품이 있을 수 있음

제조업체 엔티티 입장에서도 삼성 제조업체는 TV 뿐만 아니라 세탁기, 냉장고 등 여러 제품을 생성할 수 있음

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/29f5d722-f3dd-40ee-9092-bc262ad9faf9/image.png)

두 엔티티가 M:N 관계에 있는 경우, 두 개의 엔티티만으로는 서로를 표현하는데 부족함 → 1:N, N:1로 조정하는 작업이 필요

이를 위해 중간 엔티티를 만들어 기존 두 엔티티의 공유 속성 역할을 하게 됨

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/d7a4f085-bfeb-40a3-ad9b-7dd05f1d7fc4/image.png)

### 🌱 ERD 관계의 참여도

관계선의 각 측의 끝자락에 기호를 표시

- | : 반드시 있어야 하는 개체
- O : 없어도 되는 개체

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/740ba058-b680-449e-8b34-03216c85d262/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/14f3ae4d-5f47-43b0-90c2-224a9ea9300f/image.png)

**관계의 선택 기호**

e.g. 취미를 가진 학생이 있을 수도 있고, 취미가 없는 학생이 있을 수도 있음

**관계의 필수 기호**

e.g. 학법 21003 학생의 취미가 낚시라는 정보가 있다면, 21003 학생의 정보는 반드시 학생 엔티티에 존재해야함

### 🌱 JOIN

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/fad47d63-1092-429d-a374-fe3be0ac0abc/image.png)

## 🪴 ERD Cloud 사용법

[ERDCloud](https://www.erdcloud.com/)

### 🌱 ERD 생성

1. title 작성
    - 논리적 이름: 왼쪽. 내가 알아보기 위한 이름
    - 물리적 이름: 오른쪽. 실제 데이터베이스에 저장되는 테이블 이름
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/f64cf84a-9bf3-4781-b750-3a2a33ee7af6/image.png)
    
2. attribute 추가
    - 노란색 버튼: PK attribute 추가
    - 파란색 버튼: 일반 attribute 추가
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/21e0459a-1622-4a37-af22-b13ee3e2098c/image.png)
    

### 🌱 노출되는 속성 선택

오른쪽 위 톱니바퀴 모양 아이콘 → 테이블에서 어떤 속성을 보여줄 지 설정 가능

- Display: 화면에서 보여지는 테이블의 속성을 선택하는 곳
- share: 다른 사용자와 동시 작업을 할 지 선택

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/55c3d5dc-1008-49d1-b746-63e423055f66/image.png)

### 🌱 관계도 그리기

왼쪽 메뉴바에서 선택 

관계를 해제하려면 연결괴어 있는(FK로 등록된) attribute를 삭제해야 함

- Identifying Relationship: 실선
- Non-Identifying Relationship: 점선

## 🪴인스타그램 기능 분석

### 🌱 요구사항

- 게시글 조회
- 게시글에 사진과 함께 글 작성하기
- 게시글에 댓글 및 대댓글 기능
- 게시글에 좋아요 기능
- 게시글, 댓글, 좋아요 삭제 기능
- 유저 간 1:1 DM 기능
- 회원 기능은 필수로 추가 → 추후 로그인에서 사용됨

### 🌱 기능 분석

**User & Follow**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/6c78981f-beff-49cc-af2f-6942efc82382/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/465f4fd0-21ed-48e9-999f-992d845dfe23/image.png)

[ 기능분석 ]

- 생일을 기준으로 만 14세 이상만 가입할 수 있다

[ 관계분석 ]

- 한 명의 회원은 아무도 팔로우하지 않거나, 여러 명을 팔로우할 수 있다 (1:N)
- 한 명의 회원은 게시물을 아무것도 올리지 않거나 여러 개를 올릴 수 있다 (1:N)

**Post & Post Image & Post Like & Scrap**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/2168f18c-90c1-4d13-a34e-a0192696e922/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/6877a071-b126-4dd9-aa51-7f2f67eb6d7b/image.png)

[ 기능분석 ]

- 이미지와 본문, 생성일
- 게시물에는 여러 장의 사진을 올릴 수 있다

[ 관계분석 ]

- 한 명의 회원은 여러 게시물을 스크랩할 수 있고, 한 개의 게시물은 여러 회원에게 스크랩될 수 있다 (M:N)
- 한 명의 회원은 여러개의 게시물에 좋아요를 누를 수 있고, 하나의 게시물에는 여러 명이 좋아요를 누를 수 있다 (M:N)

**Comment & Comment Like**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/ef4c289e-9443-4598-8b89-05d2ad850006/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/974036da-acb4-4495-897b-44da13c3a7ef/image.png)

[ 기능분석 ]

- 댓글을 게시글에 달린다
- 댓글에는 대댓글이 달릴 수 있다
- 대댓글의 수가 표시된다
    
    → 부모 id가 null이 아닐 때 부모 id에 딸린 댓글 수로 계산
    

[ 관계분석 ]

- 한 명의 회원은 여러 개의 댓글을 달 수 있다(1:N)
- 하나의 댓글에는 여러 명이 좋아요를 누를 수 있고 한 명의 회원은 여러 개의 댓글에 좋아요를 누를 수 있다(M:N)
- 하나의 게시글에는 여러 개의 댓글이 달릴 수 있다(1:N)

**DM & DM Room**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/28fd345b-58ac-485b-8768-65cf8162b96a/image.png)

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/053a8561-a466-42b0-992e-8006b21c947b/image.png)

[ 기능분석 ]

- 1:1 채팅방
- 보냈던 DM을 언급해 답장할 수 있다
- DM 방에서 주고 받는 이미지의 경우, url 한 줄로 만들 수 있기 때문에 따로 엔티티를 만들지 않는다

[ 관계분석 ]

- DM 답장을 위해서는 하나의 DM만 언급이 가능하다 (1:1)
- 하나의 DM방에는 여러 개의 DM이 있다(1:N)
- 한 명의 회원은 여러 개의 DM을 보낼 수 있다 (1:N)
- 한 명의 회원은 여러 개의 DM 방에 들어갈 수 있고, 하나의 DM방에는 2명의 회원이 들어갈 수 있다 (M:N)

### 🌱 ERD

https://www.erdcloud.com/d/qF45tQoypstpAqQW3

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/d6cd7a95-d578-4984-bc27-c7e8cb6ebb02/dc4cf164-861b-4feb-8b5f-5b6920998dce/image.png)

## 🪴 클래스 생성

엔티티마다 클래스를 생성하고, 필요한 필드 추가

### 🌱 Directory Architecture

*src > main > java > com.ceos20.spring_boot*

🗂️ user

📁 domain

- User.java
- Follow.java

📁 repository

- UserRepository.java
- FollowRespoitory.java

🗂️ post

📁 domain

- Post.java
- PostImage.java
- PostLike.java
- Scrap.java

📁 repository

- PostRepository.java
- PostImageRepository.java
- PostLikeRepository.java
- ScrapRepository.java

🗂️ comment

📁 domain

- Comment.java
- CommentLike.java

📁 repository

- CommentRepository.java
- CommentLike.java

🗂️ dm

📁 domain

- DM.java
- DMRoom.java

📁 repository

- DMRepository.java
- DMRoomRepository.java

### 🌱 필드 구성 예시

```java
package com.ceos20.spring_boot.comment.domain;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "comment_content", nullable = false, length = 200)
    private String commentContent;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "like_num", nullable = false)
    private int likeNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @Column(name = "post_id2")
    private Long postId2;

}

```

**Entity 클래스 생성 시**

- `@Entity`
    
    해당 클래스가 JPA 엔티티임을 나타냄
    
    데이터베이스 테이블과 매핑하고 관리하는 클래스로 지정
    
- `@Getter` `@Setter`
    
    lombok을 사용해 자동으로 getter와 setter 메서드 생성
    
- `@NoArgsConstructor(access = AccessLevel.PROTECTED)`
    
    기본 생성자에 대한 접근 제어를 제한할 수 있음
    
    즉, 외부에서 기본 생성자를 직접 호출하지 못하도록 제한하고, 같은 패키지 내에서나 하위 클래스에서만 생성자를 사용할 수 있게 함
    
- `@AllArgsConstructor`
    
    클래스에 존재하는 모든 필드에 대해 생성자를 자동으로 만들어줌
    
- `@Builder`
    
    빌더 패턴을 자동으로 생성해줌
    
    선택적으로 필드를 설정할 수 있다는 장점
    
- `@Table(name = "따로 지정할 테이블명")`
    
    엔티티와 매핑되는 테이블 이름 지정
    
    지정 안 하면 클래스 이름이 기본값으로 사용됨
    

**Primary Key 속성**

권장하는 식별자 전략: `Long형 + 대체키 + 키 생성전략`

- `@Id`
    
    테이블의 기본 키와 객체의 필드 매핑
    
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`
    
    `@Id` 만 사용할 경우 기본 키 값을 직접 할당해줘야 하는데, 기본 키를 직접 할당하는 대신 데이터베이스에서 생성해주는 값 사용
    
- `@Column(name = "comment_id")`
    
    따로 지정할 속성명
    

**Foreign Key 속성과 연관 관계**

- 연간관계 `@OneToMany`   `@ManyToOne`   `@OneToOne`   `@ManyToMany`
- `@JoinColumn(name = "조인속성")`
- 로딩
    - 즉시 로딩
        
        `@XToOne(OneToOne, ManyToOne) 관계`는 디폴트 값이 즉시 로딩이므로 직접 지연 로딩으로 설정해야 함
        
    - 지연 로딩
        
        `@XToMany`는 기본이 LAZY
        
        지연 로딩이 트랜잭션 밖에서 안 되는 등의 이슈가 있지만 다른 대안 사용
        

**일반 속성**

- `@Column(name = "연결할 column 명", nullable = false)`

**Enum 타입**

<aside>
🤔

**Enum 타입**

열거형을 표현하는 데이터 타입. 고정된 상수 집합을 정의할 때 사용

미리 정해진 몇 가지 값만 가질 수 있는 변수를 만들고 싶을 때 사용

```java
public enum Season {
    SPRING, SUMMER, FALL, WINTER
}

Season currentSeason = Season.SPRING;
```

</aside>

- `@Enumerated(EnumType.String)`
    
    Enum의 값 자체(문자열)을 저장하도록 할 수 있음
    
- `EnumType.ORDINAL`
    
    Enum 값의 순서(index. 0부터 시작)을 데베에 저장
    

<aside>
⚠️

`EnumType.ORDINAL` 사용 시 **Enum 값이 추가되거나 순서가 바뀔 때 문제가 발생할 수 있음**

새로운 Enum 값이 중간에 추가되면 기존에 저장된 값들과 순서가 맞지 않게 돼서 데이터 무결성을 헤칠 수 있음

→ `EnumType.STRING` 사용 권장

</aside>
