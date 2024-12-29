# Wikiz : Local Archiving and generating quiz based on Wikipedia

![ex_screenshot](https://github.com/kommiter/wikiz/demo0.png)

Wikiz는 Wikipedia API와 OpenAI의 GPT API를 활용하여 사용자가 문서를 검색하고, 읽고, 퀴즈를 생성할 수 있는 응용 프로그램입니다. 사용자는 GPT API 키를 입력하여 프로그램의 기능을 활성화하고, 직관적인 UI를 통해 정보를 탐색할 수 있습니다.

---

## 📋 목차

- [프로젝트 소개](#프로젝트-소개)
- [주요 기능](#주요-기능)
- [설치 방법](#설치-방법)
- [사용 방법](#사용-방법)
- [기술 스택](#기술-스택)
- [디렉토리 구조](#디렉토리-구조)
- [라이선스](#라이선스)

---

## 🌟 프로젝트 소개

Wikiz는 다음과 같은 목표를 위해 제작되었습니다.
1. **Wikipedia 문서 검색 및 읽기**: 사용자는 Wikipedia API를 통해 키워드로 문서를 검색하고 내용을 읽을 수 있습니다.
2. **퀴즈 생성**: OpenAI GPT API를 활용하여 검색한 문서 내용을 바탕으로 4지선다형 퀴즈를 생성할 수 있습니다.
3. **로컬 저장소 활용**: 이전에 검색한 문서와 생성된 퀴즈를 로컬 데이터베이스에 저장하여 재활용 가능합니다.

---

## 🛠️ 주요 기능

### 1. 로그인
- **GPT API Key 입력**: 사용자 입력 API 키를 프로그램이 검증하여 저장합니다.
- **자동 저장**: API Key를 로컬 `APIKEY.txt`에 저장하여 재사용이 가능합니다.

### 2. 검색
- **Wikipedia 문서 검색**: 키워드 기반 문서 검색 및 결과 목록 표시.
- **문서 읽기**: 검색된 문서를 선택하여 내용을 읽고, JSON 형태로 저장.

### 3. 퀴즈 생성
- **4지선다형 퀴즈**: GPT API를 활용하여 문서 내용을 기반으로 퀴즈를 생성.
- **퀴즈 풀기**: UI를 통해 정답 확인 및 피드백 제공.

### 4. 로컬 저장소
- **문서 및 퀴즈 저장**: JSON 파일 형태로 저장하여 재사용 가능.
- **검색 기능**: 로컬 저장된 데이터를 빠르게 검색.

---

## 🖥️ 설치 방법

1. **프로젝트 클론**
```
git clone https://github.com/username/wikiz.git
cd wikiz
```

2. **필요한 라이브러리 설치**
- `build.gradle` 파일을 기반으로 Gradle을 실행하여 의존성을 설치합니다.
```
gradle clean build
```

3. **API Key 설정**
- `src/main/resources/database/APIKEY.txt`에 OpenAI API Key를 저장합니다.

4. **실행**
```
gradle run
```

---

## 📚 사용 방법

### 1. API Key 입력
- 프로그램 실행 후 로그인 화면에서 OpenAI GPT API Key를 입력하고 유효성을 검사합니다.

### 2. Wikipedia 검색
- **좌측 패널**: 키워드를 입력하여 Wikipedia 문서를 검색합니다.
- **문서 읽기**: 검색된 문서를 선택하여 내용을 읽을 수 있습니다.

### 3. 퀴즈 생성
- **우측 패널**: 선택한 문서를 바탕으로 GPT API를 사용하여 4지선다형 퀴즈를 생성합니다.
- 퀴즈 생성 후 버튼을 클릭하여 정답 확인 및 정답을 확인할 수 있습니다.

---

## 💻 기술 스택

- **프로그래밍 언어**: Java
- **UI**: Swing
- **API**:
- Wikipedia API
- OpenAI GPT API
- **빌드 도구**: Gradle
- **저장소**: 로컬 JSON 파일 저장

---

## 📂 디렉토리 구조
wikiz/ <br />
├── src/ <br />
│   ├── main/ <br />
│   │   ├── java/ <br />
│   │   │   ├── hook/ <br />
│   │   │   ├── login/ <br />
│   │   │   ├── search/ <br />
│   │   │   ├── quiz/ <br />
│   │   │   └── main/ <br />
│   │   └── resources/ <br />
│   │       └── database/ <br />
├── build.gradle <br />
└── README.md <br />

---

## 📜 라이선스

이 프로젝트는 MIT 라이선스를 따릅니다.

---
