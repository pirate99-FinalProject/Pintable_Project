
## 📍 Index

1. [프로젝트 소개](#1-프로젝트-소개-)
2. [기술 스택](#2-기술-스택-)
3. [기능 명세](#3-기능-명세-)
4. [시연 영상](#4-시연영상-)
5. [서비스 아키텍처](#5-서비스-아키텍처-)
6. [ERD](#6-erd-)
7. [API](#7-api-)
8. [트러블슈팅](#8-트러블슈팅-)
#

![image](https://user-images.githubusercontent.com/117708164/216932548-af5766aa-a9a5-4d02-ba3f-49467c3fb956.png)

# PIN Table 🍽
### Pin-Table은, 대용량 위치 데이터를 활용한 위치 검색과 스토어 웨이팅 신청을 제공하는 서비스 입니다.

- 위치 데이터를 활용한 장소 위치 검색을 할 수 있어요 🙆🏻
- 가고싶은 식당을 검색하고, 현재 웨이팅 현황 및 웨이팅 신청을 할 수 있어요 ❗️

#

## 1. 프로젝트 소개 📢
- 설명 : 
- 프로젝트 목표 :
    - 대용량 장소 데이터를 활용한 검색 및 예약 서비스 플랫폼
- 프로젝트 이름 :
    - Pin-Table
- 서비스의 필요성 :
    - 지도 검색 시 내가 원하는 장소의 웨이팅 현황과 예약을 할 수 있어 편의성이 증대됨
- 구현 기능 및 핵심 목표 :
    - 검색 시스템과 UI 간소화 + 낮은 진입 장벽(사용자 편리성)
    - **약 1000만건의** 데이터 조회 목표 1초~3초
- 기간 : 
    - 2022.12.30 ~ 2022.02.09
- 팀원 : <br>

| - | 이름 | GITHUB |
|--|--|--|
| BE | 김규리🔰 | https://github.com/kyuung09 |
| BE | 이상훈 | https://github.com/leemeo3  |
| BE | 신승호 | https://github.com/hongdangmoo49 |
| BE | 황지성 | https://github.com/developjisung |

#

## 2. 기술 스택 🔨
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Springjpa-4FC08D?style=for-the-badge&logo=jpa&logoColor=white"> 
  <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/python-3776AB?style=for-the-badge&logo=python&logoColor=white"> 
  <br>

  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
  <img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
  <img src="https://img.shields.io/badge/axios-61DAFB?style=for-the-badge&logo=axios&logoColor=black">
    <img src="https://img.shields.io/badge/Thymeleaf-339933?style=for-the-badge&logo=Thymeleaf&logoColor=white">
  <br>
 
  <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white">  
  <img src="https://img.shields.io/badge/amazon rds-61DAFB?style=for-the-badge&logo=amazonrds&logoColor=white"> 
  <img src="https://img.shields.io/badge/amazon s3-E34F26?style=for-the-badge&logo=amazons3&logoColor=white">
  <img src="https://img.shields.io/badge/redis-DD0031?style=for-the-badge&logo=redis&logoColor=white">
  <img src="https://img.shields.io/badge/github action-000000?style=for-the-badge&logo=githubaction&logoColor=white">
  <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> 
    
    
  <br>
  <img src="https://img.shields.io/badge/elasticsearch-4053D6?style=for-the-badge&logo=elasticsearch&logoColor=white">
  <img src="https://img.shields.io/badge/logstash-FCC624?style=for-the-badge&logo=logstash&logoColor=black">
  <img src="https://img.shields.io/badge/kibana-DD0031?style=for-the-badge&logo=kibana&logoColor=white">
  <img src="https://img.shields.io/badge/junit5-F05032?style=for-the-badge&logo=junit5&logoColor=white">
  <img src="https://img.shields.io/badge/jmeter-000000?style=for-the-badge&logo=jmeter&logoColor=white">
</div>

#

## 3. 기능 명세 🗂
<details>
<summary> 주요 기능 🖋 </summary>
<div markdown="1">       

 
(1) 장소 검색 
<br>
![검색빠르게](https://user-images.githubusercontent.com/117708164/217257406-1717d3d7-5384-49d6-9fe5-67e3087a5ce4.gif)
<br>
- 장소 검색 기능
- 카테고리 필터 검색 기능 
- 검색어 지정 검색 (가게 이름 / 도로명 주소 / 업종)
- 랭킹 조회(평점 4점 이상 / 평점 높은 순 / 리뷰 1000개 이상 / 리뷰 높은 순)
- Elastic Search + Nori 형태소 분석기를 이용한 빠르고 정확한 검색 기능 제공

(2) 예약(웨이팅) 시스템
<br>
![웨이팅등록2배속](https://user-images.githubusercontent.com/117708164/217257762-10912785-2722-4dd9-938d-10a768b9d117.gif)
<br>
    
- 스토어 웨이팅 신청 기능
- 스토어 현황 조회 기능
- 사용자 유효성(메일) 인증 및 메일 호출 기능

(3) 장소 리뷰 기능
<br>
![리뷰빠르게](https://user-images.githubusercontent.com/117708164/217258004-0e90f7d1-751f-480c-be8d-5cb66902d211.gif)
<br>
- 장소 리뷰 작성 및 조회 기능
- 리뷰 등록 시 스토어 정보 갱신(별점, 리뷰 갯수) 기능

(4) 관리자페이지(사장님 메뉴) 기능
<br>
![](https://s3.us-west-2.amazonaws.com/secure.notion-static.com/ace974d9-7b6f-4fd0-9433-82f2ed524571/ezgif.com-gif-maker_%284%29.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20230207%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20230207T133119Z&X-Amz-Expires=86400&X-Amz-Signature=6f7007409b140efa46b329d72a035f2c2122c118029733af550f0f3dae4078ad&X-Amz-SignedHeaders=host&response-content-disposition=filename%3D%22ezgif.com-gif-maker%2520%284%29.gif%22&x-id=GetObject)
<br>
- 웨이팅 현황 확인 기능 제공
- 손님 입장/퇴장 기능 제공
- 웨이팅 호출(이메일) 기능 제공
    
</div>
</details>

## 4. 시연영상 🎥
[시연영상 바로가기](https://www.youtube.com/watch?v=iY3YxFOawYs)

## 5. 서비스 아키텍처 🎨
![image](https://user-images.githubusercontent.com/117708164/216933029-f5c882eb-daec-4216-a27c-56822226726f.png)

## 6. ERD 👨🏻‍💻
<details>
<summary> 펼쳐보기 </summary>
<div markdown="1">  
    
![image](https://user-images.githubusercontent.com/117708164/216933200-78984f6c-6653-46ea-bf1f-ee8900e5c2bf.png)
    
</div>
</details>

## 7. API 💬
[Swagger API 바로가기](https://pintable.co.kr/swagger-ui/index.html?urls.primaryName=store#/store-controller/createStoreUsingPOST)

## 8. 트러블슈팅 🐞

<details>
<summary> 데이터 수집 </summary>
<div markdown="1">  
<br>
  (1) 공공 API를 이용한 대용량 장소 데이터 수집<br>
    - 공공 데이터 포털에서 제공하는 전국 음식점/카페의 파일 데이터 수집 (약 650만건)<br>

<br>
(2) 장소 데이터 1차 가공<br>
    - 공공 데이터 포털에서 수집한 데이터 중 불필요 컬럼 및 폐업 장소 데이터 제거 (약 200만건)<br>

<br>
(3) 네이버 플레이스 웹 크롤링을 통한 추가 데이터 수집<br>
    - 웹크롤링(Python + 셀레니움)을 이용하여 리뷰 갯수/ 블로그 리뷰 / 별점 정보 등 추가 데이터 수집<br>
    - 네이버 플레이스 크롤링 시 특정 횟수 이상 반복 시도시 차단되는 경우가 발생하였고, 아래 과정을 통해 해결<br>
    👉 https://www.notion.so/404-Not-Found-9ec37cc4600545e7972663f4d9d06364<br>
<br>
(4) Faker 라이브러리를 이용한 리뷰/사용자 데이터 생성<br>
    - Faker 라이브러리를 이용하여 약 600만건의 리뷰/사용자 데이터 생성<br>
<br>
</div>
</details>

<details>
<summary> 검색 성능 향상 </summary>
<div markdown="1">
   🔻 검색 성능 개선 자세히 보기 : https://www.notion.so/PIN-TABLE-98c3a7dbb1324630a3a300575b2f7782
</div>
</details>


<details>
<summary> 대용량 트래픽 부하분산 </summary>
<div markdown="1">
   🔻 대용량 트래픽 부하분산 자세히 보기 : https://www.notion.so/PIN-TABLE-641ad77ad45247c2801ae079db805389
)
</div>
</details>


<details>
<summary> 동시성 제어 </summary>
<div markdown="1">
   🔻 동시성 제어 자세히 보기 : https://www.notion.so/PIN-TABLE-721fb45b91454dda8255e0de4ac757ca
)
</div>
</details>
