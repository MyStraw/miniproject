# 코드 리팩토리중
##  MetroAdd 폴더에 새로 작업
1. 컨트롤러 -> 서비스 -> 레파지토리 하나의 흐름이 되게 1:1:1
2. 컨트롤러안에 모든 기능을 다 넣는게 아니라 컨트롤러를 각 기능별로 쪼개기
3. 컨트롤러, 서비스도 인터페이스 구현 (JPA를 안쓰게 되면, JDBC로 만든 레파지토리 인터페이스도 구현해야겠지?)
4. 컨트롤러, 서비스에 validation (글자 사이즈, id는 음수가 안되어야 한다, 엔티티에서도 등등)
5. JPA -> JDBC로 바꾸기
6. 테스트코드 짜기
7. Oauth로 회원가입, 로그인 하기



# 부산 지하철 이용정보 미니 프로젝트
#### 부산대학교 K-digital 3기  
#### 개발기간: 2023.07.24 ~ 2023.08.16
#### 데이터출처  
- https://www.data.go.kr/data/15054957/fileData.do [노선도 이미지]  
- https://www.data.go.kr/data/15082980/fileData.do [운행정보]
- https://www.data.go.kr/data/15050408/fileData.do [역정보]
- https://www.data.go.kr/data/15052664/fileData.do [편의시설]
- https://www.data.go.kr/data/15041171/fileData.do [승강장정보]


## 프로젝트 구성
프론트엔드 : https://github.com/fabbitox/subway  
백엔드 : https://github.com/MyStraw/miniproject

## 기술스택
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

## 시작가이드
#### Repository clone
~~~
git clone https://github.com/MyStraw/miniproject
~~~

#### application.properties
MySQL을 이용
ID와 password 등을 알맞게 수정

#### DB 구성
- 레파지토리 내 DB 폴더의 최신 날짜 폴더안의 .sql 파일 MySQL에서 불러오기후 실행
- uploads 폴더를 c:\Temp\uploads 경로에 배치

#### 백엔드
- Metro 프로젝트 서버 실행

#### 프론트엔드
- https://github.com/fabbitox/subway  


## ERD
<img src = img/erd.png>


## 주요 기능
- 부산 지하철 운행정보, 역 정보, 편의시설 정보  
- 회원가입 및 로그인  
- 게시판
- 이미지 업로드  
- 좋아요  
- 내가 쓴글 및 내가 좋아요 눌린 글 모아보기

## 화면 구성  
| 노선도 |
| ------------ | 
| <img src=img/1.png> |

| 역정보 및 게시판 |
| ------------ | 
| <img src=img/2.png> |

| 편의시설 정보 |
| ------------ | 
| <img src=img/3.png> |

| 회원정보 |
| ------------ | 
| <img src=img/4.png> |
