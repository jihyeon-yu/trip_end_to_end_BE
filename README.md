## 💻  프로젝트 명
Trip-End-To-End

## 📄  프로젝트 소개
Trip-End-To-End는 사용자가 개인 맞춤 정보를 활용하여 여행 계획을 세우고, 여행 후기를 작성 및 공유할 수 있는 여행 계획 웹 사이트입니다. 이 플랫폼은 사용자가 여행 일정을 보다 효율적으로 관리하고, 여행지에서의 경험을 다른 사용자와 나눌 수 있도록 돕습니다.

## 환경 설정

- google cloud vision api
- application.properties
- dbvalue.properties
- mysql
- sts

- [vue.js 바로가기](https://github.com/zpqmdh/trip_end_to_end_FE.git)
- [챗봇 바로가기](https://github.com/jihyeon-yu/openai_chatbot.git)

## 주요 기능

## 기술 스택

- SpringBoot + Maven
- MyBatis
- MySQL
- WebSocket
- Git
- 추가

## ERD

![image](https://github.com/jihyeon-yu/trip_end_to_end_BE/assets/87455115/d59a5468-58d3-4740-b75e-89b20da22861)

## 컨트롤러

| 이름                 | 경로         | 설명 |
| -------------------- | ------------ | ---- |
| 회원 컨트롤러        | `/members`   | 설명 |
| 여행 계획 컨트롤러   | `/plan`      | 설명 |
| 여행 후기 컨트롤러   | `/shareplan` | 설명 |
| 공지사항 컨트롤러    | `/notice`    | 설명 |
| 문의 게시판 컨트롤러 | `/qna`       | 설명 |
| 랜드마크             | `/`          | 설명 |
| 실시간 채팅          | `/`          | 설명 |

## API 리스트

| 설명                         | 메소드 | URL                                           |
| ---------------------------- | ------ | --------------------------------------------- |
| 회원 가입                    | POST   | `/api/members/signup`                         |
| 로그인                       | POST   | `/api/members/login`                          |
| 회원 목록                    | GET    | `/api/members/list`                           |
| 회원 정보                    | GET    | `/api/members/detail/{id}`                    |
| 회원 탈퇴                    | PUT    | `/api/members/delete/{id}`                    |
| 회원 정보 수정               | PUT    | `/api/members/update/{id}`                    |
| 비밀번호 변경                | PUT    | `/api/members/changepassword`                 |
| 여행 계획 생성               | POST   | `/api/plans/create`                           |
| 여행 계획 목록(작성자)       | GET    | `/api/plans/list/{id}`                        |
| 여행 계획 목록(구성원 포함)  | GET    | `/api/plans/list/all/{id}`                    |
| 여행 계획 목록(구성원 포함)  | GET    | `/api/plans/detail/{planId}`                  |
| 여행 계획 목록(구성원 포함)  | GET    | `/api/plans/delete/{planId}`                  |
| 여행 계획 목록(구성원 포함)  | GET    | `/api/plans/update/{planId}`                  |
| 여행 계획 목록(구성원 포함)  | GET    | `/api/plans/getMember/{memberId}`             |
| 여행 공유 게시글 목록        | GET    | `/api/shareplan/list`                         |
| 여행 공유 게시글 상세        | GET    | `/api/shareplan/{planBoardId}`                |
| 여행 공유 게시글 작성        | POST   | `/api/shareplan/insert`                       |
| 여행 공유 게시글 삭제        | DELETE | `/api/shareplan/{planBoardId}`                |
| 여행 공유 게시글 수정        | PUT    | `/api/shareplan/{planBoardId}`                |
| 여행 공유 게시글 댓글 작성   | POST   | `/api/shareplan/insert/{planBoardId}/comment` |
| 여행 공유 게시글 댓글 삭제   | DELETE | `/api/shareplan/comment/{commentId}`          |
| 여행 공유 게시글 댓글 수정   | PUT    | `/api/shareplan/comment/{commentId}`          |
| 여행 공유 게시글 태그 삽입   | POST   | `/api/shareplan/insert/{planBoardId}/tag`     |
| 여행 공유 게시글 태그 삭제   | DELETE | `/api/shareplan/tag/{planBoardTagId}`         |
| 여행 공유 게시글 태그 검색   | GET    | `/api/shareplan/tag/{tagName}`                |
| 여행 공유 게시글 좋아요 추가 | POST   | `/api/shareplan/insert/{planBoardId}/like`    |
| 여행 공유 게시글 좋아요 해제 | DELETE | `/api/shareplan/like/{planLikeId}`            |
| 지도 시도 목록               | GET    | `/api/map/sido`                               |
| 지도 구군 목록               | GET    | `/api/map/gugun/{sidoCode}`                   |
| 지도 관광정보 목록           | GET    | `/api/map/attractioninfo`                     |
| 지도 관광정보 설명           | GET    | `/api/map/attractiondescription/{contentId}`  |
| 공지사항 목록                | GET    | `/api/notice/list`                            |
| 공지사항 상세                | GET    | `/api/notice/{noticeId}`                      |
| 공지사항 작성                | POST   | `/api/notice/insert`                          |
| 공지사항 수정                | PUT    | `/api/notice/{noticeId}`                      |
| 공지사항 삭제                | DELETE | `/api/notice/{noticeId}`                      |
| 문의게시글 목록              | GET    | `/api/qna/list`                               |
| 문의게시글 상세              | GET    | `/api/qna/{qnaBoardId}`                       |
| 문의게시글 작성              | POST   | `/api/qna/insert`                             |
| 문의게시글 수정              | PUT    | `/api/qna/{qnaBoardId}`                       |
| 문의게시글 삭제              | DELETE | `/api/qna/{qnaBoardId}`                       |
| 문의게시글 답변 작성         | POST   | `/api/qna/insert/{qnaBoardId}/comment`        |
| 문의게시글 답변 삭제         | DELETE | `/api/qna/comment/{commentId}`                |
| 문의게시글 답변 수정         | PUT    | `/api/qna/comment/{commentId}`                |
| 랜드마크 상세                | POST   | `/api/detectLandmark`                         |
