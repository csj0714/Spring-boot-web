## 목차

1. 의뢰 내용
2. 작업 내용
    1. AWS RDS 변경
    2. 프로젝트 빌드 형식 변경
    3. AWS EC2 Java Version 변경
    4. 쉘 스크립트 작성
        1. Spring Boot 기동 스크립트
        2. Spring Boot 정지 스크립트
        3. Spring Boot 및 서버 메모리, 디스크 조회 스크립트
    5. 프로젝트 빌드 및 배포 프로세스
3. 결과물 확인

## 의뢰 내용

1. Spring Boot 프로젝트를 제공받은 AWS RDS와 연결한 상태로, AWS EC2에 배포한다.

## 작업 내용

### 1. AWS RDS 변경

AS-IS : MariaDB

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://mydb.cri6wuai4i5n.ap-northeast-2.rds.amazonaws.com:3306/mydb?allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=s2794105!
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
```

TO-BE : MySQL

```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://myspringbootdb.cb22cew022cn.us-east-1.rds.amazonaws.com:3306/myspringbootdb?allowPublicKeyRetrieval=true
spring.datasource.username=csj5801
spring.datasource.password=s2794105!
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
```

<br/>

### 2. 프로젝트 빌드 형식 변경

> 변경 이유
> - jar 파일로 빌드할 경우, 스프링 부트의 내장 WAS인 tomcat을 사용할 수가 있습니다.
    >

- 따라서 별도의 웹 서버를 구축하지 않아도 되어 편리합니다.

> - war 파일 형식으로 빌드할 경우에는 직접 EC2에 tomcat을 설치하고, 스프링부트를 연결해야 합니다.
> - jar 파일로 빌드할 경우에도 동일하게 타임리프를 이용하여 Spring Boot에서 프론트엔드 화면을 보여줄 수 있어, 충분하다고 판단하였습니다. 실무에서도 jar 파일을 많이 사용합니다.


<br/> 

AS-IS : war

```xml

<project xmlns="..."
        ...
<version>0.0.1-SNAPSHOT</version>
<packaging>war</packaging>
        </project>
```

TO-BE : jar

```xml

<project xmlns="..."
        ...
<version>0.0.1</version>
<packaging>jar</packaging>
        </project>
```

<br/>

### 3. AWS EC2 Java Version 변경

AS-IS : Java 11

```bash
sudo apt-get install openjdk-11-jdk
```

TO-BE : Java 17

```bash
sudo apt-get install openjdk-17-jdk
```

<br/>

### 4. 쉘 스크립트 작성

Spring Boot 기동 스크립트

```bash
# AWS EC2 접속 후 ~ 위치에서
sh start.sh
```

Spring Boot 정지 스크립트

```bash
# AWS EC2 접속 후 ~ 위치에서
sh stop.sh
```

Spring Boot 및 서버 디스크, 메모리 상태 조회 스크립트

```bash
# AWS EC2 접속 후 ~ 위치에서
sh health_check.sh
```

<br/>

### 5. 프로젝트 빌드 및 배포 프로세스

1. 프로젝트 빌드
    - pom.xml에서 버전업 (0.0.1 → 0.0.2)
    - IntelliJ 에서 우측의 Maven Lifecycle 기능을 이용하여 package 버튼 클릭
    - {프로젝트 루트}/target/test-0.0.2.jar 생성 확인
2. 프로젝트 배포
    ```bash
    # 프로젝트 루트에서 실행
    # scp 명령어를 이용하여 AWS EC2 서버로 jar 파일 전송
    scp -i {pem 키 위치}/sshKey2.pem ./target/test-0.0.2.jar ubuntu@43.200.132.76:/home/ubuntu
    ```

3. 프로젝트 실행
    ```bash
    # AWS EC2 접속
    ssh -i {pem 키 위치}/sshKey2.pem ubuntu@43.200.132.76 
    
    # 이전에 실행중이던 스프링부트가 있을 경우 종료
    sh stop.sh
    
    # 스프링부트 프로젝트가 종료되었는지 확인
    # ps 확인란에서 test-***.jar가 있는지를 체크
    sh health_check.sh
    
    # 스프링부트 프로젝트 실행
    sh start.sh
    ```

<br/>

## 결과물 확인

아래 주소로 접속할 경우 확인 가능

- http://43.200.132.76:8080/