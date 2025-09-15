## 유저
- `POST /user-service/member`
    - 회원가입
- `POST /user-service/login`
    - 로그인
- `GET /user-service/profile/member`
    - 유저 프로필 검색
- `GET /user-service/profile/member/{memberId}`
    - 해당 유저 프로필 조회
- `GET /user-service/member/{memberId}/info`
    - 해당 유저 정보 조회
- `GET /user-service/member/info`
    - 유저 정보 조회

## 게시물
- `POST /post-service/post`
    - 게시물 생성
- `GET /post-service/member/{memberId}/post`
    - 해당 유저가 작성한 게시물 리스트 조회
- `GET /post-service/member/{memberId}/post/preview`
    - 해당 유저가 작성한 게시물 중 본인의 관심사와 같은 해시태그를 가진 게시물을 미리보기 형태로 조회
- `GET /post-service/feed`
    - 팔로우하고 있는 사람들의 게시물 리스트 조회
- `GET /post-service/top-commented`
    - 댓글 많은 글 top5 조회
- `POST /post-service/post/{postId}/comment`
    - 해당 게시물에 댓글 달기
- `GET /post-service/post/{postId}/comment`
    - 해당 게시물의 댓글 리스트 조회
- `POST /post-service/post/{postId}/comment/{commentId}`
    - 해당 게시물의 특정 댓글에 대댓글 달기
- `GET /post-service/post/{postId}/comment/{commentId}`
    - 해당 게시물의 특정 댓글의 대댓글 조회

## 팔로우
- `PATCH /follow-service/follow/{memberId}`
    - 팔로우하기 or 팔로우 취소하기
- `GET /follow-service/follower`
    - 팔로워 조회하기
- `GET /follow-service/following-of-following/{memberId}`
    - 해당 유저가 팔로우하는 유저가 팔로우하는 유저를 추천

## 채팅
- `GET /chat-service/room`
    - 채팅방 조회
- `GET /chat-service/room/{roomId}/chat`
    - 해당 채팅방 메시지 조회
- `PUT /chat-service/room/{roomId}/chat/read`
    - 해당 채팅방 메시지 읽음
- `/app/room/create`
    - 채팅방 생성
- `/app/chat`
    - 채팅 보내기