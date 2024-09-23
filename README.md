# spring-instagram-20th
CEOS 20th BE study - instagram clone coding

### DB 모델링

- User는 Post, Comment, Like, DirectMessage와 각각 1:1 관계를 가짐
- Post와 Comment, Like는 게시글을 중심으로 관계가 형성됨


### JPA
자바에서 관계형 데이터베이스를 다루기 위한 표준 인터페이스
데이터베이스와 자바 객체 간의 변환을 도와주는 역할
- Entity
  
    데이터베이스 테이블과 매핑되는 클래스 e.g. User 라는 클래스가 있으면, 이는 데이터베이스의 user 테이블과 연결됨
- Repository
    
    JPA에서 제공하는 인터페이스를 상속받아 데이터베이스에 접근하는 메소드를 자동으로 생성
- JPQL (Java Persistence Query Language)
    
    데이터베이스 테이블이 아닌 자바 객체를 대상으로 쿼리 작성
    
