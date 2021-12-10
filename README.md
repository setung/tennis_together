# Tennis Together
--------------
## 프로젝트 개요
서울 경기 지역의 테니스를 하는 유저들을 모아 함께 게임할 수 있도록 매칭해주는 API서버 토이 프로젝트입니다.!

## 프로젝트 구조
![프로젝트 구조도](https://user-images.githubusercontent.com/67427856/143246534-b41ff20c-5f95-4dbb-93dd-adfc1c4b36a9.png)


## 기술스택
- Spring boot
- Spring Sequrity
- Spring Data Jpa
- Java 11
- postgresql
- Firebase OAuth
- Firebase Storage
- Github
- Heroku

## 주요기능
1. 회원가입 / 탈퇴
2. 로그인 / 로그아웃
3. 회원정보 수정
4. 게시글 CRUD
5. 친구 추가 / 삭제
6. 친구 추천
   
## 기술
 - 로그인은 Firebase OAuth를 사용하였습니다.
 - 이미지 저장을 위해 Firebase Storage를 사용하였습니다.
 - 자동 배포 환경을 Github Actions와 Heroku로 구축하였습니다.
 - 예외처리를 위해 @RestControllerAdvice를 사용하였습니다.
 - API 문서는 Swagger를 도입하였습니다.

## 개발 담당
### 안지인
- GameComment 등록 / 수정 / 삭제 / 전체조회 
- 추천 친구 조회 
- 친구 팔로우 / 팔로우취소 / 전체조회 
### 강일훈
### 장세훈
- 테니스장 정보 크롤링
- Game(모집글) CRUD
- Tennis Court
- Location
- User Review CRUD
- Game 신청 / 승락 / 거절


<br>
Tennis-Together url :  https://tennis-togeter.herokuapp.com/ <br>
Frontend Github Url : https://github.com/ssso-pro1/tennis-together-fe <br>
Tennis Crawler Github Url : https://github.com/setung/tennis_together_crawler

